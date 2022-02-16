package com.yufu.yepshop.persistence.dao;

import com.yufu.yepshop.persistence.DO.OrderDO;
import com.yufu.yepshop.persistence.DO.OrderRateDO;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author wang
 * @date 2022/1/18 23:19
 */
@Repository
public interface OrderRateDAO
        extends PagingAndSortingRepository<OrderRateDO, Long>, JpaSpecificationExecutor<OrderRateDO> {

    List<OrderRateDO> getByOrderId(Long orderId);
}
