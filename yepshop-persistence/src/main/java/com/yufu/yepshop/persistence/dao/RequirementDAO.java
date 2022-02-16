package com.yufu.yepshop.persistence.dao;

import com.yufu.yepshop.persistence.DO.GoodsDO;
import com.yufu.yepshop.persistence.DO.RequirementDO;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

/**
 * @author wang
 * @date 2022/2/16 23:12
 */
@Repository
public interface RequirementDAO extends PagingAndSortingRepository<RequirementDO, Long>,
        JpaSpecificationExecutor<RequirementDO> {
}
