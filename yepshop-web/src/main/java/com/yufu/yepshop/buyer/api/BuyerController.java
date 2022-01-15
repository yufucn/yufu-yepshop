package com.yufu.yepshop.buyer.api;

import com.yufu.yepshop.application.OrderService;
import com.yufu.yepshop.common.Result;
import com.yufu.yepshop.types.dto.BuyerOrderDTO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

/**
 * @author wang
 * @date 2022/1/15 12:54
 */
@Api(tags = "Shop - 买家 - 订单")
@RestController
@RequestMapping("/api/v1/shop/buyer/orders")
@Slf4j
public class BuyerController {

    private final OrderService orderService;

    public BuyerController(OrderService orderService) {
        this.orderService = orderService;
    }

    @ApiOperation(value = "列表")
    @GetMapping()
    public Result<Page<BuyerOrderDTO>> getMyOrders(
            @RequestParam Integer page,
            @RequestParam(name = "per_page") Integer perPage,
            @RequestParam String orderState
    ) {
        return orderService.pagedBuyerList(page, perPage, orderState);
    }
}
