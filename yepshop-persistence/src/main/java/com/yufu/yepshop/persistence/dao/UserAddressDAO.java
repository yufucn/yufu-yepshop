package com.yufu.yepshop.persistence.dao;

import com.yufu.yepshop.persistence.DO.GoodsDO;
import com.yufu.yepshop.persistence.DO.UserAddressDO;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

/**
 * @author wang
 * @date 2022/1/15 22:46
 */
public interface UserAddressDAO  extends
        PagingAndSortingRepository<UserAddressDO, Long>,
        JpaSpecificationExecutor<UserAddressDO> {
}
