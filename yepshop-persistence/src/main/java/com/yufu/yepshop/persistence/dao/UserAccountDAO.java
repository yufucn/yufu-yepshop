package com.yufu.yepshop.persistence.dao;

import com.yufu.yepshop.persistence.DO.GoodsViewDO;
import com.yufu.yepshop.persistence.DO.UserAccountDO;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.Optional;

/**
 * @author wang
 * @date 2022/1/5 23:09
 */
@Repository
public interface UserAccountDAO extends CrudRepository<UserAccountDO, Long>, JpaSpecificationExecutor<UserAccountDO> {
    Optional<UserAccountDO> findByUserName(String userName);

    Optional<UserAccountDO> findByOpenId(String openId);

    Optional<UserAccountDO> findByEmail(String email);

    @Transactional
    @Modifying(clearAutomatically = true)
    @Query(value = "update yufu_user_account set mobile=?1 where id = ?2", nativeQuery = true)
    Integer updateMobile(String mobile, Long id);

    @Transactional
    @Modifying(clearAutomatically = true)
    @Query(value = "update yufu_user_account set latitude=?1,longitude=?2 where id = ?3", nativeQuery = true)
    Integer updateLocation(Double latitude, Double longitude, Long id);
}
