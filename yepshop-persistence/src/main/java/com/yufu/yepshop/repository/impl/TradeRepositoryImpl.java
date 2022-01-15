package com.yufu.yepshop.repository.impl;

import com.yufu.yepshop.persistence.DO.TradeDO;
import com.yufu.yepshop.persistence.converter.TradeConverter;
import com.yufu.yepshop.persistence.dao.TradeDAO;
import com.yufu.yepshop.repository.TradeRepository;
import com.yufu.yepshop.types.dto.TradeDTO;
import org.springframework.stereotype.Repository;

/**
 * @author wang
 * @date 2022/1/14 22:24
 */
@Repository
public class TradeRepositoryImpl  implements TradeRepository {

    private final TradeDAO orderDAO;
    private final TradeConverter converter = TradeConverter.INSTANCE;

    public TradeRepositoryImpl(TradeDAO orderDAO) {
        this.orderDAO = orderDAO;
    }

    @Override
    public TradeDTO save(TradeDTO entity) {
        TradeDO doo = converter.toDO(entity);
        orderDAO.save(doo);
        return converter.toDTO(doo);
    }
}
