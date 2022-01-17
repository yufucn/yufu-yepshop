package com.yufu.yepshop.application.impl;

import com.yufu.yepshop.application.BaseService;
import com.yufu.yepshop.application.UserFollowService;
import com.yufu.yepshop.common.Result;
import com.yufu.yepshop.persistence.DO.UserAddressDO;
import com.yufu.yepshop.persistence.DO.UserFollowDO;
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

    public UserFollowServiceImpl(UserFollowDAO userFollowDAO) {
        this.userFollowDAO = userFollowDAO;
    }

    @Override
    public Result<Boolean> followed(Long id) {
        UserFollowDO doo = find(id);
        if (doo != null) {
            return Result.success(true,"已关注");
        }
        return Result.success(false,"未关注");
    }

    @Override
    public Result<Boolean> follow(Long id) {
        if (find(id) == null) {
            UserFollowDO doo = new UserFollowDO();
            doo.setUserId(id);
            userFollowDAO.save(doo);
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
        }
        return Result.success("取消关注成功！");
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
