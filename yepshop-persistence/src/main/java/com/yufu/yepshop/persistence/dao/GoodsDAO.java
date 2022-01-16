package com.yufu.yepshop.persistence.dao;

import com.yufu.yepshop.persistence.DO.GoodsDO;
import com.yufu.yepshop.persistence.DO.OrderDO;
import com.yufu.yepshop.persistence.DO.UserDO;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

/**
 * @author wang
 * @date 2022/1/8 10:23
 */
@Repository
public interface GoodsDAO extends PagingAndSortingRepository<GoodsDO, Long>, JpaSpecificationExecutor<GoodsDO> {

    @Transactional
    @Modifying(clearAutomatically = true)
    @Query(value = "update yufu_goods set total_collect=IFNULL(total_collect,0) + 1 where id = ?1", nativeQuery = true)
    Integer updateCollect(Long id);

    @Transactional
    @Modifying(clearAutomatically = true)
    @Query(value = "update yufu_goods set totalComment=IFNULL(totalComment,0) + 1 where id = ?1", nativeQuery = true)
    Integer updateComment(Long id);
}
