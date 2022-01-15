package com.yufu.yepshop.application.impl;

import com.yufu.yepshop.application.BaseService;
import com.yufu.yepshop.application.GoodsService;
import com.yufu.yepshop.common.Result;
import com.yufu.yepshop.persistence.DO.GoodsDO;
import com.yufu.yepshop.persistence.DO.GoodsDetailDO;
import com.yufu.yepshop.persistence.DO.OrderDO;
import com.yufu.yepshop.persistence.DO.UserAccountDO;
import com.yufu.yepshop.persistence.converter.GoodsConverter;
import com.yufu.yepshop.persistence.dao.GoodsDAO;
import com.yufu.yepshop.persistence.dao.GoodsDetailDAO;
import com.yufu.yepshop.persistence.dao.UserAccountDAO;
import com.yufu.yepshop.types.command.CreateGoodsCommand;
import com.yufu.yepshop.types.command.UpdateGoodsCommand;
import com.yufu.yepshop.types.dto.GoodsDTO;
import com.yufu.yepshop.types.dto.GoodsListDTO;
import com.yufu.yepshop.types.enums.AuditState;
import com.yufu.yepshop.types.enums.GoodsState;
import com.yufu.yepshop.types.enums.OrderState;
import com.yufu.yepshop.types.enums.SellerType;
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
    private final GoodsConverter goodsAssembler = GoodsConverter.INSTANCE;

    public GoodsServiceImpl(
            GoodsDAO goodsDAO,
            UserAccountDAO accountDAO,
            GoodsDetailDAO goodsDetailDAO) {
        this.accountDAO = accountDAO;
        this.goodsDAO = goodsDAO;
        this.goodsDetailDAO = goodsDetailDAO;
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
    public Result<Boolean> update(String id, UpdateGoodsCommand command) {
        Long lId = Long.parseLong(id);
        GoodsDO entity = goodsDAO.findById(lId).get();
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
    public Result<Boolean> update(String id, GoodsState state) {
        Long lId = Long.parseLong(id);
        GoodsDO entity = goodsDAO.findById(lId).get();
        entity.setGoodsState(state);
        goodsDAO.save(entity);
        return Result.success(true);
    }

    @Override
    public Result<Boolean> delete(String id) {
        goodsDAO.deleteById(Long.parseLong(id));
        return Result.success(true);
    }

    @Override
    public Result<Page<GoodsListDTO>> pagedList(Integer page, Integer perPage, String goodsState) {
        Pageable pageable = PageRequest.of(page, perPage, Sort.Direction.DESC, "id");
        Specification<GoodsDO> spc = (x, y, z) -> {
            ArrayList<Predicate> list = new ArrayList<>();
            if (!"ALL".equalsIgnoreCase(goodsState)) {
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
    public Result<GoodsDTO> get(String id) {
        Long lId = Long.parseLong(id);
        GoodsDO goodsDO = goodsDAO.findById(lId).get();
        GoodsDetailDO goodsDetailDO = goodsDetailDAO.findById(lId).get();
        GoodsDTO result = goodsAssembler.toDTO(goodsDO);
        goodsAssembler.toDTO(goodsDetailDO, result);
        buildSeller(result.getSeller());
        return Result.success(result);
    }

    private Seller buildSeller(Seller seller){
        Optional<UserAccountDO> sellerOptional = accountDAO.findById(seller.getLongId());
        if (sellerOptional.isPresent()) {
            UserAccountDO sellerDO = sellerOptional.get();
            seller.setNickName(sellerDO.getNickName());
            seller.setAvatarUrl(sellerDO.getAvatarUrl());
            return seller;
        }
        return null;
    }

}
