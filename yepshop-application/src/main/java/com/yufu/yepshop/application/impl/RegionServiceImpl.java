package com.yufu.yepshop.application.impl;

import com.yufu.yepshop.application.RegionService;
import com.yufu.yepshop.application.assembler.RegionAssembler;
import com.yufu.yepshop.common.Result;
import com.yufu.yepshop.mdm.RegionInfo;
import com.yufu.yepshop.repository.RegionRepository;
import com.yufu.yepshop.types.dto.RegionDTO;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author wang
 * @date 2022/1/8 14:45
 */
@Service
public class RegionServiceImpl implements RegionService {
    private final RegionRepository regionRepository;
    private final RegionAssembler assembler = RegionAssembler.INSTANCE;

    public RegionServiceImpl(RegionRepository regionRepository) {
        this.regionRepository = regionRepository;
    }


    @Override
    public Result<List<RegionDTO>> getAll() {
        List<RegionInfo> list = regionRepository.findAll();
        return Result.success(assembler.toDTOList(list));
    }
}
