package com.yufu.yepshop.slider.api;

import com.yufu.yepshop.application.SliderService;
import com.yufu.yepshop.common.Result;
import com.yufu.yepshop.types.dto.SliderDTO;
import com.yufu.yepshop.types.enums.Platform;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author wang
 * @date 2022/2/11 21:16
 */
@Api(tags = "Shop - 广告")
@RestController
@RequestMapping("/api/v1/shop/slider")
@Slf4j
public class SliderController {

    private final SliderService sliderService;

    public SliderController(SliderService sliderService) {
        this.sliderService = sliderService;
    }

    @ApiOperation(value = "轮播列表")
    @GetMapping
    public Result<List<SliderDTO>> sliderList(
            @RequestParam Platform platform,
            @RequestParam String pageId,
            @RequestParam String positionId
    ) {
        return sliderService.list(platform, pageId, positionId);
    }
}
