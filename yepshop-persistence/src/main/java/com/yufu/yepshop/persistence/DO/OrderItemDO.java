package com.yufu.yepshop.persistence.DO;

import com.yufu.yepshop.domain.types.auditing.CreationAuditedEntity;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import java.math.BigDecimal;

/**
 * @author wang
 * @date 2021/12/28 0:42
 */
@Getter
@Setter
@Entity(name = "yufu_order_item")
@EntityListeners(AuditingEntityListener.class)
public class OrderItemDO extends CreationAuditedEntity {
    private Long orderId;

    private Long goodsId;

    @Column(length = 64)
    private String skuId;

    private String goodsTitle;

    private String goodsImageUrl;

    private BigDecimal price;

    private Integer num;
}
