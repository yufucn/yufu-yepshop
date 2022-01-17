package com.yufu.yepshop.application;

import com.yufu.yepshop.ConvertUtils;
import com.yufu.yepshop.persistence.DO.SchoolDO;
import com.yufu.yepshop.persistence.DO.UserAccountDO;
import com.yufu.yepshop.persistence.dao.SchoolDAO;
import com.yufu.yepshop.persistence.dao.UserAccountDAO;
import com.yufu.yepshop.types.value.Buyer;
import com.yufu.yepshop.types.value.SchoolValue;
import com.yufu.yepshop.types.value.Seller;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Optional;

/**
 * @author wang
 * @date 2022/1/12 0:37
 */
public class BaseService {
    protected UserAccountDO currentUser() {
        return (UserAccountDO) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }

    protected void buildSeller(UserAccountDAO accountDAO, Seller seller) {
        if (seller != null) {
            UserAccountDO doo = findUser(accountDAO, ConvertUtils.getLongId(seller.getId()));
            if (doo != null) {
                seller.setNickName(doo.getNickName());
                seller.setAvatarUrl(doo.getAvatarUrl());
            }
        }
    }


    protected void buildBuyer(UserAccountDAO accountDAO, Buyer buyer) {
        if (buyer != null) {
            UserAccountDO doo = findUser(accountDAO, ConvertUtils.getLongId(buyer.getId()));
            if (doo != null) {
                buyer.setNickName(doo.getNickName());
                buyer.setAvatarUrl(doo.getAvatarUrl());
            }
        }
    }

    protected void builderSchool(SchoolDAO schoolDAO, SchoolValue schoolValue) {
        if (schoolValue != null) {
            Optional<SchoolDO> optional = schoolDAO.findById(ConvertUtils.getLongId(schoolValue.getId()));
            if (optional.isPresent()) {
                SchoolDO doo = optional.get();
                schoolValue.setName(doo.getName());
            }
        }
    }

    protected UserAccountDO findUser(UserAccountDAO accountDAO, Long id) {
        if (id != null) {
            Optional<UserAccountDO> sellerOptional = accountDAO.findById(id);
            return sellerOptional.orElse(null);
        }
        return null;
    }
}
