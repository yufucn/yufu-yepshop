package com.yufu.yepshop.domain.ordering;

import com.yufu.yepshop.types.enums.OrderState;
import com.yufu.yepshop.types.enums.PayType;
import com.yufu.yepshop.types.enums.SellerType;
import com.yufu.yepshop.types.value.DeliveryAddressValue;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @author wang
 * @date 2022/1/12 0:31
 */
@Getter
@Setter
public class Order {

    private Long sellerId;

    /**
     * A（平台）
     * B（商城商家）
     * C（普通卖家）
     */
    private SellerType sellerType;

    private Long buyerId;

    private PayType payType;

    private String payNo;

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

    private OrderState state;

}
