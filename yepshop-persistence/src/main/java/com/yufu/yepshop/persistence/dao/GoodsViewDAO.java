package com.yufu.yepshop.persistence.dao;

import com.yufu.yepshop.persistence.DO.GoodsViewDO;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

/**
 * @author wang
 * @date 2022/1/16 11:31
 */
@Repository
public interface GoodsViewDAO
        extends CrudRepository<GoodsViewDO, Long>, JpaSpecificationExecutor<GoodsViewDO> {

    @Transactional
    @Modifying
    @Query(value = "delete from #{#entityName} where creator_id = :creatorId", nativeQuery = true)
    void clear(@Param("creatorId") Long creatorId);
}
