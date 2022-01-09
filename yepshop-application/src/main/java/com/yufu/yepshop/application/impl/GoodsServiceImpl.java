package com.yufu.yepshop.application.impl;

import com.yufu.yepshop.application.GoodsService;
import com.yufu.yepshop.application.assembler.GoodsAssembler;
import com.yufu.yepshop.common.Result;
import com.yufu.yepshop.domain.goods.Goods;
import com.yufu.yepshop.repository.GoodsRepository;
import com.yufu.yepshop.types.command.CreateGoodsCommand;
import com.yufu.yepshop.types.command.UpdateGoodsCommand;
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
        repository.save(entity);
        return Result.success(true);
    }

    @Override
    public Result<Boolean> update(String id, UpdateGoodsCommand command) {
        Goods entity = goodsAssembler.toEntity(command);
        entity.setImageUrls(command.buildUrls());
        entity.setId(id);
        repository.save(entity);
        return Result.success(true);
    }
}
