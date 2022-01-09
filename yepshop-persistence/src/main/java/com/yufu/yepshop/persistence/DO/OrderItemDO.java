package com.yufu.yepshop.persistence.DO;

import com.yufu.yepshop.domain.types.auditing.CreationAuditedEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import java.math.BigDecimal;

/**
 * @author wang
 * @date 2021/12/28 0:42
 */
@Getter
@Setter
@Entity(name = "yufu_order_item")
public class OrderItemDO extends CreationAuditedEntity {
    private Long orderId;

    private Long goodsId;

    private String skuId;

    private String goodsTitle;

    private String goodsImageUrl;

    private BigDecimal price;

    private Integer num;
}
