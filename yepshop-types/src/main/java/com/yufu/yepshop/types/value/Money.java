package com.yufu.yepshop.types.value;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;

/**
 * @author wang
 * @date 2021/12/26 20:49
 */
@Getter
@Setter
@Embeddable
public class Money {

    @NotNull(message = "金额不能为空")
    private Integer amount;

    @NotNull(message = "币种不能为空")
    private String currency;
}
