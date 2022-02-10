package com.yufu.yepshop.application.impl;

import com.yufu.yepshop.application.UserService;
import com.yufu.yepshop.common.Result;
import com.yufu.yepshop.domain.service.UserDomainService;
import com.yufu.yepshop.domain.service.impl.BaseService;
import com.yufu.yepshop.persistence.DO.UserAccountDO;
import com.yufu.yepshop.persistence.DO.UserDO;
import com.yufu.yepshop.persistence.DO.UserFollowDO;
import com.yufu.yepshop.persistence.converter.UserAccountConvert;
import com.yufu.yepshop.persistence.dao.UserAccountDAO;
import com.yufu.yepshop.persistence.dao.UserDAO;
import com.yufu.yepshop.persistence.dao.UserFollowDAO;
import com.yufu.yepshop.types.dto.UserDetailDTO;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @author wang
 * @date 2022/1/23 0:46
 */
@Service
public class UserServiceImpl  extends BaseService implements UserService {

    private final UserDomainService domainService;
    private final UserFollowDAO userFollowDAO;
    private final UserDAO userDAO;
    private final UserAccountDAO userAccountDAO;
    private final UserDomainService userDomainService;
    private final UserAccountConvert userAccountConvert = UserAccountConvert.INSTANCE;

    public UserServiceImpl(
            UserDomainService domainService,
            UserFollowDAO userFollowDAO,
            UserDAO userDAO,
            UserAccountDAO userAccountDAO,
            UserDomainService userDomainService) {
        this.domainService = domainService;
        this.userFollowDAO = userFollowDAO;
        this.userDAO = userDAO;
        this.userAccountDAO = userAccountDAO;
        this.userDomainService = userDomainService;
    }

    @Override
    public Result<UserDetailDTO> user(Long id) {
        return Result.success(domainService.userDetail(id));
    }

    @Override
    public Result<Boolean> followed(Long id) {
        UserFollowDO doo = find(id);
        if (doo != null) {
            return Result.success(true, "已关注");
        }
        return Result.success(false, "未关注");
    }

    @Override
    public Result<Boolean> follow(Long id) {
        if (find(id) == null) {
            UserFollowDO doo = new UserFollowDO();
            doo.setUserId(id);
            userFollowDAO.save(doo);

            updateFollowers(id, 1);
            updateFollowing(currentUser().getId(), 1);

            return Result.success("关注成功！");
        } else {
            return Result.fail("已关注，请勿重复关注！");
        }
    }

    @Override
    public Result<Boolean> unfollow(Long id) {
        UserFollowDO doo = find(id);
        if (doo != null) {
            userFollowDAO.delete(doo);
            updateFollowers(id, -1);
            updateFollowing(currentUser().getId(), -1);
        }
        return Result.success("取消关注成功！");
    }

    @Override
    public Result<Page<UserDetailDTO>> following(Long userId, Integer page, Integer perPage) {
        Sort.Direction sortDirection = Sort.Direction.DESC;
        String column = "id";
        Pageable pageable = PageRequest.of(page, perPage, sortDirection, column);
        Specification<UserFollowDO> spc = (x, y, z) -> {
            ArrayList<Predicate> list = new ArrayList<>();
            list.add(z.equal(x.get("creatorId"), userId));
            Predicate[] predicates = new Predicate[list.size()];
            return z.and(list.toArray(predicates));
        };
        Page<UserFollowDO> paged = userFollowDAO.findAll(spc, pageable);
        List<UserFollowDO> list = paged.getContent();
        List<Long> ids = list.stream().map(UserFollowDO::getUserId).collect(Collectors.toList());
        List<UserDetailDTO> detailDTOS = new ArrayList<>();
        for (Long id : ids) {
            detailDTOS.add(userDomainService.userDetail(id));
        }

        List<UserFollowDO> userFollowDOS = getUserFollowingByIds(currentUser().getId(), ids);
        for (UserDetailDTO dto : detailDTOS) {
            Optional<UserFollowDO> doo =
                    userFollowDOS.stream().filter(a -> a.getUserId().toString().equals(dto.getAccount().getId())).findFirst();
            if (doo.isPresent()) {
                dto.getAccount().setFollowed(true);
            }
        }

        Page<UserDetailDTO> result = new PageImpl<>(detailDTOS, paged.getPageable(), paged.getTotalElements());
        return Result.success(result);
    }

