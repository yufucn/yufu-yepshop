package com.yufu.yepshop.application;

import com.yufu.yepshop.common.Result;
import com.yufu.yepshop.types.command.CreateGoodsCommand;
import com.yufu.yepshop.types.command.UpdateGoodsCommand;
import com.yufu.yepshop.types.dto.GoodsDTO;
import com.yufu.yepshop.types.dto.GoodsListDTO;
import com.yufu.yepshop.types.enums.GoodsState;
import org.springframework.data.domain.Page;

/**
 * @author wang
 * @date 2022/1/9 18:24
 */
public interface GoodsService {
    Result<Boolean> create(CreateGoodsCommand command);

    Result<Boolean> update(String id, UpdateGoodsCommand command);

    Result<Boolean> update(String id, GoodsState state);

    Result<Boolean> delete(String id);

    Result<Page<GoodsListDTO>> pagedList(Integer page, Integer perPage, String goodsState);

    Result<GoodsDTO> get(String id);
}
