package com.yufu.yepshop.application.impl;

import com.yufu.yepshop.application.RequirementService;
import com.yufu.yepshop.common.Constants;
import com.yufu.yepshop.common.Result;
import com.yufu.yepshop.config.YepxiaoConfig;
import com.yufu.yepshop.domain.service.UserDomainService;
import com.yufu.yepshop.domain.service.impl.BaseService;
import com.yufu.yepshop.persistence.DO.*;
import com.yufu.yepshop.persistence.converter.RequirementCommentConverter;
import com.yufu.yepshop.persistence.converter.RequirementConverter;
import com.yufu.yepshop.persistence.dao.*;
import com.yufu.yepshop.types.command.*;
import com.yufu.yepshop.types.dto.*;
import com.yufu.yepshop.types.enums.AuditState;
import com.yufu.yepshop.types.enums.RequirementState;
import com.yufu.yepshop.types.query.RequirementQuery;
import com.yufu.yepshop.types.value.SchoolValue;
import com.yufu.yepshop.types.value.UserValue;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Predicate;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author wang
 * @date 2022/2/16 23:35
 */
@Service
public class RequirementServiceImpl extends BaseService implements RequirementService {

    private final UserDomainService userDomainService;
    private final RequirementDAO requirementDAO;
    private final RequirementCommentDAO requirementCommentDAO;
    private final RequirementCommentReplyDAO requirementCommentReplyDAO;
    private final SchoolDAO schoolDAO;
    private final UserAccountDAO accountDAO;
    private final YepxiaoConfig yepxiaoConfig;

    private final RequirementConverter requirementConverter = RequirementConverter.INSTANCE;
    private final RequirementCommentConverter requirementCommentConverter = RequirementCommentConverter.INSTANCE;

    public RequirementServiceImpl(UserDomainService userDomainService,
                                  RequirementDAO requirementDAO,
                                  RequirementCommentDAO requirementCommentDAO,
                                  RequirementCommentReplyDAO requirementCommentReplyDAO,
                                  SchoolDAO schoolDAO, UserAccountDAO accountDAO,
                                  YepxiaoConfig yepxiaoConfig) {
        this.userDomainService = userDomainService;
        this.requirementDAO = requirementDAO;
        this.requirementCommentDAO = requirementCommentDAO;
        this.requirementCommentReplyDAO = requirementCommentReplyDAO;
        this.schoolDAO = schoolDAO;
        this.accountDAO = accountDAO;
        this.yepxiaoConfig = yepxiaoConfig;
    }

    @Override
    public Result<String> create(CreateRequirementCommand command) {
        RequirementDO entity = requirementConverter.toDO(command);
        //默认上架、审核通过
        entity.setRequirementState(RequirementState.UP);
        entity.setAuditState(AuditState.PENDING);
        requirementDAO.save(entity);
        Long id = entity.getId();
        return Result.success(id.toString(), "发布成功");
    }

    @Override
    public Result<Boolean> update(Long id, CreateRequirementCommand command) {
        RequirementDO doo = getById(id);
        if (doo != null) {
            requirementConverter.toDO(command, doo);
            requirementDAO.save(doo);
            return Result.success(true);
        }
        return Result.fail("求购不存在！");
    }

    @Override
    public Result<Boolean> update(Long id, RequirementState state) {
        RequirementDO doo = getById(id);
        if (doo != null) {
            doo.setRequirementState(state);
            requirementDAO.save(doo);
            return Result.success(true);
        }
        return Result.fail("求购不存在！");
    }

    @Override
    public Result<Boolean> delete(Long id) {
        requirementDAO.deleteById(id);
        return Result.success(true);
    }

    @Override
    public Result<Boolean> block(Long id) {
        RequirementDO doo = getById(id);
        if (doo != null) {
            doo.setAuditState(AuditState.BLOCK);
            requirementDAO.save(doo);
            return Result.success(true);
        }
        return Result.fail("求购不存在！");
    }

    @Override
    public Result<Boolean> approved(Long id) {
        RequirementDO doo = getById(id);
        if (doo != null) {
            doo.setAuditState(AuditState.SUCCESS);
            requirementDAO.save(doo);
            return Result.success(true);
        }
        return Result.fail("求购不存在！");
    }

