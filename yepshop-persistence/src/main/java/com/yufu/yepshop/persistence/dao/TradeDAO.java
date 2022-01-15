package com.yufu.yepshop.persistence.dao;

import com.yufu.yepshop.persistence.DO.OrderDO;
import com.yufu.yepshop.persistence.DO.TradeDO;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

/**
 * @author wang
 * @date 2022/1/14 22:03
 */
@Repository
public interface TradeDAO extends PagingAndSortingRepository<TradeDO, Long> {
}
