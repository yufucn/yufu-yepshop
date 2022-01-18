package com.yufu.yepshop.types.command;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * @author wang
 * @date 2022/1/18 22:37
 */
@Getter
@Setter
public class OrderSendCommand {

    @ApiModelProperty(value = "物流公司名称")
    private String logisticsCompany;

    @ApiModelProperty(value = "快递单号")
    private String invoiceNo;
}
