package com.yufu.yepshop.mdm.api;

import com.yufu.yepshop.application.GoodsCategoryService;
import com.yufu.yepshop.common.Result;
import com.yufu.yepshop.types.command.CreateGoodsCategoryCommand;
import com.yufu.yepshop.types.dto.GoodsCategoryDTO;
import com.yufu.yepshop.types.dto.RegionDTO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * @author wang
 * @date 2022/1/8 15:54
 */
@Api(tags = "主数据 - 分类")
@RestController
@RequestMapping("/api/v1/category")
public class CategoryController {
    private final GoodsCategoryService service;

    public CategoryController(GoodsCategoryService service) {
        this.service = service;
    }

    @ApiOperation(value = "所有分类")
    @GetMapping
    public Result<List<GoodsCategoryDTO>> getAll() {
        return service.getAll();
    }

    @ApiOperation(value = "新增分类")
    @PostMapping
    public Result<Boolean> create(@RequestBody @Valid CreateGoodsCategoryCommand command) {
        return service.create(command);
    }
}
