package com.yufu.yepshop.persistence.DO;

import com.yufu.yepshop.domain.types.auditing.FullAuditedEntity;
import com.yufu.yepshop.types.enums.*;
import com.yufu.yepshop.types.value.DeliveryAddressValue;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
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
@DynamicInsert
@DynamicUpdate
public class OrderDO extends FullAuditedEntity {

    private Long sellerId;
    /**
     * A（平台）
     * B（商城商家）
     * C（普通卖家）
     */
    @Enumerated(EnumType.STRING)
    @Column(length = 1)
    private SellerType sellerType;

    private Long buyerId;

    private Long tradeId;

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
    @Column(nullable = false, columnDefinition = "int default 0")
    private Integer payment;

    /**
     * 商品总金额
     */
    @Column(nullable = false, columnDefinition = "int default 0")
    private Integer totalFee;

    /**
     * 系统优惠金额
     */
    @Column(nullable = false, columnDefinition = "int default 0")
    private Integer discountFee;

    /**
     * 邮费
     */
    @Column(nullable = false, columnDefinition = "int default 0")
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
    @Column(nullable = false, columnDefinition = "int default 0")
    private Integer buyerPointFee;

    /**
     * 卖家获得积分,返点的积分。格 式:100;单位:个
     */
    @Column(nullable = false, columnDefinition = "int default 0")
    private Integer sellerPointFee;

    @Enumerated(EnumType.STRING)
    @Column(length = 32)
    private OrderState orderState;

    /**
     * 卖家是否已评价 可选值:true(已评价),false(未评价)
     */
    private Boolean sellerRate;

    /**
     * 买家是否已评价 可选值:true(已评价),false(未评价)
     */
    private Boolean buyerRate;

    /**
     * 签收时间
     */
    private Date signTime;

    /**
     * 快递公司名称
     */
    @Column(length = 128)
    private String logisticsCompany;

    @Column(length = 64)
    private String invoiceNo;

    /**
     * 发货时间
     */
    private Date deliveryTime;


    /**
     * 交易关闭时间
     */
    private Date closeTime;

    /**
     * 评价状态
     */
    @Enumerated(EnumType.STRING)
    @Column(length = 32)
    private RateState rateState;

    /**
     * 付款
     */
    public void pay() {
        this.setOrderState(OrderState.WAIT_SELLER_SEND_GOODS);
        this.setPayTime(new Date());
        this.setBuyerPointFee(this.getPayment());
        this.setSellerPointFee(this.getPayment());
    }

    /**
     * 评价
     */
    public void rate(OrderRole role) {
        if (role == OrderRole.BUYER) {
            buyerRate();
        } else {
            sellerRate();
        }
    }

    /**
     * 签收
     */
    public void cancel() {
        this.setOrderState(OrderState.TRADE_CLOSED);
        Date now = new Date();
        this.setCloseTime(now);
    }

    /**
     * 签收
     */
    public void sign() {
        this.setOrderState(OrderState.TRADE_FINISHED);
        Date now = new Date();
        this.setSignTime(now);
        this.setEndTime(now);
    }

    /**
     * 发货
     */
    public void send(String logisticsCompany, String invoiceNo) {
        this.setLogisticsCompany(logisticsCompany);
        this.setInvoiceNo(invoiceNo);
        this.setDeliveryTime(new Date());
        this.setOrderState(OrderState.WAIT_BUYER_CONFIRM_GOODS);
    }

    /**
     * 买家评价
     */
    private void buyerRate() {
        this.setBuyerRate(true);
        if (sellerRate == null) {
            this.setRateState(RateState.RATE_BUYER_UN_SELLER);
        } else {
            this.setRateState(RateState.RATE_BUYER_SELLER);
            close();
        }
    }


    /**
     * 卖家评价
     */
    private void sellerRate() {
        this.setSellerRate(true);
        if (buyerRate == null) {
            this.setRateState(RateState.RATE_UN_BUYER_SELLER);
        } else {
            this.setRateState(RateState.RATE_BUYER_SELLER);
            close();
        }
    }

    /**
     * 交易关闭
     */
    private void close() {
        this.setCloseTime(new Date());
        this.setOrderState(OrderState.TRADE_CLOSED);
    }

}
