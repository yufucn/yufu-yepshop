package com.yufu.yepshop.persistence.converter;

import com.yufu.yepshop.persistence.DO.GoodsDO;
import com.yufu.yepshop.persistence.DO.GoodsDetailDO;
import com.yufu.yepshop.persistence.DO.RequirementDO;
import com.yufu.yepshop.types.command.CreateGoodsCommand;
import com.yufu.yepshop.types.command.CreateRequirementCommand;
import com.yufu.yepshop.types.command.UpdateGoodsCommand;
import com.yufu.yepshop.types.dto.GoodsDTO;
import com.yufu.yepshop.types.dto.GoodsListDTO;
import com.yufu.yepshop.types.dto.RequirementListDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * @author wang
 * @date 2022/1/8 14:05
 */
@Mapper(disableSubMappingMethodsGeneration = true, uses = {StringLongMapper.class})
public interface RequirementConverter {
    RequirementConverter INSTANCE = Mappers.getMapper(RequirementConverter.class);

    @Mappings({
            @Mapping(source = "schoolId", target = "school.id"),
            @Mapping(source = "creatorId", target = "user.id")
    })
    RequirementListDTO toListDTO(RequirementDO entity);

    @Mappings({
            @Mapping(source = "schoolId", target = "school.id"),
            @Mapping(source = "creatorId", target = "user.id")
    })
    List<RequirementListDTO> toListDTO(List<RequirementDO> models);

    RequirementDO toDO(CreateRequirementCommand command);

    RequirementDO toDO(CreateRequirementCommand dto, @MappingTarget RequirementDO goodsDO);

}
