package com.yufu.yepshop.persistence.converter;

import com.yufu.yepshop.persistence.DO.GoodsDO;
import com.yufu.yepshop.persistence.DO.GoodsDetailDO;
import com.yufu.yepshop.persistence.DO.RegionDO;
import com.yufu.yepshop.types.command.CreateGoodsCommand;
import com.yufu.yepshop.types.command.UpdateGoodsCommand;
import com.yufu.yepshop.types.dto.GoodsDTO;
import com.yufu.yepshop.types.dto.GoodsListDTO;
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
@Mapper(disableSubMappingMethodsGeneration = true, uses = {StringLongMapper.class})
public interface GoodsConverter {
    GoodsConverter INSTANCE = Mappers.getMapper(GoodsConverter.class);

    /**
     * @return DO
     */
    GoodsDO toDO(GoodsDTO dto, @MappingTarget GoodsDO goodsDO);

    @Mapping(expression = "java(MapStruct.listToStr(entity.getImageUrlList()))", target = "imageUrls")
    GoodsDetailDO toDO(GoodsDTO entity, @MappingTarget GoodsDetailDO goodsDetailDO);

    /**
     * @param model DO
     * @return Entity
     */
    @Mappings({
            @Mapping(source = "id", target = "goods.id"),
            @Mapping(source = "title", target = "goods.title"),
            @Mapping(source = "skuId", target = "goods.skuId"),
            @Mapping(source = "picUrl", target = "goods.picUrl"),
            @Mapping(source = "price", target = "goods.price"),
            @Mapping(source = "sellerId", target = "seller.id"),
            @Mapping(source = "sellerType", target = "seller.type"),
            @Mapping(source = "schoolId", target = "school.id")
    })
    GoodsDTO toDTO(GoodsDO model);

    @Mapping(expression = "java(MapStruct.strToList(model.getImageUrls()))", target = "imageUrlList")
    GoodsDTO toDTO(GoodsDetailDO model, @MappingTarget GoodsDTO entity);

    List<GoodsDTO> toDTOList(List<GoodsDO> models);

    @Mappings({
            @Mapping(source = "id", target = "goods.id"),
            @Mapping(source = "title", target = "goods.title"),
            @Mapping(source = "skuId", target = "goods.skuId"),
            @Mapping(target = "goods.picUrl",
                    expression = "java(goodsDO.getPicUrl()+\"?x-oss-process=style/list\")"),
            @Mapping(source = "price", target = "goods.price"),
            @Mapping(source = "sellerId", target = "seller.id"),
            @Mapping(source = "sellerType", target = "seller.type"),
            @Mapping(source = "schoolId", target = "school.id")
    })
    GoodsListDTO toListDTO(GoodsDO entity);

    List<GoodsListDTO> toGoodsListDTO(List<GoodsDO> models);

    GoodsDTO toDTO(CreateGoodsCommand command);

    GoodsDO toDO(CreateGoodsCommand command);

    GoodsDO toDO(UpdateGoodsCommand command);

    GoodsDO toDO(UpdateGoodsCommand dto, @MappingTarget GoodsDO goodsDO);
}
