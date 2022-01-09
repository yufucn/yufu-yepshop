package com.yufu.yepshop.persistence.repository.impl;

import com.yufu.yepshop.domain.goods.Goods;
import com.yufu.yepshop.persistence.DO.GoodsDO;
import com.yufu.yepshop.persistence.DO.GoodsDetailDO;
import com.yufu.yepshop.persistence.converter.GoodsConverter;
import com.yufu.yepshop.persistence.dao.GoodsDAO;
import com.yufu.yepshop.persistence.dao.GoodsDetailDAO;
import com.yufu.yepshop.repository.GoodsRepository;
import org.springframework.stereotype.Repository;

/**
 * @author wang
 * @date 2022/1/9 18:37
 */
@Repository
public class GoodsRepositoryImpl implements GoodsRepository {

    private final GoodsDAO dao;
    private final GoodsDetailDAO detailDao;

    private final GoodsConverter converter = GoodsConverter.INSTANCE;


    public GoodsRepositoryImpl(GoodsDAO dao, GoodsDetailDAO detailDao) {
        this.dao = dao;
        this.detailDao = detailDao;
    }

    @Override
    public Boolean save(Goods entity) {
        GoodsDO goodsDo = new GoodsDO();
        if (entity.getId() != null) {
            goodsDo = dao.findById(Long.parseLong(entity.getId())).get();
        }
        converter.toDO(entity, goodsDo);
        dao.save(goodsDo);

        GoodsDetailDO detailDO = new GoodsDetailDO();
        converter.toDO(entity, detailDO);
        detailDao.save(detailDO);
        return true;
    }
}
