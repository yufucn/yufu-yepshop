package com.yufu.yepshop.persistence.dao;

import com.yufu.yepshop.persistence.DO.GoodsDO;
import com.yufu.yepshop.persistence.DO.OrderDO;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

/**
 * @author wang
 * @date 2022/1/12 22:39
 */
@Repository
public interface OrderDAO extends PagingAndSortingRepository<OrderDO, Long> {
}
