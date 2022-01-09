package com.yufu.yepshop.repository;

import com.yufu.yepshop.mdm.GoodsCategory;
import com.yufu.yepshop.mdm.RegionInfo;

import java.util.List;

/**
 * @author wang
 * @date 2022/1/9 10:40
 */
public interface GoodsCategoryRepository {
    List<GoodsCategory> findAll();

    Boolean save(GoodsCategory entity);
}
