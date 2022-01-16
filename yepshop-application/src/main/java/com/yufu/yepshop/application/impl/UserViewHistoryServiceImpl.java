package com.yufu.yepshop.application.impl;

import com.yufu.yepshop.application.BaseService;
import com.yufu.yepshop.application.UserViewHistoryService;
import com.yufu.yepshop.common.Result;
import com.yufu.yepshop.persistence.DO.UserViewHistoryDO;
import com.yufu.yepshop.persistence.dao.UserViewHistoryDAO;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.Optional;

/**
 * @author wang
 * @date 2022/1/16 11:40
 */
@Service
public class UserViewHistoryServiceImpl extends BaseService implements UserViewHistoryService {
    private final UserViewHistoryDAO dao;

    public UserViewHistoryServiceImpl(UserViewHistoryDAO dao) {
        this.dao = dao;
    }

    @Override
    public Result<Boolean> view(Long id) {
        Specification<UserViewHistoryDO> spc = (x, y, z) -> {
            ArrayList<Predicate> list = new ArrayList<>();
            list.add(z.equal(x.get("goodsId"), id));
            list.add(z.equal(x.get("creatorId"), currentUser().getId()));
            Predicate[] predicates = new Predicate[list.size()];
            return z.and(list.toArray(predicates));
        };
        Optional<UserViewHistoryDO> dooOptional = dao.findOne(spc);
        if (dooOptional.isPresent()) {
            UserViewHistoryDO doo = dooOptional.get();
            doo.setViewCount(doo.getViewCount() + 1);
            dao.save(doo);
        } else {
            UserViewHistoryDO userViewHistoryDO = new UserViewHistoryDO();
            userViewHistoryDO.setViewCount(1);
            userViewHistoryDO.setGoodsId(id);
            dao.save(userViewHistoryDO);
        }
        return Result.success();
    }
}
