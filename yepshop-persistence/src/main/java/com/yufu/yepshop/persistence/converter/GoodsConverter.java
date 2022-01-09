package com.yufu.yepshop.persistence.converter;

import com.yufu.yepshop.domain.goods.Goods;
import com.yufu.yepshop.mdm.RegionInfo;
import com.yufu.yepshop.persistence.DO.GoodsDO;
import com.yufu.yepshop.persistence.DO.GoodsDetailDO;
import com.yufu.yepshop.persistence.DO.RegionDO;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * @author wang
 * @date 2022/1/8 14:05
 */
@Mapper(disableSubMappingMethodsGeneration = true)
public interface GoodsConverter {
    GoodsConverter INSTANCE = Mappers.getMapper(GoodsConverter.class);

    /**
     * @param entity Entity
     * @return DO
     */
    GoodsDO toDO(Goods entity, @MappingTarget GoodsDO goodsDO);

    GoodsDetailDO toDO(Goods entity, @MappingTarget GoodsDetailDO goodsDetailDO);
    /**
     * @param model DO
     * @return Entity
     */
    Goods toEntity(GoodsDO model);


    List<Goods> toEntityList(List<GoodsDO> models);
}
