package com.yufu.yepshop.mdm.api;

import com.yufu.yepshop.application.RegionService;
import com.yufu.yepshop.common.Result;
import com.yufu.yepshop.types.dto.RegionDTO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author wang
 * @date 2022/1/8 13:54
 */
@Api(tags = "主数据 - 行政区域")
@RestController
@RequestMapping("/api/v1/region")
public class RegionController {

    private final RegionService regionService;

    public RegionController(RegionService regionService) {
        this.regionService = regionService;
    }

    @ApiOperation(value = "所有省市区")
    @GetMapping
    public Result<List<RegionDTO>> getAll() {
        return regionService.getAll();
    }
}
