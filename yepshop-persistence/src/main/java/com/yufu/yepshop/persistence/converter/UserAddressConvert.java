package com.yufu.yepshop.persistence.converter;

import com.yufu.yepshop.persistence.DO.GoodsDO;
import com.yufu.yepshop.persistence.DO.UserAddressDO;
import com.yufu.yepshop.types.command.CreateGoodsCommand;
import com.yufu.yepshop.types.command.CreateUserAddressCommand;
import com.yufu.yepshop.types.command.UpdateGoodsCommand;
import com.yufu.yepshop.types.command.UpdateUserAddressCommand;
import com.yufu.yepshop.types.dto.UserAddressDTO;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.TargetType;
import org.mapstruct.factory.Mappers;

/**
 * @author wang
 * @date 2022/1/15 22:47
 */
@Mapper(disableSubMappingMethodsGeneration = true, uses = {StringLongMapper.class})
public interface UserAddressConvert {
    UserAddressConvert INSTANCE = Mappers.getMapper(UserAddressConvert.class);

    UserAddressDO toDO(CreateUserAddressCommand command);

    UserAddressDO toDO(UpdateUserAddressCommand command, @MappingTarget UserAddressDO doo);

    UserAddressDTO toDTO(UserAddressDO doo);
}
