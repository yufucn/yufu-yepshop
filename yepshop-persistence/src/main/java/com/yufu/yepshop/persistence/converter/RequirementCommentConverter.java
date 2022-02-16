package com.yufu.yepshop.persistence.converter;

import com.yufu.yepshop.persistence.DO.GoodsCommentDO;
import com.yufu.yepshop.persistence.DO.GoodsCommentReplyDO;
import com.yufu.yepshop.persistence.DO.RequirementCommentDO;
import com.yufu.yepshop.persistence.DO.RequirementCommentReplyDO;
import com.yufu.yepshop.types.dto.CommentDTO;
import com.yufu.yepshop.types.dto.CommentReplyDTO;
import com.yufu.yepshop.types.dto.RequirementCommentDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

/**
 * @author wang
 * @date 2022/1/29 11:45
 */
@Mapper(disableSubMappingMethodsGeneration = true, uses = {StringLongMapper.class})
public interface RequirementCommentConverter {
    RequirementCommentConverter INSTANCE = Mappers.getMapper(RequirementCommentConverter.class);


    @Mappings({
            @Mapping(source = "creatorId", target = "user.id"),
    })
    RequirementCommentDTO toDTO(RequirementCommentDO commentDO);


    @Mappings({
            @Mapping(source = "creatorId", target = "user.id"),
            @Mapping(source = "replyToUserId", target = "toUser.id"),
    })
    CommentReplyDTO toReplyDTO(RequirementCommentReplyDO commentDO);
}
