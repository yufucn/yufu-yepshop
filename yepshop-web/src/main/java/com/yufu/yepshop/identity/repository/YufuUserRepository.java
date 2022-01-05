package com.yufu.yepshop.identity.repository;

import com.yufu.yepshop.identity.entity.YufuUser;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * @author wang
 * @date 2022/1/5 23:09
 */
@Repository
public interface YufuUserRepository extends CrudRepository<YufuUser, Long> {
    Optional<YufuUser> findByUserName(String userName);
    Optional<YufuUser> findByEmail(String email);
}
