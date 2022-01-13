package com.yufu.yepshop.domain.ordering;

import com.yufu.yepshop.types.enums.OrderState;
import com.yufu.yepshop.types.enums.PayType;
import com.yufu.yepshop.types.enums.SellerType;
import com.yufu.yepshop.types.value.DeliveryAddressValue;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.List;

/**
 * @author wang
 * @date 2022/1/12 0:31
 */
@Getter
@Setter
public class Order {

    private String id;

    private String sellerId;

    /**
     * A（平台）
     * B（商城商家）
     * C（普通卖家）
     */
    private SellerType sellerType;

    private String buyerId;

    private PayType payType;

    private String payNo;

    private DeliveryAddressValue deliveryAddress;

    /**
     * 实付金额,单位为分
     */
    private Integer payment;

    /**
     * 商品总金额,单位为分
     */
    private Integer totalFee;

    /**
     * 系统优惠金额,单位为分
     */
    private Integer discountFee;

    /**
     * 邮费,单位为分
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

    private OrderState state;

    private List<OrderItem> items;
}
