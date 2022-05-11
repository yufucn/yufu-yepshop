package com.yufu.yepshop.buyer.api;

import com.yufu.yepshop.application.TradeService;
import com.yufu.yepshop.common.Result;
import com.yufu.yepshop.external.dto.WechatPayResponse;
import com.yufu.yepshop.types.command.CheckoutCommand;
import com.yufu.yepshop.types.command.CreateOrderCommand;
import com.yufu.yepshop.types.command.PayCommand;
import com.yufu.yepshop.types.dto.CheckoutDTO;
import com.yufu.yepshop.types.event.PaymentReceivedEvent;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.ParseException;

/**
 * @author wang
 * @date 2022/1/11 22:59
 */
@Api(tags = "Shop - 买家 - 交易")
@RestController
@RequestMapping("/api/v1/shop/buyer/trade")
@Slf4j
public class TradeController {

    private final TradeService tradeService;

    public TradeController(TradeService tradeService) {
        this.tradeService = tradeService;
    }

    @ApiOperation(value = "下单")
    @PostMapping
    public Result<WechatPayResponse> create(@RequestBody CheckoutCommand command) {
        return tradeService.checkout(command);
    }

    @ApiOperation(value = "去支付")
    @PostMapping("/pay")
    public Result<WechatPayResponse> pay(@RequestBody PayCommand command) {
        return tradeService.pay(command);
    }

    @ApiOperation(value = "支付成功")
    @PostMapping("/pay-success")
    public Result<Boolean> paySuccess(@RequestBody PaymentReceivedEvent event) throws ParseException {
        return tradeService.paySuccess(event);
    }
}
