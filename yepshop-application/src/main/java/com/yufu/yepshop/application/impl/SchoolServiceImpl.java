package com.yufu.yepshop.application.impl;

import com.yufu.yepshop.application.SchoolService;
import com.yufu.yepshop.common.Constants;
import com.yufu.yepshop.common.Result;
import com.yufu.yepshop.persistence.DO.GoodsDO;
import com.yufu.yepshop.persistence.DO.SchoolDO;
import com.yufu.yepshop.persistence.converter.SchoolConvert;
import com.yufu.yepshop.persistence.dao.SchoolDAO;
import com.yufu.yepshop.types.command.CreateSchoolCommand;
import com.yufu.yepshop.types.dto.SchoolDTO;
import com.yufu.yepshop.types.enums.GoodsState;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;

/**
 * @author wang
 * @date 2022/1/16 21:54
 */
@Service
public class SchoolServiceImpl implements SchoolService {

    private final SchoolDAO schoolDAO;
    private SchoolConvert convert = SchoolConvert.INSTANCE;


    public SchoolServiceImpl(SchoolDAO schoolDAO) {
        this.schoolDAO = schoolDAO;
    }

    @Override
    public Result<List<SchoolDTO>> getAll() {
        Specification<SchoolDO> spc = (x, y, z) -> {
            ArrayList<Predicate> list = new ArrayList<>();
            Predicate[] predicates = new Predicate[list.size()];
            return z.and(list.toArray(predicates));
        };
        List<SchoolDO> doList = schoolDAO.findAll(spc, Sort.by(Sort.Direction.DESC, "sortId"));
        return Result.success(convert.toDTOList(doList));
    }

    @Override
    public Result<Boolean> create(CreateSchoolCommand command) {
        schoolDAO.save(convert.toDO(command));
        return Result.success();
    }
}
