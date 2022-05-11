package com.yufu.yepshop.types.event;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 * @author wang
 * @date 2022/5/11 23:43
 */
@Getter
@Setter
@AllArgsConstructor
public class PaymentReceivedEvent {
    private String outTradNo;
    private String transactionId;
    private String successTime;
}
