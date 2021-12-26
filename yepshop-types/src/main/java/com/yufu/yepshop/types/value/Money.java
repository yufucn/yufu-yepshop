package com.yufu.yepshop.types.value;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Currency;

/**
 * @author wang
 * @date 2021/12/26 20:49
 */
public class Money {

    @NotNull(message = "金额不能为空")
    private BigDecimal amount;

    @NotNull(message = "币种不能为空")
    private String currency;

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }
}
