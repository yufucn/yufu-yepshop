package com.yufu.yepshop.application.impl;

import com.yufu.yepshop.application.GoodsCategoryService;
import com.yufu.yepshop.common.Result;
import com.yufu.yepshop.persistence.converter.GoodsCategoryConverter;
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

    public GoodsCategoryServiceImpl(GoodsCategoryRepository repository) {
        this.repository = repository;
    }

    @Override
    public Result<List<GoodsCategoryDTO>> getAll() {
        List<GoodsCategoryDTO> list = repository.findAll();
        return Result.success(list);
    }

    @Override
    public Result<Boolean> create(CreateGoodsCategoryCommand command) {
        GoodsCategoryDTO entity = new GoodsCategoryDTO();
        entity.setDescription(command.getDescription());
        entity.setName(command.getName());
        entity.setParentId(command.getParentId());
        entity.setSortId(command.getSortId());
        return Result.success(repository.save(entity));
    }
}
