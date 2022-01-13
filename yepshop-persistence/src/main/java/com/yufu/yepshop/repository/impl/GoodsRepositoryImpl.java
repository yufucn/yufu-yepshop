package com.yufu.yepshop.repository.impl;

import com.yufu.yepshop.domain.goods.Goods;
import com.yufu.yepshop.persistence.DO.GoodsDO;
import com.yufu.yepshop.persistence.DO.GoodsDetailDO;
import com.yufu.yepshop.persistence.converter.GoodsConverter;
import com.yufu.yepshop.persistence.dao.GoodsDAO;
import com.yufu.yepshop.persistence.dao.GoodsDetailDAO;
import com.yufu.yepshop.repository.GoodsRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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
            goodsDo = dao.findById(entity.getId()).get();
        }
        converter.toDO(entity, goodsDo);
        dao.save(goodsDo);

        GoodsDetailDO detailDO = new GoodsDetailDO();
        converter.toDO(entity, detailDO);
        detailDO.setId(goodsDo.getId());
        detailDao.save(detailDO);
        return true;
    }

    @Override
    public Page<Goods> pagedList(Integer page, Integer perPage) {
        Pageable pageable = PageRequest.of(page, perPage, Sort.Direction.DESC, "id");
        Page<Goods> pageResult =
                dao.findAll(pageable).map(this::convert);
        return pageResult;
    }

    @Override
    public Goods get(String id) {
        Long lId = Long.parseLong(id);
        GoodsDO goodsDO = dao.findById(lId).get();
        GoodsDetailDO goodsDetailDO = detailDao.findById(lId).get();
        Goods result = converter.toEntity(goodsDO);
        converter.toEntity(goodsDetailDO, result);
        return result;
    }

//    @Override
//    public List<Goods> getList(List<Long> ids) {
//        Iterable<GoodsDO> dos = dao.findAllById(ids);
//        List<GoodsDO> goodsDOList = new ArrayList<>();
//        dos.forEach(goodsDOList::add);
//        return converter.toEntityList(goodsDOList);
//    }

    private Goods convert(GoodsDO gDo) {
        return converter.toEntity(gDo);
    }
}
