package com.yufu.yepshop.persistence.converter;

import com.yufu.yepshop.persistence.DO.OrderRateDO;
import com.yufu.yepshop.types.command.OrderRateCommand;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * @author wang
 * @date 2022/1/18 23:22
 */
@Mapper(disableSubMappingMethodsGeneration = true)
public interface OrderRateConverter {
    OrderRateConverter INSTANCE = Mappers.getMapper(OrderRateConverter.class);

    OrderRateDO toDO(OrderRateCommand command);
}
