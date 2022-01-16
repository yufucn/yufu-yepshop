package com.yufu.yepshop.persistence.dao;

import com.yufu.yepshop.persistence.DO.UserAddressDO;
import com.yufu.yepshop.persistence.DO.UserDO;
import com.yufu.yepshop.persistence.DO.UserViewHistoryDO;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * @author wang
 * @date 2022/1/16 11:31
 */
@Repository
public interface UserViewHistoryDAO
        extends CrudRepository<UserViewHistoryDO, Long>, JpaSpecificationExecutor<UserViewHistoryDO> {
}
