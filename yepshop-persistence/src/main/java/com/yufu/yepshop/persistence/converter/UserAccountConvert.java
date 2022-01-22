package com.yufu.yepshop.persistence.converter;

import com.yufu.yepshop.persistence.DO.UserAccountDO;
import com.yufu.yepshop.types.dto.UserAccountDTO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * @author wang
 * @date 2022/1/16 22:52
 */
@Mapper(disableSubMappingMethodsGeneration = true, uses = {StringLongMapper.class})
public interface UserAccountConvert {
    UserAccountConvert INSTANCE = Mappers.getMapper(UserAccountConvert.class);

    UserAccountDTO toDTO(UserAccountDO doo);
}
