package com.yufu.yepshop.persistence.converter;

import com.yufu.yepshop.mdm.GoodsCategory;
import com.yufu.yepshop.mdm.RegionInfo;
import com.yufu.yepshop.persistence.DO.GoodsCategoryDO;
import com.yufu.yepshop.persistence.DO.RegionDO;
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
    GoodsCategoryDO toDO(GoodsCategory entity);
    
    /**
     * @param model DO
     * @return Entity
     */
    GoodsCategory toEntity(GoodsCategoryDO model);


    List<GoodsCategory> toEntityList(List<GoodsCategoryDO> models);
}
