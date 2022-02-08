package com.yufu.yepshop.persistence.dao;

import com.yufu.yepshop.persistence.DO.GoodsCategoryDO;
import com.yufu.yepshop.persistence.DO.GoodsCommentDO;
import com.yufu.yepshop.persistence.DO.GoodsDO;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

/**
 * @author wang
 * @date 2022/1/29 11:28
 */
@Repository
public interface GoodsCommentDAO extends CrudRepository<GoodsCommentDO, Long>,
        JpaSpecificationExecutor<GoodsCommentDO> {

    @Transactional
    @Modifying(clearAutomatically = true)
    @Query(value = "update yufu_goods_comment set total_reply=IFNULL(total_reply,0) + 1 where id = ?1", nativeQuery = true)
    Integer updateTotalReply(Long id);
}
