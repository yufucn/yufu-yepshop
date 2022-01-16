package com.yufu.yepshop.application.impl;

import com.yufu.yepshop.application.BaseService;
import com.yufu.yepshop.application.UserCollectService;
import com.yufu.yepshop.common.Result;
import com.yufu.yepshop.persistence.DO.UserCollectDO;
import com.yufu.yepshop.persistence.dao.GoodsDAO;
import com.yufu.yepshop.persistence.dao.UserCollectDAO;
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

    private final UserCollectDAO dao;
    private final GoodsDAO goodsDAO;

    public UserCollectServiceImpl(UserCollectDAO dao, GoodsDAO goodsDAO) {
        this.dao = dao;
        this.goodsDAO = goodsDAO;
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
        List<UserCollectDO> dooList = dao.findAll(spc);
        if (dooList.size() == 0) {
            UserCollectDO userCollectDO = new UserCollectDO();
            userCollectDO.setGoodsId(id);
            dao.save(userCollectDO);
            goodsDAO.updateCollect(id);
            return Result.success();
        }
        return Result.fail("-1", "已收藏，请勿重复收藏！");
    }
}
