package com.yufu.yepshop.persistence.dao;

import com.yufu.yepshop.persistence.DO.GoodsDO;
import com.yufu.yepshop.persistence.DO.UserDO;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * @author wang
 * @date 2022/1/8 10:23
 */
@Repository
public interface GoodsDAO extends CrudRepository<GoodsDO, Long> {
}
