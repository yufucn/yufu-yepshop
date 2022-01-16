package com.yufu.yepshop.mdm.api;

import com.yufu.yepshop.application.SchoolService;
import com.yufu.yepshop.common.Result;
import com.yufu.yepshop.types.command.CreateSchoolCommand;
import com.yufu.yepshop.types.dto.RegionDTO;
import com.yufu.yepshop.types.dto.SchoolDTO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author wang
 * @date 2022/1/16 21:50
 */
@Api(tags = "主数据 - 学校")
@RestController
@RequestMapping("/api/v1/mdm/school")
public class SchoolController {

    private final SchoolService schoolService;

    public SchoolController(SchoolService schoolService) {
        this.schoolService = schoolService;
    }


    @ApiOperation(value = "所有学校")
    @GetMapping
    public Result<List<SchoolDTO>> getAll() {
        return schoolService.getAll();
    }

    @ApiOperation(value = "添加学校")
    @PostMapping
    public Result<Boolean> create(@RequestBody CreateSchoolCommand command) {
        return schoolService.create(command);
    }
}
