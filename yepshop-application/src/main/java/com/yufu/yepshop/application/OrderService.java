package com.yufu.yepshop.application;

import com.yufu.yepshop.common.Result;
import com.yufu.yepshop.types.command.OrderRateCommand;
import com.yufu.yepshop.types.command.OrderSendCommand;
import com.yufu.yepshop.types.command.UpdateOrderAddressCommand;
import com.yufu.yepshop.types.dto.BuyerOrderDTO;
import com.yufu.yepshop.types.dto.OrderDTO;
import com.yufu.yepshop.types.dto.OrderRateDTO;
import com.yufu.yepshop.types.dto.SellerOrderDTO;
import org.springframework.data.domain.Page;

import java.util.List;

/**
 * @author wang
 * @date 2022/1/15 13:13
 */
public interface OrderService {

    Result<Page<BuyerOrderDTO>> pagedBuyerList(Integer page, Integer perPage, String orderSate);

    Result<Page<SellerOrderDTO>> pagedSellerList(Integer page, Integer perPage, String orderSate);

    Result<OrderDTO> get(Long id);

    Result<Boolean> changeAddress(Long id, UpdateOrderAddressCommand command);

    Result<Boolean> sign(Long id);

    Result<Boolean> cancel(Long id);

    Result<Boolean> changePayment(Long id,Integer payment);

    Result<Boolean> send(Long id, OrderSendCommand command);

    Result<Boolean> rate(Long id, OrderRateCommand command);

    Result<List<OrderRateDTO>> rates(Long id);
}
