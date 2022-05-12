package com.yufu.yepshop.persistence.converter;

import com.yufu.yepshop.persistence.DO.OrderDO;
import com.yufu.yepshop.persistence.DO.OrderItemDO;
import com.yufu.yepshop.types.dto.*;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * @author wang
 * @date 2022/1/8 14:05
 */
@Mapper(disableSubMappingMethodsGeneration = true, uses = {StringLongMapper.class})
public interface OrderConverter {
    OrderConverter INSTANCE = Mappers.getMapper(OrderConverter.class);

    /**
     * @param entity Entity
     * @return DO
     */
    @Mappings({
            @Mapping(source = "buyer.id", target = "buyerId"),
            @Mapping(source = "seller.id", target = "sellerId"),
            @Mapping(source = "seller.type", target = "sellerType"),
    })
    OrderDO toDO(OrderDTO entity);

    List<OrderItemDO> toDO(List<OrderItemDTO> value);

    @Mappings({
            @Mapping(source = "goods.id", target = "goodsId"),
            @Mapping(source = "goods.title", target = "title"),
            @Mapping(source = "goods.skuId", target = "skuId"),
            @Mapping(source = "goods.picUrl", target = "picUrl"),
            @Mapping(source = "goods.price", target = "price")
    })
    OrderItemDO toDO(OrderItemDTO entity);

    @Mappings({
            @Mapping(source = "sellerId", target = "seller.id"),
            @Mapping(source = "sellerType", target = "seller.type"),
            @Mapping(source = "buyerId", target = "buyer.id")

    })
    OrderDTO toDTO(OrderDO doo);

    List<OrderDTO> toListDTO(List<OrderDO> orders);


    List<OrderItemDTO> toDTO(List<OrderItemDO> value);

    @Mappings({
            @Mapping(source = "goodsId", target = "goods.id"),
            @Mapping(source = "title", target = "goods.title"),
            @Mapping(source = "skuId", target = "goods.skuId"),
            @Mapping(source = "picUrl", target = "goods.picUrl"),
            @Mapping(source = "price", target = "goods.price")
    })
    OrderItemDTO toDTO(OrderItemDO entity);

    @Mappings({
            @Mapping(source = "sellerId", target = "seller.id"),
            @Mapping(source = "sellerType", target = "seller.type")
    })
    BuyerOrderDTO toBuyerDTO(OrderDO entity);

    @Mappings({
            @Mapping(source = "buyerId", target = "buyer.id")
    })
    SellerOrderDTO toSellerDTO(OrderDO entity);
}
