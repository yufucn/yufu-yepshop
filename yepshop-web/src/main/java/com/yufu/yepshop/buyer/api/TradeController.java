package com.yufu.yepshop.buyer.api;

import com.yufu.yepshop.application.TradeService;
import com.yufu.yepshop.common.Result;
import com.yufu.yepshop.types.command.CheckoutCommand;
import com.yufu.yepshop.types.command.CreateOrderCommand;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
    public Result<Boolean> create(@RequestBody CheckoutCommand command) {
        return tradeService.checkout(command);
    }
}
