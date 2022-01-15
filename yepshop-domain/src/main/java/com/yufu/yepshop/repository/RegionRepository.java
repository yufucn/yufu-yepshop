package com.yufu.yepshop.repository;

import com.yufu.yepshop.types.dto.RegionDTO;

import java.util.List;


/**
 * @author wang
 * @date 2022/1/8 14:17
 */
public interface RegionRepository {
    List<RegionDTO> findAll();
}
