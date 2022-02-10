package com.yufu.yepshop.application;

import com.yufu.yepshop.common.Result;
import com.yufu.yepshop.types.command.CreateGoodsCommand;
import com.yufu.yepshop.types.command.CreateGoodsCommentCommand;
import com.yufu.yepshop.types.command.CreateGoodsCommentReplyCommand;
import com.yufu.yepshop.types.command.UpdateGoodsCommand;
import com.yufu.yepshop.types.dto.CommentDTO;
import com.yufu.yepshop.types.dto.CommentReplyDTO;
import com.yufu.yepshop.types.dto.GoodsDTO;
import com.yufu.yepshop.types.dto.GoodsListDTO;
import com.yufu.yepshop.types.enums.GoodsState;
import com.yufu.yepshop.types.query.GoodsQuery;
import org.springframework.data.domain.Page;

/**
 * @author wang
 * @date 2022/1/9 18:24
 */
public interface GoodsService {
    Result<String> create(CreateGoodsCommand command);

    Result<Boolean> update(Long id, UpdateGoodsCommand command);

    Result<Boolean> update(Long id, GoodsState state);

    Result<Boolean> delete(Long id);

    Result<Page<GoodsListDTO>> pagedList(Long creatorId, Integer page, Integer perPage, String goodsState);

    Result<GoodsDTO> get(Long id);

    Result<Page<GoodsListDTO>> search(GoodsQuery query);

    Result<Page<GoodsListDTO>> collectionList(Integer page, Integer perPage);

    Result<Page<GoodsListDTO>> viewList(Integer page, Integer perPage);

    Result<Boolean> viewClear();

    Result<Boolean> comment(Long id, CreateGoodsCommentCommand command);

    Result<Boolean> commentDelete(Long id, Long commentId);

    Result<Boolean> commentReply(Long id, Long commentId, CreateGoodsCommentReplyCommand command);

    Result<Boolean> commentReplyDelete(Long id, Long commentId, Long replyId);

    Result<Page<CommentDTO>> commentsGoods(Long id, Integer page, Integer perPage);

    Result<Page<CommentReplyDTO>> commentReplyGoodsList(
            Long id,
            Long commentId,
            Integer page,
            Integer perPage);
}
