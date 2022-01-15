package com.yufu.yepshop.repository;

import com.yufu.yepshop.types.dto.GoodsCategoryDTO;

import java.util.List;

/**
 * @author wang
 * @date 2022/1/9 10:40
 */
public interface GoodsCategoryRepository {
    List<GoodsCategoryDTO> findAll();

    Boolean save(GoodsCategoryDTO entity);
}
