package com.yufu.yepshop.application;

import com.yufu.yepshop.common.Result;
import com.yufu.yepshop.types.dto.BuyerOrderDTO;
import com.yufu.yepshop.types.dto.OrderDTO;
import com.yufu.yepshop.types.dto.SellerOrderDTO;
import org.springframework.data.domain.Page;

/**
 * @author wang
 * @date 2022/1/15 13:13
 */
public interface OrderService {

    Result<Page<BuyerOrderDTO>> pagedBuyerList(Integer page, Integer perPage, String orderSate);

    Result<Page<SellerOrderDTO>> pagedSellerList(Integer page, Integer perPage, String orderSate);

    Result<OrderDTO> get(Long id);

}
