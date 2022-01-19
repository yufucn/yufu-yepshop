package com.yufu.yepshop.persistence.converter;

import com.yufu.yepshop.persistence.DO.UserAddressDO;
import com.yufu.yepshop.persistence.DO.UserSchoolDO;
import com.yufu.yepshop.types.command.CreateUserAddressCommand;
import com.yufu.yepshop.types.command.UpdateUserAddressCommand;
import com.yufu.yepshop.types.dto.UserAddressDTO;
import com.yufu.yepshop.types.value.SchoolValue;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * @author wang
 * @date 2022/1/15 22:47
 */
@Mapper(disableSubMappingMethodsGeneration = true, uses = {StringLongMapper.class})
public interface UserSchoolConvert {
    UserSchoolConvert INSTANCE = Mappers.getMapper(UserSchoolConvert.class);

    @Mappings({
            @Mapping(source = "schoolId", target = "id"),
            @Mapping(source = "schoolName", target = "name"),

    })
    SchoolValue toDTO(UserSchoolDO doo);

    List<SchoolValue> toDTO(List<UserSchoolDO> doo);
}
