package com.yufu.yepshop.persistence.dao;

import com.yufu.yepshop.persistence.DO.UserAddressDO;
import com.yufu.yepshop.persistence.DO.UserCollectDO;
import com.yufu.yepshop.persistence.DO.UserDO;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;

/**
 * @author wang
 * @date 2022/1/16 10:50
 */
public interface UserCollectDAO  extends CrudRepository<UserCollectDO, Long> , JpaSpecificationExecutor<UserCollectDO> {
}
