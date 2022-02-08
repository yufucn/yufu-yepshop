package com.yufu.yepshop.application.impl;

import com.yufu.yepshop.domain.service.impl.BaseService;
import com.yufu.yepshop.application.UserCollectService;
import com.yufu.yepshop.common.Result;
import com.yufu.yepshop.persistence.DO.UserCollectDO;
import com.yufu.yepshop.persistence.dao.GoodsDAO;
import com.yufu.yepshop.persistence.dao.UserCollectDAO;
import com.yufu.yepshop.persistence.dao.UserDAO;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;

/**
 * @author wang
 * @date 2022/1/16 10:53
 */
@Service
public class UserCollectServiceImpl extends BaseService implements UserCollectService {

    private final UserCollectDAO userCollectDAO;
    private final GoodsDAO goodsDAO;
    private final UserDAO userDAO;

    public UserCollectServiceImpl(
            UserCollectDAO userCollectDAO,
            GoodsDAO goodsDAO,
            UserDAO userDAO) {
        this.userCollectDAO = userCollectDAO;
        this.goodsDAO = goodsDAO;
        this.userDAO = userDAO;
    }

    @Override
    public Result<Boolean> collect(Long id) {
        Specification<UserCollectDO> spc = (x, y, z) -> {
            ArrayList<Predicate> list = new ArrayList<>();
            list.add(z.equal(x.get("goodsId"), id));
            list.add(z.equal(x.get("creatorId"), currentUser().getId()));
            Predicate[] predicates = new Predicate[list.size()];
            return z.and(list.toArray(predicates));
        };
        List<UserCollectDO> dooList = userCollectDAO.findAll(spc);
        if (dooList.size() == 0) {
            UserCollectDO userCollectDO = new UserCollectDO();
            userCollectDO.setGoodsId(id);
            userCollectDAO.save(userCollectDO);
            goodsDAO.updateTotalCollect(id);
            userDAO.collect(id);
            return Result.success();
        }
        return Result.fail("-1", "已收藏，请勿重复收藏！");
    }

    @Override
    public Result<Boolean> cancelCollect(Long id) {
        UserCollectDO doo = find(id);
        if (doo != null) {
            userCollectDAO.delete(doo);
            goodsDAO.cancelCollect(id);
            userDAO.cancelCollect(id);
        }
        return Result.success("取消收藏成功！");
    }

    @Override
    public Result<Boolean> collected(Long id) {
        UserCollectDO doo = find(id);
        if (doo != null) {
            return Result.success(true, "已收藏");
        }
        return Result.success(false, "未收藏");
    }

    private UserCollectDO find(Long id) {
        Specification<UserCollectDO> spc = (x, y, z) -> {
            ArrayList<Predicate> list = new ArrayList<>();
            list.add(z.equal(x.get("creatorId"), currentUser().getId()));
            list.add(z.equal(x.get("goodsId"), id));
            Predicate[] predicates = new Predicate[list.size()];
            return z.and(list.toArray(predicates));
        };
        return userCollectDAO.findOne(spc).orElse(null);
    }
}
