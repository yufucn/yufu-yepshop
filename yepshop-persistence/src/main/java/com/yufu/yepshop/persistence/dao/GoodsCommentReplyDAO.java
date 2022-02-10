package com.yufu.yepshop.persistence.dao;

import com.yufu.yepshop.persistence.DO.GoodsCommentReplyDO;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author wang
 * @date 2022/1/29 11:34
 */
@Repository
public interface GoodsCommentReplyDAO extends CrudRepository<GoodsCommentReplyDO, Long>,
        JpaSpecificationExecutor<GoodsCommentReplyDO> {

    @Transactional
    @Modifying
    @Query(value = "delete from yufu_goods_comment_reply where comment_id = :commentId", nativeQuery = true)
    void deleteByCommentId(@Param("commentId") Long commentId);
}
