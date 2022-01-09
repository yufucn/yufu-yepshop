package com.yufu.yepshop.application.assembler;

import com.yufu.yepshop.domain.goods.Goods;
import com.yufu.yepshop.mdm.RegionInfo;
import com.yufu.yepshop.types.command.CreateGoodsCommand;
import com.yufu.yepshop.types.command.UpdateGoodsCommand;
import com.yufu.yepshop.types.dto.GoodsDTO;
import com.yufu.yepshop.types.dto.RegionDTO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * @author wang
 * @date 2022/1/8 15:09
 */
@Mapper(disableSubMappingMethodsGeneration = true)
public interface GoodsAssembler {

    GoodsAssembler INSTANCE = Mappers.getMapper(GoodsAssembler.class);

    /**
     * @param entity Entity
     * @return DTO
     */
    GoodsDTO toDTO(Goods entity);

    Goods toEntity(CreateGoodsCommand command);

    Goods toEntity(UpdateGoodsCommand command);
}
