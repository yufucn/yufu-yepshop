package com.yufu.yepshop.types.dto;

import com.yufu.yepshop.types.enums.OrderState;
import com.yufu.yepshop.types.enums.SellerType;
import com.yufu.yepshop.types.value.Buyer;
import com.yufu.yepshop.types.value.DeliveryAddressValue;
import com.yufu.yepshop.types.value.Seller;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.List;

/**
 * @author wang
 * @date 2022/1/16 1:27
 */
@Getter
@Setter
public class OrderDTO {

    private String id;

    private Seller seller;

    private Buyer buyer;

    private String tradeId;

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

    private OrderState orderState;

    private List<OrderItemDTO> items;
}
