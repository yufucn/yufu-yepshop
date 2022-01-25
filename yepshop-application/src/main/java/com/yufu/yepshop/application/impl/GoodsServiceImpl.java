package com.yufu.yepshop.application.impl;

import com.yufu.yepshop.domain.service.impl.BaseService;
import com.yufu.yepshop.application.GoodsService;
import com.yufu.yepshop.common.Constants;
import com.yufu.yepshop.common.Result;
import com.yufu.yepshop.persistence.DO.*;
import com.yufu.yepshop.persistence.converter.GoodsConverter;
import com.yufu.yepshop.persistence.dao.*;
import com.yufu.yepshop.types.command.CreateGoodsCommand;
import com.yufu.yepshop.types.command.UpdateGoodsCommand;
import com.yufu.yepshop.types.dto.GoodsDTO;
import com.yufu.yepshop.types.dto.GoodsListDTO;
import com.yufu.yepshop.types.enums.AuditState;
import com.yufu.yepshop.types.enums.GoodsState;
import com.yufu.yepshop.types.enums.SellerType;
import com.yufu.yepshop.types.enums.SortFilter;
import com.yufu.yepshop.types.query.GoodsQuery;
import com.yufu.yepshop.types.value.Seller;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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
    private final UserCollectDAO userCollectDAO;
    private final GoodsViewDAO goodsViewDAO;
    private final GoodsConverter goodsAssembler = GoodsConverter.INSTANCE;

    public GoodsServiceImpl(
            GoodsDAO goodsDAO,
            UserAccountDAO accountDAO,
            GoodsDetailDAO goodsDetailDAO,
            SchoolDAO schoolDAO, UserCollectDAO userCollectDAO, GoodsViewDAO goodsViewDAO) {
        this.accountDAO = accountDAO;
        this.goodsDAO = goodsDAO;
        this.goodsDetailDAO = goodsDetailDAO;
        this.schoolDAO = schoolDAO;
        this.userCollectDAO = userCollectDAO;
        this.goodsViewDAO = goodsViewDAO;
    }

    @Override
    public Result<String> create(CreateGoodsCommand command) {
        UserAccountDO user = currentUser();
        GoodsDO entity = goodsAssembler.toDO(command);
        entity.setTitle(command.getTitleFromText());
        entity.setSellerId(user.getId());
        entity.setSellerType(SellerType.C);
        entity.setTotalCollect(0);
        entity.setTotalComment(0);
        //默认上架、审核通过
        entity.setAuditState(AuditState.SUCCESS);
        goodsDAO.save(entity);
        Long id = entity.getId();
        GoodsDetailDO detail = new GoodsDetailDO();
        detail.setId(id);
        detail.setText(command.getText());
        detail.setImageUrls(String.join(",", command.getImageUrlList()));
        goodsDetailDAO.save(detail);
        return Result.success(id.toString(), "发布成功");
    }

    @Override
    public Result<Boolean> update(Long id, UpdateGoodsCommand command) {
        GoodsDO goodsDO = getById(id);
        if (goodsDO != null) {
            goodsAssembler.toDO(command, goodsDO);
            goodsDO.setTitle(command.getTitleFromText());
            goodsDAO.save(goodsDO);

            GoodsDetailDO detailDO = new GoodsDetailDO();

            detailDO.setId(goodsDO.getId());
            detailDO.setText(command.getText());
            detailDO.setImageUrls(String.join(",", command.getImageUrlList()));
            goodsDetailDAO.save(detailDO);

            return Result.success(true);
        }
        return Result.fail("商品不存在！");
    }

    @Override
    public Result<Boolean> update(Long id, GoodsState state) {
        GoodsDO goodsDO = getById(id);
        if (goodsDO != null) {
            goodsDO.setGoodsState(state);
            goodsDAO.save(goodsDO);
            return Result.success(true);
        }
        return Result.fail("商品不存在！");
    }

    @Override
    public Result<Boolean> delete(Long id) {
        goodsDAO.deleteById(id);
        goodsDetailDAO.deleteById(id);
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
        buildSeller(accountDAO, seller);
        builderSchool(schoolDAO, dto.getSchool());
        return dto;
    }

    @Override
    public Result<GoodsDTO> get(Long id) {
        GoodsDO goodsDO = getById(id);
        if (goodsDO != null) {
            GoodsDetailDO goodsDetailDO = goodsDetailDAO.findById(id).get();
            GoodsDTO result = goodsAssembler.toDTO(goodsDO);
            goodsAssembler.toDTO(goodsDetailDO, result);
            buildSeller(accountDAO, result.getSeller());
            builderSchool(schoolDAO, result.getSchool());
            return Result.success(result);
        } else {
            return Result.fail("商品不存在！");
        }
    }

    @Override
    public Result<Page<GoodsListDTO>> search(GoodsQuery query) {
        Sort.Direction sortDirection = Sort.Direction.DESC;
        String column = "id";
        String sort = query.getSort();
        if (StringUtils.isEmpty(sort)) {
            sort = SortFilter.ALL.toString();
        }
        switch (SortFilter.valueOf(sort)) {
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
            list.add(z.equal(x.get("goodsState"), GoodsState.UP));
            list.add(z.equal(x.get("auditState"), AuditState.SUCCESS));
            if (!StringUtils.isEmpty(query.getKeyword())) {
                list.add(z.like(x.get("title"), "%" + query.getKeyword() + "%"));
            }
            if (query.getSchoolIds().size() > 0) {
                Expression<String> exp = x.get("schoolId");
                list.add(exp.in(query.getSchoolIds()));
            }
            if (query.getCategoryIds().size() > 0) {
                Expression<String> exp = x.get("categoryId");
                list.add(exp.in(query.getCategoryIds()));
            }
            if (query.getConditionIds().size() > 0) {
                Expression<String> exp = x.get("conditionId");
                list.add(exp.in(query.getConditionIds()));
            }
            Predicate[] predicates = new Predicate[list.size()];
            return z.and(list.toArray(predicates));
        };
        Page<GoodsListDTO> paged =
                goodsDAO.findAll(spc, pageable).map(this::convert);
        return Result.success(paged);
    }

    @Override
    public Result<Page<GoodsListDTO>> collectionList(Integer page, Integer perPage) {
        Long userId = currentUser().getId();
        Sort.Direction sortDirection = Sort.Direction.DESC;
        String column = "id";
        Pageable pageable = PageRequest.of(page, perPage, sortDirection, column);
        Specification<UserCollectDO> spc = (x, y, z) -> {
            ArrayList<Predicate> list = new ArrayList<>();
            list.add(z.equal(x.get("creatorId"), userId));
            Predicate[] predicates = new Predicate[list.size()];
            return z.and(list.toArray(predicates));
        };
        Page<UserCollectDO> paged = userCollectDAO.findAll(spc, pageable);
        List<UserCollectDO> list = paged.getContent();
        List<Long> ids = list.stream().map(UserCollectDO::getGoodsId).collect(Collectors.toList());
        List<GoodsDO> goodsDOList = getByIds(ids);
        List<GoodsListDTO> listDTOS = goodsAssembler.toGoodsListDTO(goodsDOList);
        Page<GoodsListDTO> result = new PageImpl<>(listDTOS, paged.getPageable(), paged.getTotalElements());
        return Result.success(result);
    }

    @Override
    public Result<Page<GoodsListDTO>> viewList(Integer page, Integer perPage) {
        Long userId = currentUser().getId();
        Sort.Direction sortDirection = Sort.Direction.DESC;
        String column = "id";
        Pageable pageable = PageRequest.of(page, perPage, sortDirection, column);
        Specification<GoodsViewDO> spc = (x, y, z) -> {
            ArrayList<Predicate> list = new ArrayList<>();
            list.add(z.equal(x.get("creatorId"), userId));
            Predicate[] predicates = new Predicate[list.size()];
            return z.and(list.toArray(predicates));
        };
        Page<GoodsViewDO> paged = goodsViewDAO.findAll(spc, pageable);
        List<GoodsViewDO> list = paged.getContent();
        List<Long> ids = list.stream().map(GoodsViewDO::getGoodsId).collect(Collectors.toList());
        List<GoodsDO> goodsDOList = getByIds(ids);
        List<GoodsListDTO> listDTOS = goodsAssembler.toGoodsListDTO(goodsDOList);
        for (GoodsListDTO dto : listDTOS) {
            Optional<GoodsViewDO> doo =
                    list.stream().filter(a -> a.getGoodsId().toString().equals(dto.getGoods().getId())).findFirst();
            doo.ifPresent(goodsViewDO -> dto.setViewDate(goodsViewDO.getCreationTime()));
        }
        Page<GoodsListDTO> result = new PageImpl<>(listDTOS, paged.getPageable(), paged.getTotalElements());
        return Result.success(result);
    }

    @Override
    public Result<Boolean> viewClear() {
        userCollectDAO.deleteByCreatorId(currentUser().getId());
        return Result.success(true);
    }

    private List<UserCollectDO> getCollects(List<Long> ids, Long userId) {
        Specification<UserCollectDO> spc = (x, y, z) -> {
            ArrayList<Predicate> list = new ArrayList<>();
            list.add(z.equal(x.get("creatorId"), userId));
            if (ids.size() > 0) {
                Expression<Long> exp = x.get("goodsId");
                list.add(exp.in(ids));
            }
            Predicate[] predicates = new Predicate[list.size()];
            return z.and(list.toArray(predicates));
        };
        return userCollectDAO.findAll(spc);
    }

    private GoodsDO getById(Long id) {
        Optional<GoodsDO> goodsDO = goodsDAO.findById(id);
        return goodsDO.orElse(null);
    }

    private List<GoodsDO> getByIds(List<Long> ids) {
        if (ids.size() == 0) {
            return new ArrayList<>();
        }
        Specification<GoodsDO> spc = (x, y, z) -> {
            ArrayList<Predicate> list = new ArrayList<>();
            if (ids.size() > 0) {
                Expression<Long> exp = x.get("id");
                list.add(exp.in(ids));
            }
            Predicate[] predicates = new Predicate[list.size()];
            return z.and(list.toArray(predicates));
        };
        return goodsDAO.findAll(spc);
    }
}
