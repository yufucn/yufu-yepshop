package com.yufu.yepshop.types.dto;

import com.yufu.yepshop.types.enums.OrderState;
import com.yufu.yepshop.types.enums.RateState;
import com.yufu.yepshop.types.enums.SellerType;
import com.yufu.yepshop.types.value.Buyer;
import com.yufu.yepshop.types.value.DeliveryAddressValue;
import com.yufu.yepshop.types.value.Seller;
import io.swagger.annotations.ApiModelProperty;
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

    @ApiModelProperty(value = "交易单号")
    private String tradeId;

    @ApiModelProperty(value = "收货地址")
    private DeliveryAddressValue deliveryAddress;

    /**
     * 实付金额,单位为分
     */
    @ApiModelProperty(value = "实付金额")
    private Integer payment;

    /**
     * 商品总金额,单位为分
     */
    @ApiModelProperty(value = "商品总金额")
    private Integer totalFee;

    /**
     * 系统优惠金额,单位为分
     */
    @ApiModelProperty(value = "系统优惠金额")
    private Integer discountFee;

    /**
     * 邮费,单位为分
     */
    @ApiModelProperty(value = "邮费")
    private Integer postFee;

    /**
     * 付款时间
     */
    @ApiModelProperty(value = "支付时间")
    private Date payTime;

    /**
     * 交易成功时间 - 确认收货
     */
    @ApiModelProperty(value = "交易完成时间（确认收货时间）")
    private Date endTime;

    /**
     * 买家获得积分,返点的积分。格 式:100;单位:个
     */
    @ApiModelProperty(value = "买获得积分点")
    private Integer buyerPointFee;

    /**
     * 卖家获得积分,返点的积分。格 式:100;单位:个
     */
    @ApiModelProperty(value = "卖家获得积分点")
    private Integer sellerPointFee;

    @ApiModelProperty(value = "订单状态")
    private OrderState orderState;

    private List<OrderItemDTO> items;

    @ApiModelProperty(value = "快递单号")
    private String invoiceNo;

    @ApiModelProperty(value = "物流公司")
    private String logisticsCompany;

    @ApiModelProperty(value = "评价状态")
    private RateState rateState;

    @ApiModelProperty(value = "卖家已评价")
    private Boolean sellerRate;

    @ApiModelProperty(value = "买家已评价")
    private Boolean buyerRate;

    @ApiModelProperty(value = "签收时间")
    private Date signTime;

    @ApiModelProperty(value = "交易关闭时间")
    private Date closeTime;

    @ApiModelProperty(value = "发货时间")
    private Date deliverTime;
}
