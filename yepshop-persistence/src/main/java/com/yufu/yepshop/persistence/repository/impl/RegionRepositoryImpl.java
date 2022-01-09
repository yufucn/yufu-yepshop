package com.yufu.yepshop.persistence.repository.impl;

import com.yufu.yepshop.mdm.RegionInfo;
import com.yufu.yepshop.persistence.DO.RegionDO;
import com.yufu.yepshop.persistence.converter.RegionConverter;
import com.yufu.yepshop.persistence.dao.RegionDAO;
import com.yufu.yepshop.repository.RegionRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author wang
 * @date 2022/1/8 14:29
 */
@Repository
public class RegionRepositoryImpl implements RegionRepository {

    private final RegionDAO accountDAO;

    private final RegionConverter converter = RegionConverter.INSTANCE;

    public RegionRepositoryImpl(RegionDAO accountDAO) {
        this.accountDAO = accountDAO;
    }

    @Override
    public List<RegionInfo> findAll() {
        List<RegionDO> doList = (List<RegionDO>) accountDAO.findAll();
        return converter.toEntityList(doList);
    }
}
