package com.yufu.yepshop.persistence.DO;

import com.yufu.yepshop.domain.types.auditing.FullAuditedEntity;
import com.yufu.yepshop.types.enums.PayState;
import com.yufu.yepshop.types.enums.PayType;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

/**
 * @author wang
 * @date 2022/1/14 21:45
 */
@Getter
@Setter
@Entity(name = "yufu_trade")
@EntityListeners(AuditingEntityListener.class)
public class TradeDO extends FullAuditedEntity {

    @Enumerated(EnumType.STRING)
    @Column(length = 32)
    private PayType payType;

    /**
     * 合并交易订单号
     */
    private String combineId;

    @Enumerated(EnumType.STRING)
    @Column(length = 32)
    private PayState payState;

    @Column(length = 128)
    private String openId;

    @Column(length = 64)
    private String prepayId;

    /**
     * 付款时间
     */
    private Date payTime;

    public boolean hasPayed() {
        return payState == PayState.TAY_SUCCESS;
    }
}
