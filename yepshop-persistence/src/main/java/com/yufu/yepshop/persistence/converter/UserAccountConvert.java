package com.yufu.yepshop.persistence.converter;

import com.yufu.yepshop.persistence.DO.GoodsDO;
import com.yufu.yepshop.persistence.DO.UserAccountDO;
import com.yufu.yepshop.types.dto.GoodsListDTO;
import com.yufu.yepshop.types.dto.UserAccountDTO;
import com.yufu.yepshop.types.value.UserValue;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * @author wang
 * @date 2022/1/16 22:52
 */
@Mapper(disableSubMappingMethodsGeneration = true, uses = {StringLongMapper.class})
public interface UserAccountConvert {
    UserAccountConvert INSTANCE = Mappers.getMapper(UserAccountConvert.class);

    UserAccountDTO toDTO(UserAccountDO doo);

    List<UserAccountDTO> toListDTO(List<UserAccountDO> models);

    UserValue toUserValue(UserAccountDO doo);

    List<UserValue> toUserValue(List<UserAccountDO> doo);
}
