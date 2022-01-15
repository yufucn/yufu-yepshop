package com.yufu.yepshop.persistence.dao;

import com.yufu.yepshop.persistence.DO.UserAccountDO;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * @author wang
 * @date 2022/1/5 23:09
 */
@Repository
public interface UserAccountDAO extends CrudRepository<UserAccountDO, Long> {
    Optional<UserAccountDO> findByUserName(String userName);
    Optional<UserAccountDO> findByOpenId(String openId);
    Optional<UserAccountDO> findByEmail(String email);
}
