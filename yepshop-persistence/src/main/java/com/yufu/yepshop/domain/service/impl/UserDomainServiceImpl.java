package com.yufu.yepshop.domain.service.impl;

import com.yufu.yepshop.domain.service.UserDomainService;
import com.yufu.yepshop.persistence.DO.SchoolDO;
import com.yufu.yepshop.persistence.DO.UserAccountDO;
import com.yufu.yepshop.persistence.DO.UserDO;
import com.yufu.yepshop.persistence.DO.UserSchoolDO;
import com.yufu.yepshop.persistence.converter.UserAccountConvert;
import com.yufu.yepshop.persistence.converter.UserConvert;
import com.yufu.yepshop.persistence.converter.UserSchoolConvert;
import com.yufu.yepshop.persistence.dao.SchoolDAO;
import com.yufu.yepshop.persistence.dao.UserAccountDAO;
import com.yufu.yepshop.persistence.dao.UserDAO;
import com.yufu.yepshop.persistence.dao.UserSchoolDAO;
import com.yufu.yepshop.types.command.BindSchoolCommand;
import com.yufu.yepshop.types.dto.UserDetailDTO;
import com.yufu.yepshop.types.value.SchoolValue;
import com.yufu.yepshop.types.value.UserValue;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;

/**
 * @author wang
 * @date 2022/1/23 0:18
 */
@Service
public class UserDomainServiceImpl extends BaseService implements UserDomainService {

    private final UserAccountDAO userAccountDAO;
    private final SchoolDAO schoolDAO;
    private final UserSchoolDAO userSchoolDAO;
    private final UserDAO userDAO;
    private final UserAccountConvert userAccountConvert = UserAccountConvert.INSTANCE;
    private final UserSchoolConvert convert = UserSchoolConvert.INSTANCE;
    private final UserConvert userConvert = UserConvert.INSTANCE;

    public UserDomainServiceImpl(
            UserAccountDAO userAccountDAO,
            SchoolDAO schoolDAO, UserSchoolDAO userSchoolDAO,
            UserDAO userDAO) {
        this.userAccountDAO = userAccountDAO;
        this.schoolDAO = schoolDAO;
        this.userSchoolDAO = userSchoolDAO;
        this.userDAO = userDAO;
    }

    @Override
    public Boolean bindSchool(Long schoolId) {
        SchoolDO schoolDO = schoolDAO.findById(schoolId).orElse(null);
        if (schoolDO != null) {
            List<SchoolValue> schools = new ArrayList<>();
            SchoolValue schoolValue = new SchoolValue();
            schoolValue.setName(schoolDO.getName());
            schoolValue.setId(schoolDO.getId().toString());
            schools.add(schoolValue);
            BindSchoolCommand command = new BindSchoolCommand();
            command.setSchools(schools);
            bindSchool(command);
        }
        return true;
    }

    @Override
    public Boolean bindSchool(BindSchoolCommand command) {
        if (command.getSchools().size() > 0) {
            userSchoolDAO.deleteByCreatorId(currentUser().getId());
            for (SchoolValue schoolValue : command.getSchools()) {
                UserSchoolDO schoolDO = new UserSchoolDO();
                schoolDO.setSchoolId(Long.parseLong(schoolValue.getId()));
                schoolDO.setSchoolName(schoolValue.getName());
                userSchoolDAO.save(schoolDO);
            }
        }
        return true;
    }

    @Override
    public UserDetailDTO userDetail(Long id) {
        UserDetailDTO dto = new UserDetailDTO();
        UserAccountDO userAccountDO = userAccountDAO.findById(id).orElse(null);
        UserDO userDO = userDAO.findById(id).orElse(null);
        dto.setAccount(userAccountConvert.toDTO(userAccountDO));
        dto.setSchools(schools(id));
        dto.setUser(userConvert.toDTO(userDO));
        return dto;
    }

    @Override
    public List<SchoolValue> schools(Long id) {
        Specification<UserSchoolDO> spc = (x, y, z) -> {
            ArrayList<Predicate> list = new ArrayList<>();
            list.add(z.equal(x.get("creatorId"), id));
            Predicate[] predicates = new Predicate[list.size()];
            return z.and(list.toArray(predicates));
        };
        List<UserSchoolDO> list = userSchoolDAO.findAll(spc);
        return convert.toDTO(list);
    }

    @Override
    public List<UserValue> users(List<Long> ids) {
        Specification<UserAccountDO> spc = (x, y, z) -> {
            ArrayList<Predicate> list = new ArrayList<>();
            if (ids.size() > 0) {
                Expression<String> exp = x.get("id");
                list.add(exp.in(ids));
            }
            Predicate[] predicates = new Predicate[list.size()];
            return z.and(list.toArray(predicates));
        };
        return userAccountConvert.toUserValue(userAccountDAO.findAll(spc));
    }

    @Override
    public UserValue user(Long id) {
        List<Long> list = new ArrayList();
        list.add(id);
        List<UserValue> users = users(list);
        if (list.size() > 0) {
            return users.get(0);
        }
        return null;
    }

    @Override
    public Boolean clearTotalView(Long id) {
        return userDAO.clearTotalView(id) > 0;
    }
}
