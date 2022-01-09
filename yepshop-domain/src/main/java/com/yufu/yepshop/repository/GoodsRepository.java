package com.yufu.yepshop.repository;

import com.yufu.yepshop.domain.goods.Goods;
import org.springframework.data.domain.Page;

/**
 * @author wang
 * @date 2022/1/9 18:36
 */
public interface GoodsRepository {
    Boolean save(Goods entity);

    Page<Goods> pagedList(Integer page, Integer perPage);

    Goods get(String id);
}
