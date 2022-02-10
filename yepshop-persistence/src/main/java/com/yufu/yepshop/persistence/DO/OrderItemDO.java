package com.yufu.yepshop.persistence.DO;

import com.yufu.yepshop.domain.types.auditing.CreationAuditedEntity;
import com.yufu.yepshop.types.value.GoodsValue;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;

/**
 * @author wang
 * @date 2021/12/28 0:42
 */
@Getter
@Setter
@Entity(name = "yufu_order_item")
@EntityListeners(AuditingEntityListener.class)
@DynamicInsert
@DynamicUpdate
public class OrderItemDO extends CreationAuditedEntity {

    @ManyToOne
    @JoinColumn(name = "orderId")
    private OrderDO order;

    private Long goodsId;

    @Column(length = 64)
    private String skuId;

    private String title;

    private String picUrl;

    @Column(nullable = false, columnDefinition = "int default 0")
    private Integer price;

    @Column(nullable = false, columnDefinition = "int default 0")
    private Integer num;
}
