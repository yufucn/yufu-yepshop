package com.yufu.yepshop.application.impl;

import com.yufu.yepshop.domain.service.impl.BaseService;
import com.yufu.yepshop.application.GoodsViewService;
import com.yufu.yepshop.common.Result;
import com.yufu.yepshop.persistence.DO.GoodsViewDO;
import com.yufu.yepshop.persistence.dao.GoodsViewDAO;
import com.yufu.yepshop.persistence.dao.UserDAO;
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
public class GoodsViewServiceImpl extends BaseService implements GoodsViewService {
    private final GoodsViewDAO dao;
    private final UserDAO userDAO;


    public GoodsViewServiceImpl(GoodsViewDAO dao, UserDAO userDAO) {
        this.dao = dao;
        this.userDAO = userDAO;
    }

    @Override
    public Result<Boolean> view(Long id) {
        Long userId = currentUser().getId();
        Specification<GoodsViewDO> spc = (x, y, z) -> {
            ArrayList<Predicate> list = new ArrayList<>();
            list.add(z.equal(x.get("goodsId"), id));
            list.add(z.equal(x.get("creatorId"), userId));
            Predicate[] predicates = new Predicate[list.size()];
            return z.and(list.toArray(predicates));
        };
        Optional<GoodsViewDO> dooOptional = dao.findOne(spc);
        if (dooOptional.isPresent()) {
            GoodsViewDO doo = dooOptional.get();
            doo.setViewCount(doo.getViewCount() + 1);
            dao.save(doo);
        } else {
            GoodsViewDO userViewHistoryDO = new GoodsViewDO();
            userViewHistoryDO.setViewCount(1);
            userViewHistoryDO.setGoodsId(id);
            dao.save(userViewHistoryDO);
            userDAO.updateTotalView(userId, 1);
        }
        return Result.success();
    }
}
