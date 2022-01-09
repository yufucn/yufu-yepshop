package com.yufu.yepshop.persistence.converter;

import com.yufu.yepshop.mdm.RegionInfo;
import com.yufu.yepshop.persistence.DO.RegionDO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * @author wang
 * @date 2022/1/8 14:05
 */
@Mapper(disableSubMappingMethodsGeneration = true)
public interface RegionConverter {
    RegionConverter INSTANCE = Mappers.getMapper(RegionConverter.class);

    /**
     * @param entity Entity
     * @return DO
     */
    RegionDO toDO(RegionInfo entity);


    /**
     * @param model DO
     * @return Entity
     */
    RegionInfo toEntity(RegionDO model);


    List<RegionInfo> toEntityList(List<RegionDO> models);
}
