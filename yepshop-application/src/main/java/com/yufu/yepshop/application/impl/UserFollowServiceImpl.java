package com.yufu.yepshop.application.impl;

import com.yufu.yepshop.domain.service.impl.BaseService;
import com.yufu.yepshop.application.UserFollowService;
import com.yufu.yepshop.common.Result;
import com.yufu.yepshop.persistence.DO.*;
import com.yufu.yepshop.persistence.converter.UserAccountConvert;
import com.yufu.yepshop.persistence.dao.UserAccountDAO;
import com.yufu.yepshop.persistence.dao.UserDAO;
import com.yufu.yepshop.persistence.dao.UserFollowDAO;
import com.yufu.yepshop.types.dto.GoodsListDTO;
import com.yufu.yepshop.types.dto.UserAccountDTO;
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
 * @date 2022/1/17 23:07
 */
@Service
public class UserFollowServiceImpl extends BaseService implements UserFollowService {

    private final UserFollowDAO userFollowDAO;
    private final UserDAO userDAO;
    private final UserAccountDAO userAccountDAO;
    private final UserAccountConvert userAccountConvert = UserAccountConvert.INSTANCE;

    public UserFollowServiceImpl(
            UserFollowDAO userFollowDAO,
            UserDAO userDAO,
            UserAccountDAO userAccountDAO) {
        this.userFollowDAO = userFollowDAO;
        this.userDAO = userDAO;
        this.userAccountDAO = userAccountDAO;
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
    public Result<Page<UserAccountDTO>> following(Long userId, Integer page, Integer perPage) {
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
        List<UserAccountDO> dOList = getUserAccountByIds(ids);
        List<UserAccountDTO> listDTOS = userAccountConvert.toListDTO(dOList);
        Long currentUserId = currentUser().getId();
        if (!currentUserId.equals(userId)) {
            List<UserFollowDO> userFollowDOS = getUserFollowingByIds(currentUserId, ids);
            for (UserAccountDTO dto : listDTOS) {
                Optional<UserFollowDO> doo =
                        userFollowDOS.stream().filter(a -> a.getUserId().toString().equals(dto.getId())).findFirst();
                if (doo.isPresent()) {
                    dto.setFollowed(true);
                }
            }
        }
        Page<UserAccountDTO> result = new PageImpl<>(listDTOS, paged.getPageable(), paged.getTotalElements());
        return Result.success(result);
    }

    @Override
    public Result<Page<UserAccountDTO>> follwers(Long userId, Integer page, Integer perPage) {
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
        List<UserAccountDO> dOList = getUserAccountByIds(ids);
        List<UserAccountDTO> listDTOS = userAccountConvert.toListDTO(dOList);

        Long currentUserId = currentUser().getId();
        if (!currentUserId.equals(userId)) {
            List<UserFollowDO> userFollowDOS = getUserFollowingByIds(currentUserId, ids);
            for (UserAccountDTO dto : listDTOS) {
                Optional<UserFollowDO> doo =
                        userFollowDOS.stream().filter(a -> a.getUserId().toString().equals(dto.getId())).findFirst();
                if (doo.isPresent()) {
                    dto.setFollowed(true);
                }
            }
        }

        Page<UserAccountDTO> result = new PageImpl<>(listDTOS, paged.getPageable(), paged.getTotalElements());
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
        List<UserFollowDO> list1 = userFollowDAO.findAll(spc1);
        return list1;
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
