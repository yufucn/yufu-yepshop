package com.yufu.yepshop.application.impl;

import com.yufu.yepshop.application.SliderService;
import com.yufu.yepshop.common.Constants;
import com.yufu.yepshop.common.Result;
import com.yufu.yepshop.domain.service.impl.BaseService;
import com.yufu.yepshop.persistence.DO.GoodsDO;
import com.yufu.yepshop.persistence.DO.SliderDO;
import com.yufu.yepshop.persistence.converter.SliderConvert;
import com.yufu.yepshop.persistence.dao.SliderDAO;
import com.yufu.yepshop.types.dto.SliderDTO;
import com.yufu.yepshop.types.enums.GoodsState;
import com.yufu.yepshop.types.enums.Platform;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;

/**
 * @author wang
 * @date 2022/2/11 21:53
 */
@Service
public class SliderServiceImpl  extends BaseService implements SliderService {

    private final SliderDAO sliderDAO;
    private final SliderConvert sliderConvert = SliderConvert.INSTANCE;

    public SliderServiceImpl(SliderDAO sliderDAO) {
        this.sliderDAO = sliderDAO;
    }

    @Override
    public Result<List<SliderDTO>> list(Platform platform, String pageId, String positionId) {
        Specification<SliderDO> spc = (x, y, z) -> {
            ArrayList<Predicate> list = new ArrayList<>();
            list.add(z.equal(x.get("platform"), platform));
            list.add(z.equal(x.get("pageId"), pageId));
            list.add(z.equal(x.get("positionId"), positionId));
            Predicate[] predicates = new Predicate[list.size()];
            return z.and(list.toArray(predicates));
        };
        List<SliderDO> dos = sliderDAO.findAll(spc, Sort.by(Sort.Direction.DESC, "sortId"));
        return Result.success(sliderConvert.toDTO(dos));
    }
}
