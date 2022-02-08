package com.yufu.yepshop.persistence.dao;

import com.yufu.yepshop.persistence.DO.UserDO;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

/**
 * @author wang
 * @date 2022/1/7 23:47
 */
@Repository
public interface UserDAO extends CrudRepository<UserDO, Long> {

    @Transactional
    @Modifying(clearAutomatically = true)
    @Query(value = "update yufu_user set total_view=IFNULL(total_view,0) + 1 where id = ?1", nativeQuery = true)
    Integer updateTotalView(Long id);

    @Transactional
    @Modifying(clearAutomatically = true)
    @Query(value = "update yufu_user set total_view=0 where id = ?1", nativeQuery = true)
    Integer clearTotalView(Long id);

    @Transactional
    @Modifying(clearAutomatically = true)
    @Query(value = "update yufu_user set total_collect=IFNULL(total_collect,0) + 1 where id = ?1", nativeQuery = true)
    Integer collect(Long id);

    @Transactional
    @Modifying(clearAutomatically = true)
    @Query(value = "update yufu_user set total_collect=IFNULL(total_collect,1) - 1 where id = ?1", nativeQuery = true)
    Integer cancelCollect(Long id);
}
