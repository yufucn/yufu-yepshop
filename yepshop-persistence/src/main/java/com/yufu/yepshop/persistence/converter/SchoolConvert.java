package com.yufu.yepshop.persistence.converter;

import com.yufu.yepshop.persistence.DO.RegionDO;
import com.yufu.yepshop.persistence.DO.SchoolDO;
import com.yufu.yepshop.types.command.CreateSchoolCommand;
import com.yufu.yepshop.types.dto.RegionDTO;
import com.yufu.yepshop.types.dto.SchoolDTO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * @author wang
 * @date 2022/1/16 22:09
 */
@Mapper(disableSubMappingMethodsGeneration = true)
public interface SchoolConvert {

    SchoolConvert INSTANCE = Mappers.getMapper(SchoolConvert.class);

    /**
     * @param entity Entity
     * @return DO
     */
    SchoolDTO toDTO(SchoolDO entity);


    List<SchoolDTO> toDTOList(List<SchoolDO> models);

    SchoolDO toDO(CreateSchoolCommand command);
}
