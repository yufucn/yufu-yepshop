package com.yufu.yepshop.buyer.api;

import com.yufu.yepshop.application.UserAddressService;
import com.yufu.yepshop.common.Result;
import com.yufu.yepshop.types.command.CreateUserAddressCommand;
import com.yufu.yepshop.types.command.UpdateUserAddressCommand;
import com.yufu.yepshop.types.dto.GoodsListDTO;
import com.yufu.yepshop.types.dto.UserAddressDTO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

/**
 * @author wang
 * @date 2022/1/15 22:41
 */
@Api(tags = "Shop - 买家 - 地址")
@RestController
@RequestMapping("/api/v1/shop/buyer/address")
@Slf4j
public class BuyerAddressController {

    private final UserAddressService userAddressService;

    public BuyerAddressController(UserAddressService userAddressService) {
        this.userAddressService = userAddressService;
    }

    @ApiOperation(value = "新增")
    @PostMapping
    public Result<Boolean> create(@RequestBody CreateUserAddressCommand command) {
        return userAddressService.create(command);
    }

    @ApiOperation(value = "更新")
    @PutMapping("/{id}")
    public Result<Boolean> update(
            @PathVariable Long id,
            @RequestBody UpdateUserAddressCommand command) {
        return userAddressService.update(id, command);
    }

    @ApiOperation(value = "详情")
    @GetMapping("/{id}")
    public Result<UserAddressDTO> get(
            @PathVariable Long id) {
        return userAddressService.get(id);
    }

    @ApiOperation(value = "删除")
    @DeleteMapping("/{id}")
    public Result<Boolean> update(
            @PathVariable Long id) {
        return userAddressService.delete(id);
    }

    @ApiOperation(value = "设置默认")
    @PutMapping("/{id}/set-default")
    public Result<Boolean> setDefault(
            @PathVariable Long id) {
        return userAddressService.setDefault(id);
    }

    @ApiOperation(value = "列表")
    @GetMapping()
    public Result<Page<UserAddressDTO>> getGoods(
            @RequestParam Integer page,
            @RequestParam Integer perPage)
    {
        return userAddressService.pagedList(page, perPage);
    }

}
