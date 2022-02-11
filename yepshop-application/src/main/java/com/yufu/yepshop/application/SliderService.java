package com.yufu.yepshop.application;

import com.yufu.yepshop.common.Result;
import com.yufu.yepshop.types.dto.SchoolDTO;
import com.yufu.yepshop.types.dto.SliderDTO;
import com.yufu.yepshop.types.enums.Platform;

import java.util.List;

/**
 * @author wang
 * @date 2022/2/11 21:52
 */
public interface SliderService {
    Result<List<SliderDTO>> list(
            Platform platform,
            String pageId,
            String positionId);
}
