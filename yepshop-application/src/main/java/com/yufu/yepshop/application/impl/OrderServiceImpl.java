package com.yufu.yepshop.application.impl;

import com.yufu.yepshop.application.BaseService;
import com.yufu.yepshop.application.OrderService;
import com.yufu.yepshop.common.Result;
import com.yufu.yepshop.domain.goods.Goods;
import com.yufu.yepshop.domain.ordering.Order;
import com.yufu.yepshop.persistence.DO.UserAccountDO;
import com.yufu.yepshop.repository.GoodsRepository;
import com.yufu.yepshop.types.command.CreateOrderCommand;
import com.yufu.yepshop.types.enums.SellerType;
import org.springframework.stereotype.Service;


/**
 * @author wang
 * @date 2022/1/12 0:23
 */
@Service
public class OrderServiceImpl extends BaseService implements OrderService {

    private final GoodsRepository goodsRepository;

    public OrderServiceImpl(GoodsRepository goodsRepository) {
        this.goodsRepository = goodsRepository;
    }

    @Override
    public Result<Boolean> create(CreateOrderCommand command) {
        Order order = new Order();
        Goods goods = goodsRepository.get(command.getGoodId());
        UserAccountDO user = currentUser();
        order.setSellerType(SellerType.A);
        order.setBuyerId(user.getId());
        return null;
    }
}
