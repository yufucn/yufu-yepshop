//package com.yufu.yepshop.application.assembler;
//
//import com.yufu.yepshop.mdm.RegionInfo;
//import com.yufu.yepshop.types.dto.RegionDTO;
//import org.mapstruct.Mapper;
//import org.mapstruct.Mapping;
//import org.mapstruct.factory.Mappers;
//
//import java.util.List;
//
///**
// * @author wang
// * @date 2022/1/8 15:09
// */
//@Mapper(disableSubMappingMethodsGeneration = true)
//public interface RegionAssembler {
//
//    RegionAssembler INSTANCE = Mappers.getMapper(RegionAssembler.class);
//
//    /**
//     * @param entity Entity
//     * @return DTO
//     */
//    RegionDTO toDTO(RegionInfo entity);
//
//
//    List<RegionDTO> toDTOList(List<RegionInfo> models);
//}
