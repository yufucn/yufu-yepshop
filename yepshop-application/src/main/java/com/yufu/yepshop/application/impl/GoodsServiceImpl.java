package com.yufu.yepshop.application.impl;

import com.yufu.yepshop.config.YepxiaoConfig;
import com.yufu.yepshop.domain.service.UserDomainService;
import com.yufu.yepshop.domain.service.impl.BaseService;
import com.yufu.yepshop.application.GoodsService;
import com.yufu.yepshop.common.Constants;
import com.yufu.yepshop.common.Result;
import com.yufu.yepshop.persistence.DO.*;
import com.yufu.yepshop.persistence.converter.GoodsCommentConverter;
import com.yufu.yepshop.persistence.converter.GoodsConverter;
import com.yufu.yepshop.persistence.dao.*;
import com.yufu.yepshop.types.command.CreateGoodsCommand;
import com.yufu.yepshop.types.command.CreateCommentCommand;
import com.yufu.yepshop.types.command.CreateCommentReplyCommand;
import com.yufu.yepshop.types.command.UpdateGoodsCommand;
import com.yufu.yepshop.types.dto.CommentDTO;
import com.yufu.yepshop.types.dto.CommentReplyDTO;
import com.yufu.yepshop.types.dto.GoodsDTO;
import com.yufu.yepshop.types.dto.GoodsListDTO;
import com.yufu.yepshop.types.enums.AuditState;
import com.yufu.yepshop.types.enums.GoodsState;
import com.yufu.yepshop.types.enums.SellerType;
import com.yufu.yepshop.types.enums.SortFilter;
import com.yufu.yepshop.types.query.CommentQuery;
import com.yufu.yepshop.types.query.GoodsQuery;
import com.yufu.yepshop.types.value.SchoolValue;
import com.yufu.yepshop.types.value.Seller;
import com.yufu.yepshop.types.value.UserValue;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Predicate;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
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
    private final GoodsCommentDAO goodsCommentDAO;
    private final GoodsCommentReplyDAO goodsCommentReplyDAO;
    private final UserDomainService userDomainService;

    private final GoodsCommentConverter converter = GoodsCommentConverter.INSTANCE;

    private final GoodsConverter goodsAssembler = GoodsConverter.INSTANCE;

    private final YepxiaoConfig yepxiaoConfig;

    public GoodsServiceImpl(
            GoodsDAO goodsDAO,
            UserAccountDAO accountDAO,
            GoodsDetailDAO goodsDetailDAO,
            SchoolDAO schoolDAO,
            UserCollectDAO userCollectDAO,
            GoodsViewDAO goodsViewDAO,
            UserDomainService userDomainService,
            GoodsCommentDAO goodsCommentDAO,
            GoodsCommentReplyDAO goodsCommentReplyDAO,
            YepxiaoConfig yepxiaoConfig) {
        this.accountDAO = accountDAO;
        this.goodsDAO = goodsDAO;
        this.goodsDetailDAO = goodsDetailDAO;
        this.schoolDAO = schoolDAO;
        this.userCollectDAO = userCollectDAO;
        this.goodsViewDAO = goodsViewDAO;
        this.goodsCommentDAO = goodsCommentDAO;
        this.userDomainService = userDomainService;
        this.goodsCommentReplyDAO = goodsCommentReplyDAO;
        this.yepxiaoConfig = yepxiaoConfig;
    }

    @Override
    public Result<String> create(CreateGoodsCommand command) {
        UserAccountDO user = currentUser();
        GoodsDO entity = goodsAssembler.toDO(command);
        entity.setTitle(command.getTitleFromText());
        entity.setSellerId(user.getId());
        entity.setSellerType(SellerType.C);
        entity.setTotalCollect(0);
        //默认上架、审核通过
        entity.setAuditState(AuditState.PENDING);
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
            goodsDO.setAuditState(AuditState.PENDING);
            goodsDAO.save(goodsDO);
            GoodsDetailDO detailDO = new GoodsDetailDO();

            detailDO.setId(goodsDO.getId());
            detailDO.setText(command.getText());
            detailDO.setImageUrls(String.join(",", command.getImageUrlList()));
            goodsDetailDAO.save(detailDO);
            userDomainService.bindSchool(Long.parseLong(command.getSchoolId()));
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
    public Result<Boolean> block(Long id) {
        GoodsDO goodsDO = getById(id);
        if (goodsDO != null) {
            goodsDO.setAuditState(AuditState.BLOCK);
            goodsDAO.save(goodsDO);
            return Result.success(true);
        }
        return Result.fail("商品不存在！");
    }

    @Override
    public Result<Boolean> approved(Long id) {
        GoodsDO goodsDO = getById(id);
        if (goodsDO != null) {
            goodsDO.setAuditState(AuditState.SUCCESS);
            goodsDAO.save(goodsDO);
            return Result.success(true);
        }
        return Result.fail("商品不存在！");
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

    private GoodsListDTO justConvert(GoodsDO gDo) {
        return goodsAssembler.toListDTO(gDo);
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
            if (StringUtils.isEmpty(query.getGoodsState())) {
                list.add(z.equal(x.get("goodsState"), GoodsState.UP));
            } else {
                if (!"ALL".equals(query.getGoodsState())) {
                    list.add(z.equal(x.get("goodsState"), GoodsState.valueOf(query.getGoodsState())));
                }
            }

            if (StringUtils.isEmpty(query.getAuditState())) {
                list.add(x.get("auditState").in(yepxiaoConfig.status()));
            } else {
                if (!"ALL".equals(query.getAuditState())) {
                    list.add(z.equal(x.get("auditState"), AuditState.valueOf(query.getAuditState())));
                }
            }
            if (!StringUtils.isEmpty(query.getKeyword())) {
                list.add(z.like(x.get("title"), "%" + query.getKeyword() + "%"));
            }
            if (query.getSchoolIds() != null && query.getSchoolIds().size() > 0) {
                Expression<String> exp = x.get("schoolId");
                list.add(exp.in(query.getSchoolIds()));
            }
            if (query.getCategoryIds() != null && query.getCategoryIds().size() > 0) {
                Expression<String> exp = x.get("categoryId");
                list.add(exp.in(query.getCategoryIds()));
            }
            if (query.getConditionIds() != null && query.getConditionIds().size() > 0) {
                Expression<String> exp = x.get("conditionId");
                list.add(exp.in(query.getConditionIds()));
            }
            if (query.getStart()!=null && query.getEnd()!=null){
                try {
                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    list.add(z.greaterThanOrEqualTo(x.get("creationTime"), simpleDateFormat.parse(query.getStart())));
                    list.add(z.lessThanOrEqualTo(x.get("creationTime"), simpleDateFormat.parse(query.getEnd())));
                } catch (ParseException e) {
                }

            }
            Predicate[] predicates = new Predicate[list.size()];
            return z.and(list.toArray(predicates));
        };
        Page<GoodsListDTO> paged =
                goodsDAO.findAll(spc, pageable).map(this::convert);
        return Result.success(paged);
    }

    @Override
    public Result<Page<GoodsListDTO>> tipGoods(Integer page, Integer perPage) {
        Long userId = currentUser().getId();
        List<SchoolValue> schools = userDomainService.schools(userId);
        Specification<GoodsDO> spc = (x, y, z) -> {
            ArrayList<Predicate> list = new ArrayList<>();
            list.add(z.equal(x.get("goodsState"), GoodsState.UP));
            list.add(x.get("auditState").in(yepxiaoConfig.status()));
            if (schools.size() > 0) {
                Expression<String> exp = x.get("schoolId");
                list.add(exp.in(schools.stream().map(SchoolValue::getId).collect(Collectors.toList())));
            }
            Predicate[] predicates = new Predicate[list.size()];
            return z.and(list.toArray(predicates));
        };
        Pageable pageable = PageRequest.of(page, perPage, Sort.Direction.DESC, "id");
        Page<GoodsListDTO> paged =
                goodsDAO.findAll(spc, pageable).map(this::justConvert);
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
        Long uerId = currentUser().getId();
        goodsViewDAO.clear(uerId);
        userDomainService.clearTotalView(uerId);
        return Result.success(true);
    }

    @Override
    public Result<String> comment(Long id, CreateCommentCommand command) {
        GoodsCommentDO commentDO = new GoodsCommentDO();
        commentDO.setGoodsId(id);
        commentDO.setText(command.getText());
        commentDO.setAuditState(AuditState.PENDING);
        goodsCommentDAO.save(commentDO);
        return Result.success(commentDO.getId().toString(), "评论成功");
    }

    @Override
    public Result<Boolean> commentDelete(Long id, Long commentId) {
        goodsCommentReplyDAO.deleteByCommentId(commentId);
        goodsCommentDAO.deleteById(commentId);
        return Result.success(true);
    }

    @Override
    public Result<String> commentReply(
            Long id,
            Long commentId,
            CreateCommentReplyCommand command) {
        GoodsCommentReplyDO commentReplyDO = new GoodsCommentReplyDO();
        commentReplyDO.setCommentId(commentId);
        commentReplyDO.setText(command.getText());
        commentReplyDO.setReplyToUserId(Long.parseLong(command.getReplyToUserId()));
        commentReplyDO.setAuditState(AuditState.PENDING);
        goodsCommentReplyDAO.save(commentReplyDO);
        goodsCommentDAO.updateTotalReply(commentId, 1);
        return Result.success(commentReplyDO.getId().toString(), "评论回复成功");
    }

    @Override
    public Result<Boolean> commentReplyDelete(Long id, Long commentId, Long replyId) {
        goodsCommentReplyDAO.deleteById(replyId);
        goodsCommentDAO.updateTotalReply(commentId, -1);
        return Result.success(true);
    }

    @Override
    public Result<Page<CommentDTO>> commentsGoods(Long id, Integer page, Integer perPage) {
        Sort.Direction sortDirection = Sort.Direction.DESC;
        String column = "id";
        Specification<GoodsCommentDO> spc = (x, y, z) -> {
            ArrayList<Predicate> list = new ArrayList<>();
            list.add(z.equal(x.get("goodsId"), id));
            list.add(x.get("auditState").in(yepxiaoConfig.status()));
            Predicate[] predicates = new Predicate[list.size()];
            return z.and(list.toArray(predicates));
        };
        Pageable pageable = PageRequest.of(page, perPage, sortDirection, column);
        Page<GoodsCommentDO> paged = goodsCommentDAO.findAll(spc, pageable);
        List<GoodsCommentDO> list = paged.getContent();
        if (list.size() > 0) {
            List<CommentDTO> comments = new ArrayList<>();
            Set<Long> userIds = list.stream().map(GoodsCommentDO::getCreatorId).collect(Collectors.toSet());
            List<UserValue> users = userDomainService.users(new ArrayList<>(userIds));

            for (GoodsCommentDO doo : list) {
                CommentDTO dto = converter.toDTO(doo);
                dto.setUser(users.stream().filter(s -> s.getId().equals(dto.getUser().getId())).findFirst().orElse(null));
                comments.add(dto);
            }
            Page<CommentDTO> result = new PageImpl<>(comments, paged.getPageable(), paged.getTotalElements());
            return Result.success(result);
        }
        return Result.success(null);
    }

    @Override
    public Result<Page<CommentDTO>> commentsGoods(CommentQuery query) {
        Sort.Direction sortDirection = Sort.Direction.DESC;
        String column = "id";
        Specification<GoodsCommentDO> spc = (x, y, z) -> {
            ArrayList<Predicate> list = new ArrayList<>();
            if (StringUtils.isEmpty(query.getAuditState())) {
                list.add(x.get("auditState").in(yepxiaoConfig.status()));
            } else {
                if (!"ALL".equals(query.getAuditState())) {
                    list.add(z.equal(x.get("auditState"), AuditState.valueOf(query.getAuditState())));
                }
            }
            Predicate[] predicates = new Predicate[list.size()];
            return z.and(list.toArray(predicates));
        };
        Pageable pageable = PageRequest.of(query.getPage(), query.getPerPage(), sortDirection, column);
        Page<GoodsCommentDO> paged = goodsCommentDAO.findAll(spc, pageable);
        List<GoodsCommentDO> list = paged.getContent();
        if (list.size() > 0) {
            List<CommentDTO> comments = new ArrayList<>();
            for (GoodsCommentDO doo : list) {
                CommentDTO dto = converter.toDTO(doo);
                comments.add(dto);
            }
            Page<CommentDTO> result = new PageImpl<>(comments, paged.getPageable(), paged.getTotalElements());
            return Result.success(result);
        }
        return Result.success(null);
    }

    @Override
    public Result<Page<CommentReplyDTO>> commentReplyGoodsList(Long id, Long commentId, Integer page, Integer perPage) {
        Sort.Direction sortDirection = Sort.Direction.ASC;
        String column = "id";
        Specification<GoodsCommentReplyDO> spc = (x, y, z) -> {
            ArrayList<Predicate> list = new ArrayList<>();
            list.add(z.equal(x.get("commentId"), commentId));
            Predicate[] predicates = new Predicate[list.size()];
            return z.and(list.toArray(predicates));
        };
        Pageable pageable = PageRequest.of(page, perPage, sortDirection, column);
        Page<GoodsCommentReplyDO> paged = goodsCommentReplyDAO.findAll(spc, pageable);
        List<GoodsCommentReplyDO> list = paged.getContent();
        if (list.size() > 0) {
            List<CommentReplyDTO> comments = new ArrayList<>();
            Set<Long> userIds = new HashSet<>();
            List<Long> creatorIds = list.stream().map(GoodsCommentReplyDO::getCreatorId).collect(Collectors.toList());
            List<Long> replyToUserIds = list.stream().map(GoodsCommentReplyDO::getReplyToUserId).collect(Collectors.toList());
            userIds.addAll(creatorIds);
            userIds.addAll(replyToUserIds);
            List<UserValue> users = userDomainService.users(new ArrayList<>(userIds));

            for (GoodsCommentReplyDO doo : list) {
                CommentReplyDTO dto = converter.toReplyDTO(doo);
                dto.setUser(users.stream().filter(s -> s.getId().equals(dto.getUser().getId())).findFirst().orElse(null));
                dto.setToUser(users.stream().filter(s -> s.getId().equals(dto.getToUser().getId())).findFirst().orElse(null));
                comments.add(dto);
            }
            Page<CommentReplyDTO> result = new PageImpl<>(comments, paged.getPageable(), paged.getTotalElements());
            return Result.success(result);
        }
        return Result.success(null);
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
