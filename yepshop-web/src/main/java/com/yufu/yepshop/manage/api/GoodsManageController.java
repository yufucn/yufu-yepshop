package com.yufu.yepshop.manage.api;

import com.yufu.yepshop.application.GoodsService;
import com.yufu.yepshop.common.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

/**
 * @author wang
 * @date 2022/5/17 21:28
 */
@Api(tags = "Shop - 管理 - 闲置")
@RestController
@RequestMapping("/api/v1/manage/goods")
@Slf4j
public class GoodsManageController {

    private final GoodsService goodsService;

    public GoodsManageController(GoodsService goodsService) {
        this.goodsService = goodsService;
    }

    @ApiOperation(value = "屏蔽")
    @PutMapping("/{id}/block")
    public Result<Boolean> deleteGoods(@PathVariable Long id) {
        return goodsService.block(id);
    }

    @ApiOperation(value = "通过")
    @PutMapping("/{id}/approved")
    public Result<Boolean> approvedGoods(@PathVariable Long id) {
        return goodsService.approved(id);
    }
}
