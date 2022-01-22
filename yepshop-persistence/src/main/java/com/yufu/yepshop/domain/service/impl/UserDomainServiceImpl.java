package com.yufu.yepshop.domain.service.impl;

import com.yufu.yepshop.domain.service.UserDomainService;
import com.yufu.yepshop.persistence.DO.UserAccountDO;
import com.yufu.yepshop.persistence.DO.UserDO;
import com.yufu.yepshop.persistence.DO.UserSchoolDO;
import com.yufu.yepshop.persistence.converter.UserAccountConvert;
import com.yufu.yepshop.persistence.converter.UserConvert;
import com.yufu.yepshop.persistence.converter.UserSchoolConvert;
import com.yufu.yepshop.persistence.dao.UserAccountDAO;
import com.yufu.yepshop.persistence.dao.UserDAO;
import com.yufu.yepshop.persistence.dao.UserSchoolDAO;
import com.yufu.yepshop.types.command.BindSchoolCommand;
import com.yufu.yepshop.types.dto.UserDetailDTO;
import com.yufu.yepshop.types.value.SchoolValue;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;

/**
 * @author wang
 * @date 2022/1/23 0:18
 */
@Service
public class UserDomainServiceImpl  extends BaseService implements UserDomainService {

    private UserAccountDAO userAccountDAO;
    private UserSchoolDAO userSchoolDAO;
    private UserDAO userDAO;
    private final UserAccountConvert userAccountConvert = UserAccountConvert.INSTANCE;
    private final UserSchoolConvert convert = UserSchoolConvert.INSTANCE;
    private final UserConvert userConvert = UserConvert.INSTANCE;

    public UserDomainServiceImpl(
            UserAccountDAO userAccountDAO,
            UserSchoolDAO userSchoolDAO,
            UserDAO userDAO) {
        this.userAccountDAO = userAccountDAO;
        this.userSchoolDAO = userSchoolDAO;
        this.userDAO = userDAO;
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
}
