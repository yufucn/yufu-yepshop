package com.yufu.yepshop.persistence.converter;

import com.yufu.yepshop.persistence.DO.SchoolDO;
import com.yufu.yepshop.persistence.DO.SliderDO;
import com.yufu.yepshop.types.command.CreateSchoolCommand;
import com.yufu.yepshop.types.dto.SchoolDTO;
import com.yufu.yepshop.types.dto.SliderDTO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * @author wang
 * @date 2022/1/16 22:09
 */
@Mapper(disableSubMappingMethodsGeneration = true)
public interface SliderConvert {

    SliderConvert INSTANCE = Mappers.getMapper(SliderConvert.class);

    /**
     * @param entity Entity
     * @return DO
     */
    SliderDTO toDTO(SliderDO entity);

    List<SliderDTO> toDTO(List<SliderDO> entity);
}
