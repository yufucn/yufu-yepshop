package com.yufu.yepshop.persistence.converter;

import com.yufu.yepshop.domain.goods.Goods;
import com.yufu.yepshop.domain.ordering.Order;
import com.yufu.yepshop.persistence.DO.GoodsDO;
import com.yufu.yepshop.persistence.DO.GoodsDetailDO;
import com.yufu.yepshop.persistence.DO.OrderDO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * @author wang
 * @date 2022/1/8 14:05
 */
@Mapper(disableSubMappingMethodsGeneration = true)
public interface OrderConverter {
    OrderConverter INSTANCE = Mappers.getMapper(OrderConverter.class);

    /**
     * @param entity Entity
     * @return DO
     */
    OrderDO toDO(Order entity);
}
