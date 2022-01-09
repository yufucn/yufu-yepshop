package com.yufu.yepshop.application;

import com.yufu.yepshop.common.Result;
import com.yufu.yepshop.types.dto.RegionDTO;
import org.w3c.dom.stylesheets.LinkStyle;

import java.util.List;

/**
 * @author wang
 * @date 2022/1/8 14:44
 */
public interface RegionService {
    Result<List<RegionDTO>> getAll();
}
