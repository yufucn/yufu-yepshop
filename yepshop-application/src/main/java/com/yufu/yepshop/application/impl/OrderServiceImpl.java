package com.yufu.yepshop.application.impl;

import com.yufu.yepshop.application.BaseService;
import com.yufu.yepshop.application.OrderService;
import com.yufu.yepshop.common.Result;
import com.yufu.yepshop.persistence.DO.OrderDO;
import com.yufu.yepshop.persistence.DO.UserAccountDO;
import com.yufu.yepshop.persistence.converter.OrderConverter;
import com.yufu.yepshop.persistence.dao.OrderDAO;
import com.yufu.yepshop.persistence.dao.UserAccountDAO;
import com.yufu.yepshop.types.dto.BuyerOrderDTO;
import com.yufu.yepshop.types.dto.SellerOrderDTO;
import com.yufu.yepshop.types.enums.OrderState;
import com.yufu.yepshop.types.value.Buyer;
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
 * @date 2022/1/15 13:14
 */
@Service
public class OrderServiceImpl extends BaseService implements OrderService {
    private final UserAccountDAO userAccountDAO;
    private final OrderDAO orderDAO;
    private OrderConverter orderAssembler = OrderConverter.INSTANCE;

    public OrderServiceImpl(
            UserAccountDAO userAccountDAO,
            OrderDAO orderDAO) {
        this.userAccountDAO = userAccountDAO;
        this.orderDAO = orderDAO;
    }

    @Override
    public Result<Page<BuyerOrderDTO>> pagedBuyerList(Integer page, Integer perPage, String orderSate) {
        UserAccountDO user = currentUser();
        Specification<OrderDO> spc = (x, y, z) -> {
            ArrayList<Predicate> list = new ArrayList<>();
            list.add(z.equal(x.get("creatorId"), user.getId()));
            if (!"ALL".equalsIgnoreCase(orderSate)) {
                list.add(z.equal(x.get("orderState"), OrderState.valueOf(orderSate)));
            }
            Predicate[] predicates = new Predicate[list.size()];
            return z.and(list.toArray(predicates));
        };

        Pageable pageable = PageRequest.of(page, perPage, Sort.Direction.DESC, "id");
        Page<BuyerOrderDTO> paged = orderDAO.findAll(spc, pageable).map(this::convertBuyerDTO);
        return Result.success(paged);
    }

    @Override
    public Result<Page<SellerOrderDTO>> pagedSellerList(Integer page, Integer perPage, String orderSate) {
        UserAccountDO user = currentUser();
        Specification<OrderDO> spc = (x, y, z) -> {
            ArrayList<Predicate> list = new ArrayList<>();
            list.add(z.equal(x.get("sellerId"), user.getId()));
            if (!"ALL".equalsIgnoreCase(orderSate)) {
                list.add(z.equal(x.get("orderState"), OrderState.valueOf(orderSate)));
            }
            Predicate[] predicates = new Predicate[list.size()];
            return z.and(list.toArray(predicates));
        };
        Pageable pageable = PageRequest.of(page, perPage, Sort.Direction.DESC, "id");
        Page<SellerOrderDTO> paged = orderDAO.findAll(spc, pageable).map(this::convertSellerDTO);
        return Result.success(paged);
    }

    private BuyerOrderDTO convertBuyerDTO(OrderDO gDo) {
        BuyerOrderDTO dto = orderAssembler.toBuyerDTO(gDo);
        Optional<UserAccountDO> sellerOptional = userAccountDAO.findById(gDo.getSellerId());
        if (sellerOptional.isPresent()) {
            UserAccountDO sellerDO = sellerOptional.get();
            Seller seller = dto.getSeller();
            seller.setNickName(sellerDO.getNickName());
            seller.setAvatarUrl(sellerDO.getAvatarUrl());
        }
        return dto;
    }

    private SellerOrderDTO convertSellerDTO(OrderDO gDo) {
        SellerOrderDTO dto = orderAssembler.toSellerDTO(gDo);
        Optional<UserAccountDO> buyerOptional = userAccountDAO.findById(gDo.getBuyerId());
        if (buyerOptional.isPresent()) {
            UserAccountDO buyerDO = buyerOptional.get();
            Buyer buyer = dto.getBuyer();
            buyer.setNickName(buyerDO.getNickName());
            buyer.setAvatarUrl(buyerDO.getAvatarUrl());
        }
        return dto;
    }
}
