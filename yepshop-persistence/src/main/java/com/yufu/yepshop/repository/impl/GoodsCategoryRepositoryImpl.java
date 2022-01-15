package com.yufu.yepshop.repository.impl;

import com.yufu.yepshop.persistence.DO.GoodsCategoryDO;
import com.yufu.yepshop.persistence.DO.RegionDO;
import com.yufu.yepshop.persistence.converter.GoodsCategoryConverter;
import com.yufu.yepshop.persistence.converter.RegionConverter;
import com.yufu.yepshop.persistence.dao.GoodsCategoryDAO;
import com.yufu.yepshop.persistence.dao.RegionDAO;
import com.yufu.yepshop.repository.GoodsCategoryRepository;
import com.yufu.yepshop.repository.RegionRepository;
import com.yufu.yepshop.types.dto.GoodsCategoryDTO;
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
    public List<GoodsCategoryDTO> findAll() {
        List<GoodsCategoryDO> doList = (List<GoodsCategoryDO>) accountDAO.findAll();
        return converter.toDTOList(doList);
    }

    @Override
    public Boolean save(GoodsCategoryDTO entity) {
        accountDAO.save(converter.toDO(entity));
        return true;
    }
}
