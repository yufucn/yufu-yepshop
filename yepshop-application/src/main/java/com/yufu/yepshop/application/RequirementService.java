package com.yufu.yepshop.application;

import com.yufu.yepshop.common.Result;
import com.yufu.yepshop.types.command.*;
import com.yufu.yepshop.types.dto.*;
import com.yufu.yepshop.types.enums.GoodsState;
import com.yufu.yepshop.types.enums.RequirementState;
import com.yufu.yepshop.types.query.GoodsQuery;
import com.yufu.yepshop.types.query.RequirementQuery;
import org.springframework.data.domain.Page;

/**
 * @author wang
 * @date 2022/2/16 23:30
 */
public interface RequirementService {
    Result<String> create(CreateRequirementCommand command);

    Result<Boolean> update(Long id, CreateRequirementCommand command);

    Result<Boolean> update(Long id, RequirementState state);

    Result<Boolean> delete(Long id);

    Result<Page<RequirementListDTO>> pagedList(Long creatorId, Integer page, Integer perPage, String goodsState);

    Result<RequirementListDTO> get(Long id);

    Result<Page<RequirementListDTO>> search(RequirementQuery query);

    Result<Page<RequirementListDTO>> tip(Integer page, Integer perPage);

    Result<String> comment(Long id, CreateCommentCommand command);

    Result<Boolean> commentDelete(Long id, Long commentId);

    Result<String> commentReply(Long id, Long commentId, CreateCommentReplyCommand command);

    Result<Boolean> commentReplyDelete(Long id, Long commentId, Long replyId);

    Result<Page<RequirementCommentDTO>> comments(Long id, Integer page, Integer perPage);

    Result<Page<CommentReplyDTO>> commentReplyList(
            Long id,
            Long commentId,
            Integer page,
            Integer perPage);
}