    @Override
    public Result<Page<RequirementListDTO>> pagedList(Long creatorId, Integer page, Integer perPage, String requirementState) {
        Pageable pageable = PageRequest.of(page, perPage, Sort.Direction.DESC, "id");
        Specification<RequirementDO> spc = (x, y, z) -> {
            ArrayList<Predicate> list = new ArrayList<>();
            list.add(z.equal(x.get("creatorId"), creatorId));
            if (!Constants.QUERY_ALL.equalsIgnoreCase(requirementState)) {
                list.add(z.equal(x.get("requirementState"), RequirementState.valueOf(requirementState)));
            }
            Predicate[] predicates = new Predicate[list.size()];
            return z.and(list.toArray(predicates));
        };

        Page<RequirementListDTO> paged =
                requirementDAO.findAll(spc, pageable).map(this::convert);
        return Result.success(paged);
    }

    @Override
    public Result<RequirementListDTO> get(Long id) {
        RequirementDO requirementDO = getById(id);
        if (requirementDO != null) {
            RequirementListDTO result = requirementConverter.toListDTO(requirementDO);
            builderSchool(schoolDAO, result.getSchool());
            buildUser(accountDAO,result.getUser());
            return Result.success(result);
        } else {
            return Result.fail("求购不存在！");
        }
    }

