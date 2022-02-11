package com.yufu.yepshop.persistence.dao;

import com.yufu.yepshop.persistence.DO.GoodsDO;
import com.yufu.yepshop.persistence.DO.OrderDO;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

/**
 * @author wang
 * @date 2022/1/12 22:39
 */
@Repository
public interface OrderDAO extends PagingAndSortingRepository<OrderDO, Long>, JpaSpecificationExecutor<OrderDO> {

    @Transactional
    @Modifying(clearAutomatically = true)
    @Query(value = "update yufu_order set payment=?2 where id = ?1", nativeQuery = true)
    Integer updatePayment(Long id, Integer payment);
}
