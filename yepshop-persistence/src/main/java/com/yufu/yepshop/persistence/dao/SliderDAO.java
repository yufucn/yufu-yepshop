package com.yufu.yepshop.persistence.dao;

import com.yufu.yepshop.persistence.DO.SliderDO;
import com.yufu.yepshop.persistence.DO.TradeDO;
import com.yufu.yepshop.persistence.DO.UserSchoolDO;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

/**
 * @author wang
 * @date 2022/2/11 21:49
 */
@Repository
public interface SliderDAO extends PagingAndSortingRepository<SliderDO, Long> ,
        JpaSpecificationExecutor<SliderDO> {
}
