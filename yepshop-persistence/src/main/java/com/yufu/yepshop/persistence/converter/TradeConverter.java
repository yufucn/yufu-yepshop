package com.yufu.yepshop.persistence.converter;

import com.yufu.yepshop.persistence.DO.OrderDO;
import com.yufu.yepshop.persistence.DO.TradeDO;
import com.yufu.yepshop.types.dto.TradeDTO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * @author wang
 * @date 2022/1/14 22:26
 */
@Mapper(disableSubMappingMethodsGeneration = true)
public interface TradeConverter {
    TradeConverter INSTANCE = Mappers.getMapper(TradeConverter.class);

    /**
     * @param entity Entity
     * @return DO
     */
    TradeDO toDO(TradeDTO entity);

    TradeDTO toDTO(TradeDO tradeDO);
}
