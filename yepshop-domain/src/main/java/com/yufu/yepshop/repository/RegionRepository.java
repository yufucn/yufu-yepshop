package com.yufu.yepshop.repository;

import com.yufu.yepshop.mdm.RegionInfo;

import java.util.List;


/**
 * @author wang
 * @date 2022/1/8 14:17
 */
public interface RegionRepository {
    List<RegionInfo> findAll();
}
