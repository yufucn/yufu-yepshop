package com.yufu.yepshop.persistence.repository.impl;

import com.yufu.yepshop.mdm.GoodsCategory;
import com.yufu.yepshop.mdm.RegionInfo;
import com.yufu.yepshop.persistence.DO.GoodsCategoryDO;
import com.yufu.yepshop.persistence.DO.RegionDO;
import com.yufu.yepshop.persistence.converter.GoodsCategoryConverter;
import com.yufu.yepshop.persistence.converter.RegionConverter;
import com.yufu.yepshop.persistence.dao.GoodsCategoryDAO;
import com.yufu.yepshop.persistence.dao.RegionDAO;
import com.yufu.yepshop.repository.GoodsCategoryRepository;
import com.yufu.yepshop.repository.RegionRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author wang
 * @date 2022/1/9 10:52
 */
@Repository
public class GoodsCategoryRepositoryImpl implements GoodsCategoryRepository {

    private final GoodsCategoryDAO accountDAO;

    private final GoodsCategoryConverter converter = GoodsCategoryConverter.INSTANCE;

    public GoodsCategoryRepositoryImpl(GoodsCategoryDAO accountDAO) {

        this.accountDAO = accountDAO;
    }

    @Override
    public List<GoodsCategory> findAll() {
        List<GoodsCategoryDO> doList = (List<GoodsCategoryDO>) accountDAO.findAll();
        return converter.toEntityList(doList);
    }

    @Override
    public Boolean save(GoodsCategory entity) {
        accountDAO.save(converter.toDO(entity));
        return true;
    }
}
