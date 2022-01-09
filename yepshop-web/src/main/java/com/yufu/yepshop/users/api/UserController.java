package com.yufu.yepshop.users.api;

import com.yufu.yepshop.common.Result;
import com.yufu.yepshop.types.dto.ItemDTO;
import com.yufu.yepshop.types.dto.UserDTO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

/**
 * @author wang
 * @date 2022/1/7 1:02
 */
@Api(tags = "Shop - 用户")
@RestController
@RequestMapping("/api/v1/users")
@Slf4j
public class UserController {

    @ApiOperation(value = "详情")
    @GetMapping("/{id}")
    public Result<UserDTO> getUser(@PathVariable String id) {
        return null;
    }


    @ApiOperation(value = "闲置 - 列表（上架的、下架的、卖出的、草稿）")
    @GetMapping("/{id}/goods")
    public Result<Page<ItemDTO>> getUserGoods(
            @PathVariable String id,
            @RequestParam Integer page,
            @RequestParam(name = "per_page") Integer perPage,
            @RequestParam(value = "up,down,sold,draft") String state
    ) {
        return null;
    }

    @ApiOperation(value = "订单 - 列表")
    @GetMapping("/{id}/orders")
    public Result<Page<ItemDTO>> getUserOrders(
            @PathVariable String id,
            @RequestParam Integer page,
            @RequestParam(name = "per_page") Integer perPage,
            @RequestParam(value = "up,down,draft") String state
    ) {
        return null;
    }
}
