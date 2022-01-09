package com.yufu.yepshop.application.impl;

import com.yufu.yepshop.application.GoodsCategoryService;
import com.yufu.yepshop.application.assembler.GoodsCategoryAssembler;
import com.yufu.yepshop.common.Result;
import com.yufu.yepshop.mdm.GoodsCategory;
import com.yufu.yepshop.mdm.RegionInfo;
import com.yufu.yepshop.repository.GoodsCategoryRepository;
import com.yufu.yepshop.types.command.CreateGoodsCategoryCommand;
import com.yufu.yepshop.types.dto.GoodsCategoryDTO;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author wang
 * @date 2022/1/9 10:38
 */
@Service
public class GoodsCategoryServiceImpl implements GoodsCategoryService {

    private final GoodsCategoryRepository repository;
    private final GoodsCategoryAssembler assembler = GoodsCategoryAssembler.INSTANCE;

    public GoodsCategoryServiceImpl(GoodsCategoryRepository repository) {
        this.repository = repository;
    }

    @Override
    public Result<List<GoodsCategoryDTO>> getAll() {
        List<GoodsCategory> list = repository.findAll();
        return Result.success(assembler.toDTOList(list));
    }

    @Override
    public Result<Boolean> create(CreateGoodsCategoryCommand command) {
        GoodsCategory entity = new GoodsCategory();
        entity.setDescription(command.getDescription());
        entity.setName(command.getName());
        entity.setParentId(command.getParentId());
        entity.setSortId(command.getSortId());
        return Result.success(repository.save(entity));
    }
}
