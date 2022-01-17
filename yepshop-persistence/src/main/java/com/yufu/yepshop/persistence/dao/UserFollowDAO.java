package com.yufu.yepshop.persistence.dao;

import com.yufu.yepshop.persistence.DO.UserFollowDO;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

/**
 * @author wang
 * @date 2022/1/17 23:05
 */
@Repository
public interface UserFollowDAO
        extends PagingAndSortingRepository<UserFollowDO, Long>,
        JpaSpecificationExecutor<UserFollowDO> {
}
