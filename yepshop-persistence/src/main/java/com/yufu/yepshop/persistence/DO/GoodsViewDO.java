package com.yufu.yepshop.persistence.DO;

import com.yufu.yepshop.domain.types.auditing.AuditedEntity;
import com.yufu.yepshop.domain.types.auditing.CreationAuditedEntity;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;

/**
 * @author wang
 * @date 2021/12/27 23:43
 */
@Getter
@Setter
@Entity(name = "yufu_goods_view")
@DynamicInsert
@DynamicUpdate
@EntityListeners(AuditingEntityListener.class)
public class GoodsViewDO extends AuditedEntity {
    @Column(nullable = false, columnDefinition = "int default 0")
    private Integer viewCount;
    private Long goodsId;
}