    @Override
    public Result<Page<UserDetailDTO>> follwers(Long userId, Integer page, Integer perPage) {
        Sort.Direction sortDirection = Sort.Direction.DESC;
        String column = "id";
        Pageable pageable = PageRequest.of(page, perPage, sortDirection, column);
        Specification<UserFollowDO> spc = (x, y, z) -> {
            ArrayList<Predicate> list = new ArrayList<>();
            list.add(z.equal(x.get("userId"), userId));
            Predicate[] predicates = new Predicate[list.size()];
            return z.and(list.toArray(predicates));
        };
        Page<UserFollowDO> paged = userFollowDAO.findAll(spc, pageable);
        List<UserFollowDO> list = paged.getContent();
        List<Long> ids = list.stream().map(UserFollowDO::getCreatorId).collect(Collectors.toList());
        List<UserDetailDTO> detailDTOS = new ArrayList<>();
        for (Long id : ids) {
            detailDTOS.add(userDomainService.userDetail(id));
        }


        List<UserFollowDO> userFollowDOS = getUserFollowingByIds(currentUser().getId(), ids);
        for (UserDetailDTO dto : detailDTOS) {
            Optional<UserFollowDO> doo =
                    userFollowDOS.stream().filter(a -> a.getUserId().toString().equals(dto.getAccount().getId())).findFirst();
            if (doo.isPresent()) {
                dto.getAccount().setFollowed(true);
            }
        }

        Page<UserDetailDTO> result = new PageImpl<>(detailDTOS, paged.getPageable(), paged.getTotalElements());
        return Result.success(result);
    }

    private void updateFollowers(Long id, Integer number) {
        UserDO userDO = initUser(id);
        Integer followers = userDO.getFollowers();
        followers = followers + number;
        userDO.setFollowers(followers);
        userDAO.save(userDO);
    }

    private void updateFollowing(Long id, Integer number) {
        UserDO userDO = initUser(id);
        Integer following = userDO.getFollowing();
        following = following + number;
        userDO.setFollowing(following);
        userDAO.save(userDO);
    }

    private UserDO initUser(Long id) {
        UserDO userDO = userDAO.findById(id).orElse(null);
        if (userDO == null) {
            userDO = new UserDO();
            userDO.setId(id);
            userDO.setTotalBuy(0);
            userDO.setTotalCollect(0);
            userDO.setTotalGoods(0);

            userDO.setTotalSold(0);
            userDO.setTotalUp(0);
            userDO.setTotalView(0);
            userDO.setFollowers(0);
            userDO.setFollowing(0);
            userDAO.save(userDO);
        }
        return userDO;
    }

    private UserFollowDO find(Long id) {
        Specification<UserFollowDO> spc = (x, y, z) -> {
            ArrayList<Predicate> list = new ArrayList<>();
            list.add(z.equal(x.get("creatorId"), currentUser().getId()));
            list.add(z.equal(x.get("userId"), id));
            Predicate[] predicates = new Predicate[list.size()];
            return z.and(list.toArray(predicates));
        };
        return userFollowDAO.findOne(spc).orElse(null);
    }

    private List<UserFollowDO> getUserFollowingByIds(Long userId, List<Long> ids) {
        if (ids.size() == 0) {
            return new ArrayList<>();
        }
        Specification<UserFollowDO> spc1 = (x, y, z) -> {
            ArrayList<Predicate> list1 = new ArrayList<>();
            list1.add(z.equal(x.get("creatorId"), userId));
            Expression<Long> exp = x.get("userId");
            list1.add(exp.in(ids));
            Predicate[] predicates = new Predicate[list1.size()];
            return z.and(list1.toArray(predicates));
        };
        return userFollowDAO.findAll(spc1);
    }

    private List<UserAccountDO> getUserAccountByIds(List<Long> ids) {
        if (ids.size() == 0) {
            return new ArrayList<>();
        }
        Specification<UserAccountDO> spc = (x, y, z) -> {
            ArrayList<Predicate> list = new ArrayList<>();
            if (ids.size() > 0) {
                Expression<Long> exp = x.get("id");
                list.add(exp.in(ids));
            }
            Predicate[] predicates = new Predicate[list.size()];
            return z.and(list.toArray(predicates));
        };
        return userAccountDAO.findAll(spc);
    }
}
