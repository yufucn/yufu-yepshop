package com.yufu.yepshop.persistence.DO;

import com.yufu.yepshop.domain.types.auditing.FullAuditedEntity;
import com.yufu.yepshop.types.enums.OrderState;
import com.yufu.yepshop.types.enums.PayType;
import com.yufu.yepshop.types.enums.SellerType;
import com.yufu.yepshop.types.value.DeliveryAddressValue;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @author wang
 * @date 2021/12/28 0:30
 */
@Getter
@Setter
@Entity(name = "yufu_order")
public class OrderDO extends FullAuditedEntity {

    private Long sellerId;

    /**
     * A（平台）
     * B（商城商家）
     * C（普通卖家）
     */
    @Column(length = 1)
    private SellerType sellerType;

    private String sellerNick;

    private Long buyerId;

    private String buyerNick;

    @Enumerated(EnumType.STRING)
    @Column(length = 32)
    private PayType payType;

    @Column(length = 64)
    private String payNo;

    @Embedded
    private DeliveryAddressValue deliveryAddress;

    /**
     * 实付金额
     */
    private BigDecimal payment;

    /**
     * 商品总金额
     */
    private BigDecimal totalFee;

    /**
     * 系统优惠金额
     */
    private BigDecimal discountFee;

    /**
     * 邮费
     */
    private BigDecimal postFee;

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
    private BigDecimal buyerPointFee;

    /**
     * 卖家获得积分,返点的积分。格 式:100;单位:个
     */
    private BigDecimal sellerPointFee;

    @Enumerated(EnumType.STRING)
    @Column(length = 32)
    private OrderState state;
}