    @Override
    public Result<Page<RequirementListDTO>> search(RequirementQuery query) {
        Sort.Direction sortDirection = Sort.Direction.DESC;
        String column = "id";

        Pageable pageable = PageRequest.of(query.getPage(), query.getPerPage(), sortDirection, column);
        Specification<RequirementDO> spc = (x, y, z) -> {
            ArrayList<Predicate> list = new ArrayList<>();
            if (StringUtils.isEmpty(query.getRequirementState())) {
                list.add(z.equal(x.get("requirementState"), RequirementState.UP));
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
        Page<RequirementListDTO> paged =
                requirementDAO.findAll(spc, pageable).map(this::convert);
        return Result.success(paged);
    }

    @Override
    public Result<Page<RequirementListDTO>> tip(Integer page, Integer perPage) {
        Long userId = currentUser().getId();
        if (userId == null) {
            return Result.success();
        }
        List<SchoolValue> schools = userDomainService.schools(userId);
        Specification<RequirementDO> spc = (x, y, z) -> {
            ArrayList<Predicate> list = new ArrayList<>();
            list.add(z.equal(x.get("requirementState"), RequirementState.UP));
            list.add(x.get("auditState").in(yepxiaoConfig.status()));

            if (schools.size() > 0) {
                Expression<String> exp = x.get("schoolId");
                list.add(exp.in(schools.stream().map(SchoolValue::getId).collect(Collectors.toList())));
            }
            Predicate[] predicates = new Predicate[list.size()];
            return z.and(list.toArray(predicates));
        };
        Pageable pageable = PageRequest.of(page, perPage, Sort.Direction.DESC, "id");
        Page<RequirementListDTO> paged =
                requirementDAO.findAll(spc, pageable).map(this::justConvert);
        return Result.success(paged);
    }

    @Override
    public Result<String> comment(Long id, CreateCommentCommand command) {
        RequirementCommentDO commentDO = new RequirementCommentDO();
        commentDO.setRequirementId(id);
        commentDO.setText(command.getText());
        commentDO.setAuditState(AuditState.PENDING);
        requirementCommentDAO.save(commentDO);
        return Result.success(commentDO.getId().toString(), "评论成功");
    }

    @Override
    public Result<Boolean> commentDelete(Long id, Long commentId) {
        requirementCommentReplyDAO.deleteByCommentId(commentId);
        requirementCommentDAO.deleteById(commentId);
        return Result.success(true);
    }

    @Override
    public Result<String> commentReply(Long id, Long commentId, CreateCommentReplyCommand command) {
        RequirementCommentReplyDO commentReplyDO = new RequirementCommentReplyDO();
        commentReplyDO.setCommentId(commentId);
        commentReplyDO.setText(command.getText());
        commentReplyDO.setReplyToUserId(Long.parseLong(command.getReplyToUserId()));
        commentReplyDO.setAuditState(AuditState.PENDING);
        requirementCommentReplyDAO.save(commentReplyDO);
        requirementCommentDAO.updateTotalReply(commentId, 1);
        return Result.success(commentReplyDO.getId().toString(), "评论回复成功");
    }

    @Override
    public Result<Boolean> commentReplyDelete(Long id, Long commentId, Long replyId) {
        requirementCommentReplyDAO.deleteById(replyId);
        requirementCommentDAO.updateTotalReply(commentId, -1);
        return Result.success(true);
    }

    @Override
    public Result<Page<RequirementCommentDTO>> comments(Long id, Integer page, Integer perPage) {
        Sort.Direction sortDirection = Sort.Direction.DESC;
        String column = "id";
        Specification<RequirementCommentDO> spc = (x, y, z) -> {
            ArrayList<Predicate> list = new ArrayList<>();
            list.add(z.equal(x.get("requirementId"), id));
            list.add(x.get("auditState").in(yepxiaoConfig.status()));
            Predicate[] predicates = new Predicate[list.size()];
            return z.and(list.toArray(predicates));
        };
        Pageable pageable = PageRequest.of(page, perPage, sortDirection, column);
        Page<RequirementCommentDO> paged = requirementCommentDAO.findAll(spc, pageable);
        List<RequirementCommentDO> list = paged.getContent();
        if (list.size() > 0) {
            List<RequirementCommentDTO> comments = new ArrayList<>();
            Set<Long> userIds = list.stream().map(RequirementCommentDO::getCreatorId).collect(Collectors.toSet());
            List<UserValue> users = userDomainService.users(new ArrayList<>(userIds));

            for (RequirementCommentDO doo : list) {
                RequirementCommentDTO dto = requirementCommentConverter.toDTO(doo);
                dto.setUser(users.stream().filter(s -> s.getId().equals(dto.getUser().getId())).findFirst().orElse(null));
                comments.add(dto);
            }
            Page<RequirementCommentDTO> result = new PageImpl<>(comments, paged.getPageable(), paged.getTotalElements());
            return Result.success(result);
        }
        return Result.success(null);
    }

    @Override
    public Result<Page<CommentReplyDTO>> commentReplyList(Long id, Long commentId, Integer page, Integer perPage) {
        Sort.Direction sortDirection = Sort.Direction.ASC;
        String column = "id";
        Specification<RequirementCommentReplyDO> spc = (x, y, z) -> {
            ArrayList<Predicate> list = new ArrayList<>();
            list.add(z.equal(x.get("commentId"), commentId));
            Predicate[] predicates = new Predicate[list.size()];
            return z.and(list.toArray(predicates));
        };
        Pageable pageable = PageRequest.of(page, perPage, sortDirection, column);
        Page<RequirementCommentReplyDO> paged = requirementCommentReplyDAO.findAll(spc, pageable);
        List<RequirementCommentReplyDO> list = paged.getContent();
        if (list.size() > 0) {
            List<CommentReplyDTO> comments = new ArrayList<>();
            Set<Long> userIds = new HashSet<>();
            List<Long> creatorIds = list.stream().map(RequirementCommentReplyDO::getCreatorId).collect(Collectors.toList());
            List<Long> replyToUserIds = list.stream().map(RequirementCommentReplyDO::getReplyToUserId).collect(Collectors.toList());
            userIds.addAll(creatorIds);
            userIds.addAll(replyToUserIds);
            List<UserValue> users = userDomainService.users(new ArrayList<>(userIds));

            for (RequirementCommentReplyDO doo : list) {
                CommentReplyDTO dto = requirementCommentConverter.toReplyDTO(doo);
                dto.setUser(users.stream().filter(s -> s.getId().equals(dto.getUser().getId())).findFirst().orElse(null));
                dto.setToUser(users.stream().filter(s -> s.getId().equals(dto.getToUser().getId())).findFirst().orElse(null));
                comments.add(dto);
            }
            Page<CommentReplyDTO> result = new PageImpl<>(comments, paged.getPageable(), paged.getTotalElements());
            return Result.success(result);
        }
        return Result.success(null);
    }

    private RequirementListDTO justConvert(RequirementDO gDo) {
        return requirementConverter.toListDTO(gDo);
    }

    private RequirementDO getById(Long id) {
        Optional<RequirementDO> goodsDO = requirementDAO.findById(id);
        return goodsDO.orElse(null);
    }

    private RequirementListDTO convert(RequirementDO gDo) {
        RequirementListDTO dto = requirementConverter.toListDTO(gDo);
        builderSchool(schoolDAO, dto.getSchool());
        buildUser(accountDAO, dto.getUser());
        return dto;
    }
}
