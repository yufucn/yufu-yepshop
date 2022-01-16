package com.yufu.yepshop.application.impl;

import com.yufu.yepshop.application.BaseService;
import com.yufu.yepshop.application.GoodsService;
import com.yufu.yepshop.common.Constants;
import com.yufu.yepshop.common.Result;
import com.yufu.yepshop.persistence.DO.GoodsDO;
import com.yufu.yepshop.persistence.DO.GoodsDetailDO;
import com.yufu.yepshop.persistence.DO.SchoolDO;
import com.yufu.yepshop.persistence.DO.UserAccountDO;
import com.yufu.yepshop.persistence.converter.GoodsConverter;
import com.yufu.yepshop.persistence.dao.*;
import com.yufu.yepshop.types.command.CreateGoodsCommand;
import com.yufu.yepshop.types.command.UpdateGoodsCommand;
import com.yufu.yepshop.types.dto.GoodsDTO;
import com.yufu.yepshop.types.dto.GoodsListDTO;
import com.yufu.yepshop.types.enums.AuditState;
import com.yufu.yepshop.types.enums.GoodsState;
import com.yufu.yepshop.types.enums.SellerType;
import com.yufu.yepshop.types.query.GoodsQuery;
import com.yufu.yepshop.types.value.SchoolValue;
import com.yufu.yepshop.types.value.Seller;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.Optional;

/**
 * @author wang
 * @date 2022/1/9 18:25
 */
@Service
public class GoodsServiceImpl extends BaseService implements GoodsService {
    private final GoodsDAO goodsDAO;
    private final UserAccountDAO accountDAO;
    private final GoodsDetailDAO goodsDetailDAO;
    private final SchoolDAO schoolDAO;
    private final GoodsConverter goodsAssembler = GoodsConverter.INSTANCE;

    public GoodsServiceImpl(
            GoodsDAO goodsDAO,
            UserAccountDAO accountDAO,
            GoodsDetailDAO goodsDetailDAO,
            SchoolDAO schoolDAO) {
        this.accountDAO = accountDAO;
        this.goodsDAO = goodsDAO;
        this.goodsDetailDAO = goodsDetailDAO;
        this.schoolDAO = schoolDAO;
    }

    @Override
    public Result<Boolean> create(CreateGoodsCommand command) {
        UserAccountDO user = currentUser();
        GoodsDO entity = goodsAssembler.toDO(command);
        entity.setTitle(command.getTitleFromText());
        entity.setSellerId(user.getId());
        entity.setSellerType(SellerType.C);
        //默认上架、审核通过
        entity.setGoodsState(GoodsState.UP);
        entity.setAuditState(AuditState.SUCCESS);
        goodsDAO.save(entity);
        GoodsDetailDO detail = new GoodsDetailDO();
        detail.setId(entity.getId());
        detail.setText(command.getText());
        detail.setImageUrls(String.join(",", command.getImageUrlList()));
        goodsDetailDAO.save(detail);
        return Result.success(true);
    }

    @Override
    public Result<Boolean> update(Long id, UpdateGoodsCommand command) {
        GoodsDO entity = goodsDAO.findById(id).get();
        goodsAssembler.toDO(command);
        entity.setTitle(command.getTitleFromText());
        goodsDAO.save(entity);

        GoodsDetailDO detailDO = new GoodsDetailDO();

        detailDO.setId(entity.getId());
        detailDO.setText(command.getText());
        detailDO.setImageUrls(String.join(",", command.getImageUrlList()));
        goodsDetailDAO.save(detailDO);

        return Result.success(true);
    }

    @Override
    public Result<Boolean> update(Long id, GoodsState state) {
        GoodsDO entity = goodsDAO.findById(id).get();
        entity.setGoodsState(state);
        goodsDAO.save(entity);
        return Result.success(true);
    }

    @Override
    public Result<Boolean> delete(Long id) {
        goodsDAO.deleteById(id);
        return Result.success(true);
    }

    @Override
    public Result<Page<GoodsListDTO>> pagedList(Long creatorId, Integer page, Integer perPage, String goodsState) {
        Pageable pageable = PageRequest.of(page, perPage, Sort.Direction.DESC, "id");
        Specification<GoodsDO> spc = (x, y, z) -> {
            ArrayList<Predicate> list = new ArrayList<>();
            list.add(z.equal(x.get("creatorId"), creatorId));
            if (!Constants.QUERY_ALL.equalsIgnoreCase(goodsState)) {
                list.add(z.equal(x.get("goodsState"), GoodsState.valueOf(goodsState)));
            }
            Predicate[] predicates = new Predicate[list.size()];
            return z.and(list.toArray(predicates));
        };

        Page<GoodsListDTO> paged =
                goodsDAO.findAll(spc, pageable).map(this::convert);
        return Result.success(paged);
    }

    private GoodsListDTO convert(GoodsDO gDo) {
        GoodsListDTO dto = goodsAssembler.toListDTO(gDo);
        Seller seller = dto.getSeller();
        buildSeller(seller);
        return dto;
    }

    @Override
    public Result<GoodsDTO> get(Long id) {
        GoodsDO goodsDO = goodsDAO.findById(id).get();
        GoodsDetailDO goodsDetailDO = goodsDetailDAO.findById(id).get();
        GoodsDTO result = goodsAssembler.toDTO(goodsDO);
        goodsAssembler.toDTO(goodsDetailDO, result);
        buildSeller(result.getSeller());
        builderSchool(result.getSchool());
        return Result.success(result);
    }

    @Override
    public Result<Page<GoodsListDTO>> search(GoodsQuery query) {
        Sort.Direction sortDirection = Sort.Direction.DESC;
        String column = "id";
        switch (query.getSort()) {
            case PRICE_ASC: {
                sortDirection = Sort.Direction.ASC;
                column = "price";
                break;
            }
            case PRICE_DESC: {
                sortDirection = Sort.Direction.DESC;
                column = "price";
                break;
            }
            case LATEST:
            case ALL:
            default:
        }
        Pageable pageable = PageRequest.of(query.getPage(), query.getPerPage(), sortDirection, column);
        Specification<GoodsDO> spc = (x, y, z) -> {
            ArrayList<Predicate> list = new ArrayList<>();
            if (query.getKeyword() != null) {
                list.add(z.like(x.get("title"), query.getKeyword()));
            }
            if (query.getSchoolId() != null) {
                list.add(z.equal(x.get("schoolId"), query.getSchoolId()));
            }
            if (query.getCategoryId() != null) {
                list.add(z.equal(x.get("categoryId"), query.getCategoryId()));
            }
            if (query.getConditionId() != null) {
                list.add(z.equal(x.get("conditionId"), query.getConditionId()));
            }
            Predicate[] predicates = new Predicate[list.size()];
            return z.and(list.toArray(predicates));
        };
        Page<GoodsListDTO> paged =
                goodsDAO.findAll(spc, pageable).map(this::convert);
        return Result.success(paged);
    }

    private void buildSeller(Seller seller) {
        Optional<UserAccountDO> sellerOptional = accountDAO.findById(seller.getLongId());
        if (sellerOptional.isPresent()) {
            UserAccountDO sellerDO = sellerOptional.get();
            seller.setNickName(sellerDO.getNickName());
            seller.setAvatarUrl(sellerDO.getAvatarUrl());
        }
    }

    private void builderSchool(SchoolValue schoolValue) {
        Optional<SchoolDO> optional = schoolDAO.findById(schoolValue.getLongId());
        if (optional.isPresent()) {
            SchoolDO doo = optional.get();
            schoolValue.setName(doo.getName());
        }
    }
}
