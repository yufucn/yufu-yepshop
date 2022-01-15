package com.yufu.yepshop.external;


import com.yufu.yepshop.types.dto.TradeDTO;

import java.util.List;

/**
 * @author wang
 * @date 2022/1/13 23:29
 */
public interface ExternalWeChatPayService {
    String pay(List<TradeDTO> trades);
}
