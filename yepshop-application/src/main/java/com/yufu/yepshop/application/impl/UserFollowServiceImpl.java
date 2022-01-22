package com.yufu.yepshop.application.impl;

import com.yufu.yepshop.domain.service.impl.BaseService;
import com.yufu.yepshop.application.UserFollowService;
import com.yufu.yepshop.common.Result;
import com.yufu.yepshop.persistence.DO.UserDO;
import com.yufu.yepshop.persistence.DO.UserFollowDO;
import com.yufu.yepshop.persistence.dao.UserDAO;
import com.yufu.yepshop.persistence.dao.UserFollowDAO;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.Predicate;
import java.util.ArrayList;

/**
 * @author wang
 * @date 2022/1/17 23:07
 */
@Service
public class UserFollowServiceImpl extends BaseService implements UserFollowService {

    private final UserFollowDAO userFollowDAO;
    private final UserDAO userDAO;

    public UserFollowServiceImpl(
            UserFollowDAO userFollowDAO,
            UserDAO userDAO) {
        this.userFollowDAO = userFollowDAO;
        this.userDAO = userDAO;
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
}
