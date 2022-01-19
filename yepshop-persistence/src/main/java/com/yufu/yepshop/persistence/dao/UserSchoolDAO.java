package com.yufu.yepshop.persistence.dao;

import com.yufu.yepshop.persistence.DO.UserAddressDO;
import com.yufu.yepshop.persistence.DO.UserSchoolDO;
import com.yufu.yepshop.persistence.DO.UserViewHistoryDO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;


/**
 * @author wang
 * @date 2022/1/20 0:20
 */
@Repository
public interface UserSchoolDAO
        extends JpaRepository<UserSchoolDO, Long>,
        JpaSpecificationExecutor<UserSchoolDO>{

    @Transactional
    @Modifying
    @Query(value = "delete from #{#entityName} where creator_id = :creatorId", nativeQuery = true)
    void deleteByCreatorId(@Param("creatorId") Long creatorId);

}
