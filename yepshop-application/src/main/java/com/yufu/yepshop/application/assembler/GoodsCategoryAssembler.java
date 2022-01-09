package com.yufu.yepshop.application.assembler;

import com.yufu.yepshop.mdm.GoodsCategory;
import com.yufu.yepshop.mdm.RegionInfo;
import com.yufu.yepshop.types.dto.GoodsCategoryDTO;
import com.yufu.yepshop.types.dto.RegionDTO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * @author wang
 * @date 2022/1/9 10:35
 */
@Mapper(disableSubMappingMethodsGeneration = true)
public interface GoodsCategoryAssembler {
    GoodsCategoryAssembler INSTANCE = Mappers.getMapper(GoodsCategoryAssembler.class);

    /**
     * @param entity Entity
     * @return DTO
     */
    GoodsCategoryDTO toDTO(GoodsCategory entity);


    List<GoodsCategoryDTO> toDTOList(List<GoodsCategory> models);
}
