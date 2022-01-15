package com.yufu.yepshop.persistence.converter;

import com.yufu.yepshop.persistence.DO.GoodsCategoryDO;
import com.yufu.yepshop.persistence.DO.RegionDO;
import com.yufu.yepshop.types.dto.GoodsCategoryDTO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * @author wang
 * @date 2022/1/9 10:31
 */
@Mapper(disableSubMappingMethodsGeneration = true)
public interface GoodsCategoryConverter {
    GoodsCategoryConverter INSTANCE = Mappers.getMapper(GoodsCategoryConverter.class);

    /**
     * @param entity Entity
     * @return DO
     */
    GoodsCategoryDO toDO(GoodsCategoryDTO entity);

    /**
     * @param model DO
     * @return Entity
     */
    GoodsCategoryDTO toDTO(GoodsCategoryDO model);


    List<GoodsCategoryDTO> toDTOList(List<GoodsCategoryDO> models);
}
