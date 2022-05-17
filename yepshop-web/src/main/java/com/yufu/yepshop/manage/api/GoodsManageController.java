package com.yufu.yepshop.manage.api;

import com.yufu.yepshop.application.GoodsService;
import com.yufu.yepshop.common.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

    @ApiOperation(value = "删除")
    @DeleteMapping("/{id}")
    public Result<Boolean> deleteGoods(@PathVariable Long id) {
        return goodsService.delete(id);
    }
}
