package com.yufu.yepshop.seller.api;

import com.yufu.yepshop.application.OrderService;
import com.yufu.yepshop.common.Result;
import com.yufu.yepshop.types.dto.SellerOrderDTO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author wang
 * @date 2022/1/15 14:37
 */
@Api(tags = "Shop - 卖家 - 订单")
@RestController
@RequestMapping("/api/v1/shop/seller/orders")
@Slf4j
public class SellerController {

    private final OrderService orderService;

    public SellerController(OrderService orderService) {
        this.orderService = orderService;
    }

    @ApiOperation(value = "列表")
    @GetMapping
    public Result<Page<SellerOrderDTO>> getMyOrders(
            @RequestParam Integer page,
            @RequestParam(name = "per_page") Integer perPage,
            @RequestParam(value = "ALL(所有订单)、WAIT_SELLER_SEND_GOODS(待发货)、WAIT_BUYER_CONFIRM_GOODS(待收货)") String orderState
    ) {
        return orderService.pagedSellerList(page, perPage, orderState);
    }
}
