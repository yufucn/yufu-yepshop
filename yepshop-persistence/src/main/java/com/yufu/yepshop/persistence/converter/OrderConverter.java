package com.yufu.yepshop.persistence.converter;

import com.yufu.yepshop.persistence.DO.GoodsDO;
import com.yufu.yepshop.persistence.DO.GoodsDetailDO;
import com.yufu.yepshop.persistence.DO.OrderDO;
import com.yufu.yepshop.persistence.DO.OrderItemDO;
import com.yufu.yepshop.types.dto.BuyerOrderDTO;
import com.yufu.yepshop.types.dto.OrderDTO;
import com.yufu.yepshop.types.dto.OrderItemDTO;
import com.yufu.yepshop.types.dto.SellerOrderDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Mappings;
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
    OrderDO toDO(OrderDTO entity);

    @Mappings({
            @Mapping(source = "goodsId", target = "goods.id"),
            @Mapping(source = "title", target = "goods.title"),
            @Mapping(source = "skuId", target = "goods.skuId"),
            @Mapping(source = "picUrl", target = "goods.picUrl"),
            @Mapping(source = "price", target = "goods.price")
    })
    List<OrderItemDO> toDO(List<OrderItemDTO> value);

    @Mappings({
            @Mapping(source = "goods.id", target = "goodsId"),
            @Mapping(source = "goods.title", target = "title"),
            @Mapping(source = "goods.skuId", target = "skuId"),
            @Mapping(source = "goods.picUrl", target = "picUrl"),
            @Mapping(source = "goods.price", target = "price")
    })
    OrderItemDO toDO(OrderItemDTO entity);


    OrderDTO toDTO(OrderDO doo);

    List<OrderItemDTO> toDTO(List<OrderItemDO> value);

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
