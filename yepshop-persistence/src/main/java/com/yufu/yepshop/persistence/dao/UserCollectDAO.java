package com.yufu.yepshop.persistence.dao;

import com.yufu.yepshop.persistence.DO.UserAddressDO;
import com.yufu.yepshop.persistence.DO.UserCollectDO;
import com.yufu.yepshop.persistence.DO.UserDO;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;

/**
 * @author wang
 * @date 2022/1/16 10:50
 */
public interface UserCollectDAO  extends CrudRepository<UserCollectDO, Long> , JpaSpecificationExecutor<UserCollectDO> {
    @Transactional
    @Modifying
    @Query(value = "delete from #{#entityName} where creator_id = :creatorId", nativeQuery = true)
    void deleteByCreatorId(@Param("creatorId") Long creatorId);
}
