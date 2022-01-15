//package com.yufu.yepshop.application.assembler;
//
//import com.yufu.yepshop.persistence.DO.OrderDO;
//import com.yufu.yepshop.persistence.DO.OrderItemDO;
//import com.yufu.yepshop.persistence.converter.MapStruct;
//import com.yufu.yepshop.types.dto.BuyerOrderDTO;
//import com.yufu.yepshop.types.dto.OrderItemDTO;
//import com.yufu.yepshop.types.dto.SellerOrderDTO;
//import org.mapstruct.Mapper;
//import org.mapstruct.Mapping;
//import org.mapstruct.Mappings;
//import org.mapstruct.factory.Mappers;
//
//import java.util.List;
//
///**
// * @author wang
// * @date 2022/1/15 13:17
// */
//@Mapper(disableSubMappingMethodsGeneration = true, imports = MapStruct.class)
//public interface OrderAssembler {
//
//    OrderAssembler INSTANCE = Mappers.getMapper(OrderAssembler.class);
//
//
//    @Mappings({
//            @Mapping(source = "sellerId", target = "seller.id"),
//            @Mapping(source = "sellerType", target = "seller.type")
//    })
//    BuyerOrderDTO toBuyerDTO(OrderDO entity);
//
//    OrderItemDTO toDTO(OrderItemDO doo);
//
//    List<OrderItemDTO> toDTO(List<OrderItemDO> entity);
//
//    @Mappings({
//            @Mapping(source = "buyerId", target = "buyer.id")
//    })
//    SellerOrderDTO toSellerDTO(OrderDO entity);
//}
