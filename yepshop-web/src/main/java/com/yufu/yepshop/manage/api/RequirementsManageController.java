package com.yufu.yepshop.manage.api;

import com.yufu.yepshop.application.GoodsService;
import com.yufu.yepshop.application.RequirementService;
import com.yufu.yepshop.common.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author wang
 * @date 2022/5/17 21:32
 */
@Api(tags = "Shop - 管理 - 闲置")
@RestController
@RequestMapping("/api/v1/manage/requirements")
@Slf4j
public class RequirementsManageController {

    private final RequirementService requirementService;

    public RequirementsManageController(RequirementService requirementService) {
        this.requirementService = requirementService;
    }

    @ApiOperation(value = "屏蔽")
    @PutMapping("/{id}/block")
    public Result<Boolean> block(@PathVariable Long id) {
        return requirementService.block(id);
    }

    @ApiOperation(value = "通过")
    @PutMapping("/{id}/approved")
    public Result<Boolean> approved(@PathVariable Long id) {
        return requirementService.approved(id);
    }
}
