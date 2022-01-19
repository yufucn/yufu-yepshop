package com.yufu.yepshop.application.impl;

import com.yufu.yepshop.application.BaseService;
import com.yufu.yepshop.application.UserSchoolService;
import com.yufu.yepshop.common.Result;
import com.yufu.yepshop.persistence.DO.UserAddressDO;
import com.yufu.yepshop.persistence.DO.UserSchoolDO;
import com.yufu.yepshop.persistence.converter.UserSchoolConvert;
import com.yufu.yepshop.persistence.dao.UserSchoolDAO;
import com.yufu.yepshop.types.command.BindSchoolCommand;
import com.yufu.yepshop.types.value.SchoolValue;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;

/**
 * @author wang
 * @date 2022/1/20 0:25
 */
@Service
public class UserSchoolServiceImpl extends BaseService implements UserSchoolService {

    private final UserSchoolDAO userSchoolDAO;
    private final UserSchoolConvert convert = UserSchoolConvert.INSTANCE;

    public UserSchoolServiceImpl(UserSchoolDAO userSchoolDAO) {
        this.userSchoolDAO = userSchoolDAO;
    }

    @Override
    public Result<Boolean> bind(BindSchoolCommand command) {
        if (command.getSchools().size() > 0) {
            userSchoolDAO.deleteByCreatorId(currentUser().getId());
            for (SchoolValue schoolValue : command.getSchools()) {
                UserSchoolDO schoolDO = new UserSchoolDO();
                schoolDO.setSchoolId(Long.parseLong(schoolValue.getId()));
                schoolDO.setSchoolName(schoolValue.getName());
                userSchoolDAO.save(schoolDO);
            }
        }

        return Result.success();
    }

    @Override
    public Result<List<SchoolValue>> schools(Long id) {
        Specification<UserSchoolDO> spc = (x, y, z) -> {
            ArrayList<Predicate> list = new ArrayList<>();
            list.add(z.equal(x.get("creatorId"), id));
            Predicate[] predicates = new Predicate[list.size()];
            return z.and(list.toArray(predicates));
        };
        List<UserSchoolDO> list = userSchoolDAO.findAll(spc);

        List<SchoolValue> schoolValues = convert.toDTO(list);
        return Result.success(schoolValues);
    }
}
