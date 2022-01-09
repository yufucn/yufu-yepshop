package com.yufu.yepshop.persistence.dao;

import com.yufu.yepshop.persistence.DO.UserDO;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * @author wang
 * @date 2022/1/7 23:47
 */
@Repository
public interface UserDAO extends CrudRepository<UserDO, Long> {
}
