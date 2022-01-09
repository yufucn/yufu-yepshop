package com.yufu.yepshop.application;

import com.yufu.yepshop.common.Result;
import com.yufu.yepshop.types.command.CreateGoodsCategoryCommand;
import com.yufu.yepshop.types.dto.GoodsCategoryDTO;
import com.yufu.yepshop.types.dto.RegionDTO;

import java.util.List;

/**
 * @author wang
 * @date 2022/1/9 10:38
 */
public interface GoodsCategoryService {
    Result<List<GoodsCategoryDTO>> getAll();

    Result<Boolean> create(CreateGoodsCategoryCommand command);
}
