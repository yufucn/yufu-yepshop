package com.yufu.yepshop.repository;

import com.yufu.yepshop.domain.goods.Goods;
import com.yufu.yepshop.mdm.GoodsCategory;

/**
 * @author wang
 * @date 2022/1/9 18:36
 */
public interface GoodsRepository {
    Boolean save(Goods entity);
}
