package com.yufu.yepshop.ordering.api;

import com.yufu.yepshop.application.OrderService;
import com.yufu.yepshop.common.Result;
import com.yufu.yepshop.types.command.OrderRateCommand;
import com.yufu.yepshop.types.command.OrderSendCommand;
import com.yufu.yepshop.types.command.UpdateOrderAddressCommand;
import com.yufu.yepshop.types.dto.BuyerOrderDTO;
import com.yufu.yepshop.types.dto.OrderDTO;
import com.yufu.yepshop.types.dto.OrderRateDTO;
import com.yufu.yepshop.types.dto.SellerOrderDTO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author wang
 * @date 2022/1/16 2:02
 */
@Api(tags = "Shop - 订单")
@RestController
@RequestMapping("/api/v1/shop/order")
@Slf4j
public class OrderController {
    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @ApiOperation(value = "买家 - 列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "orderState", value = "ALL、orderState所有值", paramType = "query", dataType = "String"),
    })
    @GetMapping("/buyer")
    public Result<Page<BuyerOrderDTO>> getBuyerOrders(
            @RequestParam Integer page,
            @RequestParam Integer perPage,
            @RequestParam String orderState
    ) {
        return orderService.pagedBuyerList(page, perPage, orderState);
    }

    @ApiOperation(value = "详情")
    @GetMapping("/{id}")
    public Result<OrderDTO> get(@PathVariable Long id) {
        return orderService.get(id);
    }



    @ApiOperation(value = "卖家 - 列表")
    @GetMapping("/seller")
    public Result<Page<SellerOrderDTO>> getSellerOrders(
            @RequestParam Integer page,
            @RequestParam Integer perPage,
            @RequestParam String orderState
    ) {
        return orderService.pagedSellerList(page, perPage, orderState);
    }

    @ApiOperation(value = "卖家 - 发货")
    @PostMapping("/{id}/send")
    public Result<Boolean> sendGoods(
            @PathVariable Long id,
            @RequestBody OrderSendCommand command
    ) {
        return orderService.send(id, command);
    }

    @ApiOperation(value = "卖家 - 修改价格")
    @PostMapping("/{id}/change-payment")
    public Result<Boolean> changePayment(
            @PathVariable Long id,
            @RequestParam Integer payment
    ) {
        return orderService.changePayment(id, payment);
    }

    @ApiOperation(value = "买家 - 确认收货")
    @PostMapping("/{id}/sign")
    public Result<Boolean> signGoods(
            @PathVariable Long id
    ) {
        return orderService.sign(id);
    }

    @ApiOperation(value = "取消")
    @PostMapping("/{id}/cancel")
    public Result<Boolean> cancelGoods(
            @PathVariable Long id
    ) {
        return orderService.cancel(id);
    }

    @ApiOperation(value = "评价")
    @PostMapping("/{id}/rate")
    public Result<Boolean> rate(
            @PathVariable Long id,
            @RequestBody OrderRateCommand command
    ) {
        return orderService.rate(id, command);
    }

    @ApiOperation(value = "评价 - 列表")
    @GetMapping("/{id}/rates")
    public Result<List<OrderRateDTO>> rates(@PathVariable Long id) {
        return orderService.rates(id);
    }

    @ApiOperation(value = "买家 - 修改地址")
    @PostMapping("/{id}/change-address")
    public Result<Boolean> changeAddress(
            @PathVariable Long id,
            @RequestBody UpdateOrderAddressCommand command
    ) {
        return orderService.changeAddress(id, command);
    }


}
