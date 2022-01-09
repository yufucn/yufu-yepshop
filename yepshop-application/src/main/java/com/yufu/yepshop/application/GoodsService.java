package com.yufu.yepshop.application;

import com.yufu.yepshop.common.Result;
import com.yufu.yepshop.types.command.CreateGoodsCommand;
import com.yufu.yepshop.types.command.UpdateGoodsCommand;

/**
 * @author wang
 * @date 2022/1/9 18:24
 */
public interface GoodsService {
    Result<Boolean> create(CreateGoodsCommand command);

    Result<Boolean> update(String id, UpdateGoodsCommand command);
}
