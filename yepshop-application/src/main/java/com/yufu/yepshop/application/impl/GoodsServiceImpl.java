package com.yufu.yepshop.application.impl;

import com.yufu.yepshop.application.GoodsService;
import com.yufu.yepshop.application.assembler.GoodsAssembler;
import com.yufu.yepshop.common.Result;
import com.yufu.yepshop.domain.goods.Goods;
import com.yufu.yepshop.persistence.DO.GoodsDO;
import com.yufu.yepshop.repository.GoodsRepository;
import com.yufu.yepshop.types.command.CreateGoodsCommand;
import com.yufu.yepshop.types.command.UpdateGoodsCommand;
import com.yufu.yepshop.types.dto.GoodsDTO;
import com.yufu.yepshop.types.dto.GoodsListDTO;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

/**
 * @author wang
 * @date 2022/1/9 18:25
 */
@Service
public class GoodsServiceImpl implements GoodsService {

    private final GoodsRepository repository;
    private final GoodsAssembler goodsAssembler = GoodsAssembler.INSTANCE;

    public GoodsServiceImpl(GoodsRepository repository) {
        this.repository = repository;
    }

    @Override
    public Result<Boolean> create(CreateGoodsCommand command) {
        Goods entity = goodsAssembler.toEntity(command);
        entity.setImageUrls(command.buildUrls());
        entity.setTitle(entity.getTitleFromText());
        repository.save(entity);
        return Result.success(true);
    }

    @Override
    public Result<Boolean> update(String id, UpdateGoodsCommand command) {
        Goods entity = goodsAssembler.toEntity(command);
        entity.setImageUrls(command.buildUrls());
        entity.setTitle(entity.getTitleFromText());
        entity.setId(id);
        repository.save(entity);
        return Result.success(true);
    }

    @Override
    public Result<Page<GoodsListDTO>> pagedList(Integer page, Integer perPage) {
        Page<GoodsListDTO> paged = repository.pagedList(page,perPage).map(this::convert);
        return Result.success(paged);
    }

    @Override
    public Result<GoodsDTO> get(String id) {
        Goods entity = repository.get(id);
        GoodsDTO dto = goodsAssembler.toDTO(entity);
        return Result.success(dto);
    }

    private GoodsListDTO convert(Goods gDo) {
        return goodsAssembler.toListDTO(gDo);
    }
}
