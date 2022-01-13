package com.yufu.yepshop.persistence.DO;

import com.yufu.yepshop.domain.types.auditing.FullAuditedEntity;
import com.yufu.yepshop.types.enums.OrderState;
import com.yufu.yepshop.types.enums.PayType;
import com.yufu.yepshop.types.enums.SellerType;
import com.yufu.yepshop.types.value.DeliveryAddressValue;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

/**
 * @author wang
 * @date 2021/12/28 0:30
 */
@Getter
@Setter
@Entity(name = "yufu_order")
@EntityListeners(AuditingEntityListener.class)
public class OrderDO extends FullAuditedEntity {

    private Long sellerId;

    /**
     * A（平台）
     * B（商城商家）
     * C（普通卖家）
     */
    @Column(length = 1)
    private SellerType sellerType;

    private Long buyerId;

    @Enumerated(EnumType.STRING)
    @Column(length = 32)
    private PayType payType;

    @Column(length = 64)
    private String payNo;

    @Embedded
    private DeliveryAddressValue deliveryAddress;

    @OneToMany(
            targetEntity = OrderItemDO.class,
            mappedBy = "order",
            fetch = FetchType.LAZY,
            cascade = CascadeType.ALL)
    private List<OrderItemDO> items;

    /**
     * 实付金额
     */
    private Integer payment;

    /**
     * 商品总金额
     */
    private Integer totalFee;

    /**
     * 系统优惠金额
     */
    private Integer discountFee;

    /**
     * 邮费
     */
    private Integer postFee;

    /**
     * 付款时间
     */
    private Date payTime;

    /**
     * 交易成功时间 - 确认收货
     */
    private Date endTime;

    /**
     * 买家获得积分,返点的积分。格 式:100;单位:个
     */
    private Integer buyerPointFee;

    /**
     * 卖家获得积分,返点的积分。格 式:100;单位:个
     */
    private Integer sellerPointFee;

    @Enumerated(EnumType.STRING)
    @Column(length = 32)
    private OrderState state;
}
