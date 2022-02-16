package com.yufu.yepshop.persistence.converter;

import com.yufu.yepshop.persistence.DO.OrderRateDO;
import com.yufu.yepshop.types.command.OrderRateCommand;
import com.yufu.yepshop.types.dto.OrderRateDTO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * @author wang
 * @date 2022/1/18 23:22
 */
@Mapper(disableSubMappingMethodsGeneration = true, uses = {StringLongMapper.class})
public interface OrderRateConverter {
    OrderRateConverter INSTANCE = Mappers.getMapper(OrderRateConverter.class);

    OrderRateDO toDO(OrderRateCommand command);

    OrderRateDTO toDTO(OrderRateDO doo);

    List<OrderRateDTO> toListDTO(List<OrderRateDO> doo);
}
