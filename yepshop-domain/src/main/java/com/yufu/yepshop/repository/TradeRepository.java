package com.yufu.yepshop.repository;
import com.yufu.yepshop.types.dto.TradeDTO;

/**
 * @author wang
 * @date 2022/1/14 22:24
 */
public interface TradeRepository {
    TradeDTO save(TradeDTO entity);
}
