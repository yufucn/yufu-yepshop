package com.yufu.yepshop.application.impl;

import com.yufu.yepshop.application.BaseService;
import com.yufu.yepshop.application.UserAddressService;
import com.yufu.yepshop.common.Constants;
import com.yufu.yepshop.common.Result;
import com.yufu.yepshop.persistence.DO.GoodsDO;
import com.yufu.yepshop.persistence.DO.OrderDO;
import com.yufu.yepshop.persistence.DO.UserAccountDO;
import com.yufu.yepshop.persistence.DO.UserAddressDO;
import com.yufu.yepshop.persistence.converter.UserAddressConvert;
import com.yufu.yepshop.persistence.dao.UserAddressDAO;
import com.yufu.yepshop.types.command.CreateUserAddressCommand;
import com.yufu.yepshop.types.command.UpdateUserAddressCommand;
import com.yufu.yepshop.types.dto.BuyerOrderDTO;
import com.yufu.yepshop.types.dto.GoodsDTO;
import com.yufu.yepshop.types.dto.UserAddressDTO;
import com.yufu.yepshop.types.enums.AuditState;
import com.yufu.yepshop.types.enums.GoodsState;
import com.yufu.yepshop.types.enums.OrderState;
import com.yufu.yepshop.types.enums.SellerType;
import com.yufu.yepshop.types.value.Seller;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.Optional;

/**
 * @author wang
 * @date 2022/1/15 22:45
 */
@Service
public class UserAddressServiceImpl  extends BaseService implements UserAddressService {

    private final UserAddressDAO dao;
    private UserAddressConvert convert = UserAddressConvert.INSTANCE;

    public UserAddressServiceImpl(UserAddressDAO dao) {
        this.dao = dao;
    }


    @Override
    public Result<Boolean> create(CreateUserAddressCommand command) {
        if(command.getIsDefault()){
            clearDefault();
        }
        UserAddressDO entity = convert.toDO(command);
        dao.save(entity);
        return Result.success();
    }

    @Override
    public Result<Boolean> update(Long id, UpdateUserAddressCommand command) {
        if (command.getIsDefault()) {
            clearDefault();
        }
        UserAddressDO entity = dao.findById(id).get();
        convert.toDO(command, entity);
        dao.save(entity);
        return Result.success();
    }

    @Override
    public Result<Boolean> setDefault(Long id) {
        clearDefault();
        UserAddressDO address = dao.findById(id).get();
        address.setIsDefault(true);
        dao.save(address);
        return Result.success();
    }

    private void clearDefault() {
        Specification<UserAddressDO> spc = (x, y, z) -> {
            ArrayList<Predicate> list = new ArrayList<>();
            list.add(z.equal(x.get("isDefault"), true));
            list.add(z.equal(x.get("creatorId"), currentUser().getId()));
            Predicate[] predicates = new Predicate[list.size()];
            return z.and(list.toArray(predicates));
        };
        Optional<UserAddressDO> addressOptional = dao.findOne(spc);
        if (addressOptional.isPresent()) {
            UserAddressDO doo = addressOptional.get();
            doo.setIsDefault(false);
            dao.save(doo);
        }
    }

    @Override
    public Result<Boolean> delete(Long id) {
        dao.deleteById(id);
        return Result.success();
    }

    @Override
    public Result<Page<UserAddressDTO>> pagedList(Integer page, Integer perPage) {
        UserAccountDO user = currentUser();
        Specification<UserAddressDO> spc = (x, y, z) -> {
            ArrayList<Predicate> list = new ArrayList<>();
            list.add(z.equal(x.get("creatorId"), user.getId()));
            Predicate[] predicates = new Predicate[list.size()];
            return z.and(list.toArray(predicates));
        };

        Pageable pageable = PageRequest.of(page, perPage, Sort.Direction.DESC, "isDefault");
        Page<UserAddressDTO> paged = dao.findAll(spc, pageable).map(this::convertDTO);
        return Result.success(paged);
    }

    @Override
    public Result<UserAddressDTO> get(Long id) {
        UserAddressDO entity = dao.findById(id).get();
        UserAddressDTO dto = convert.toDTO(entity);
        return Result.success(dto);
    }

    private UserAddressDTO convertDTO(UserAddressDO gDo) {
        return convert.toDTO(gDo);
    }
}
