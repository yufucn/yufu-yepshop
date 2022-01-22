package com.yufu.yepshop.persistence.dao;

import com.yufu.yepshop.persistence.DO.GoodsViewDO;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * @author wang
 * @date 2022/1/16 11:31
 */
@Repository
public interface GoodsViewDAO
        extends CrudRepository<GoodsViewDO, Long>, JpaSpecificationExecutor<GoodsViewDO> {
}
