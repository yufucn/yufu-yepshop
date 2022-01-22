package com.yufu.yepshop.application.impl;

import com.yufu.yepshop.domain.service.impl.BaseService;
import com.yufu.yepshop.application.OrderService;
import com.yufu.yepshop.common.Constants;
import com.yufu.yepshop.common.Result;
import com.yufu.yepshop.persistence.DO.OrderDO;
import com.yufu.yepshop.persistence.DO.OrderRateDO;
import com.yufu.yepshop.persistence.DO.UserAccountDO;
import com.yufu.yepshop.persistence.converter.OrderConverter;
import com.yufu.yepshop.persistence.converter.OrderRateConverter;
import com.yufu.yepshop.persistence.dao.OrderDAO;
import com.yufu.yepshop.persistence.dao.OrderRateDAO;
import com.yufu.yepshop.persistence.dao.UserAccountDAO;
import com.yufu.yepshop.types.command.OrderRateCommand;
import com.yufu.yepshop.types.command.OrderSendCommand;
import com.yufu.yepshop.types.command.UpdateOrderAddressCommand;
import com.yufu.yepshop.types.dto.BuyerOrderDTO;
import com.yufu.yepshop.types.dto.OrderDTO;
import com.yufu.yepshop.types.dto.SellerOrderDTO;
import com.yufu.yepshop.types.enums.OrderState;
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
    private final UserAccountDAO accountDAO;
    private final OrderDAO orderDAO;
    private final OrderRateDAO orderRateDAO;
    private OrderRateConverter orderRateConverter = OrderRateConverter.INSTANCE;
    private OrderConverter orderAssembler = OrderConverter.INSTANCE;

    public OrderServiceImpl(
            UserAccountDAO accountDAO,
            OrderDAO orderDAO, OrderRateDAO orderRateDAO) {
        this.accountDAO = accountDAO;
        this.orderDAO = orderDAO;
        this.orderRateDAO = orderRateDAO;
    }

    @Override
    public Result<Page<BuyerOrderDTO>> pagedBuyerList(Integer page, Integer perPage, String orderSate) {
        UserAccountDO user = currentUser();
        Specification<OrderDO> spc = (x, y, z) -> {
            ArrayList<Predicate> list = new ArrayList<>();
            list.add(z.equal(x.get("creatorId"), user.getId()));
            if (!Constants.QUERY_ALL.equalsIgnoreCase(orderSate)) {
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
            if (!Constants.QUERY_ALL.equalsIgnoreCase(orderSate)) {
                list.add(z.equal(x.get("orderState"), OrderState.valueOf(orderSate)));
            }
            Predicate[] predicates = new Predicate[list.size()];
            return z.and(list.toArray(predicates));
        };
        Pageable pageable = PageRequest.of(page, perPage, Sort.Direction.DESC, "id");
        Page<SellerOrderDTO> paged = orderDAO.findAll(spc, pageable).map(this::convertSellerDTO);
        return Result.success(paged);
    }

    @Override
    public Result<OrderDTO> get(Long id) {
        OrderDO doo = getById(id);
        if (doo != null) {
            OrderDTO dto = orderAssembler.toDTO(doo);
            Long userId = currentUser().getId();
            if (doo.getSellerId().equals(userId) || doo.getBuyerId().equals(userId)) {
                buildSeller(accountDAO, dto.getSeller());
                buildBuyer(accountDAO, dto.getBuyer());
                return Result.success(dto);
            } else {
                return Result.fail("该订单您无权查看");
            }
        }
        return Result.fail("订单不存在");
    }

    @Override
    public Result<Boolean> changeAddress(Long id, UpdateOrderAddressCommand command) {
        OrderDO doo = getById(id);
        if (doo != null) {
            if (doo.getOrderState() == OrderState.WAIT_BUYER_PAY) {
                doo.setDeliveryAddress(command.getDeliveryAddress());
                orderDAO.save(doo);
                return Result.success("地址修改成功！");
            } else {
                return Result.fail("待支付的订单才能修改地址！");
            }
        }
        return Result.fail("订单不存在，地址更新失败！");
    }

    @Override
    public Result<Boolean> send(Long id, OrderSendCommand command) {
        OrderDO doo = getById(id);
        if (doo != null) {
            doo.send(command.getLogisticsCompany(), command.getInvoiceNo());
            orderDAO.save(doo);
            return Result.success("发货成功");
        }
        return Result.fail("订单不存在");
    }

    @Override
    public Result<Boolean> rate(Long id, OrderRateCommand command) {
        OrderDO doo = getById(id);
        if (doo != null) {
            OrderRateDO orderRateDO = orderRateConverter.toDO(command);
            orderRateDO.setOrderId(id);
            orderRateDAO.save(orderRateDO);

            doo.rate(command.getRole());
            orderDAO.save(doo);
            return Result.success("评价成功");
        }
        return Result.fail("订单不存在");
    }

    @Override
    public Result<Boolean> sign(Long id) {
        OrderDO doo = getById(id);
        if (doo != null) {
            doo.sign();
            orderDAO.save(doo);
            return Result.success("签收成功");
        }
        return Result.fail("订单不存在");
    }

    private BuyerOrderDTO convertBuyerDTO(OrderDO gDo) {
        BuyerOrderDTO dto = orderAssembler.toBuyerDTO(gDo);
        buildSeller(accountDAO,dto.getSeller());
//        Optional<UserAccountDO> sellerOptional = accountDAO.findById(gDo.getSellerId());
//        if (sellerOptional.isPresent()) {
//            UserAccountDO sellerDO = sellerOptional.get();
//            Seller seller = dto.getSeller();
//            seller.setNickName(sellerDO.getNickName());
//            seller.setAvatarUrl(sellerDO.getAvatarUrl());
//        }
        return dto;
    }

    private OrderDO getById(Long id) {
        Optional<OrderDO> doOptional = orderDAO.findById(id);
        return doOptional.orElse(null);
    }

    private SellerOrderDTO convertSellerDTO(OrderDO gDo) {
        SellerOrderDTO dto = orderAssembler.toSellerDTO(gDo);
        buildBuyer(accountDAO, dto.getBuyer());
        return dto;
    }
}
