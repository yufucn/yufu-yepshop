package com.yufu.yepshop.application;

import com.yufu.yepshop.common.Result;
import com.yufu.yepshop.types.command.CreateGoodsCommand;
import com.yufu.yepshop.types.command.CreateUserAddressCommand;
import com.yufu.yepshop.types.command.UpdateGoodsCommand;
import com.yufu.yepshop.types.command.UpdateUserAddressCommand;
import com.yufu.yepshop.types.dto.GoodsDTO;
import com.yufu.yepshop.types.dto.GoodsListDTO;
import com.yufu.yepshop.types.dto.UserAddressDTO;
import com.yufu.yepshop.types.enums.GoodsState;
import org.springframework.data.domain.Page;

/**
 * @author wang
 * @date 2022/1/15 22:42
 */

public interface UserAddressService {

    Result<Boolean> create(CreateUserAddressCommand command);

    Result<Boolean> update(Long id, UpdateUserAddressCommand command);

    Result<Boolean> setDefault(Long id);

    Result<Boolean> delete(Long id);

    Result<Page<UserAddressDTO>> pagedList(Integer page, Integer perPage);

    Result<UserAddressDTO> get(Long id);
}
