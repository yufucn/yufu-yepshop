package com.yufu.yepshop.persistence.dao;

import com.yufu.yepshop.persistence.DO.GoodsCategoryDO;
import com.yufu.yepshop.persistence.DO.SchoolDO;
import com.yufu.yepshop.persistence.DO.UserAddressDO;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * @author wang
 * @date 2022/1/8 12:44
 */
@Repository
public interface SchoolDAO extends CrudRepository<SchoolDO, Long>,
        JpaSpecificationExecutor<SchoolDO> {
}
