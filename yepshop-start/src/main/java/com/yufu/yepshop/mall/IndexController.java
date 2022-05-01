package com.yufu.yepshop.mall;

import com.yufu.yepshop.application.GoodsService;
import com.yufu.yepshop.application.SliderService;
import com.yufu.yepshop.common.Result;
import com.yufu.yepshop.types.dto.GoodsListDTO;
import com.yufu.yepshop.types.dto.SliderDTO;
import com.yufu.yepshop.types.enums.Platform;
import com.yufu.yepshop.types.query.GoodsQuery;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.Collections;
import java.util.List;

/**
 * @author wang
 * @date 2022/3/2 23:57
 */
@Controller
public class IndexController {

    private final SliderService sliderService;
    private final GoodsService goodsService;

    public IndexController(
            SliderService sliderService,
            GoodsService goodsService) {
        this.sliderService = sliderService;
        this.goodsService = goodsService;
    }

    @GetMapping({"/index", "/", "/index.html"})
    public String indexPage(HttpServletRequest request) {
        Result<List<SliderDTO>> sliders = sliderService.list(Platform.PC, "index", "top");
        GoodsQuery query = new GoodsQuery();
        query.setPage(0);
        query.setPerPage(10);
        query.setCategoryIds(Collections.singletonList("929736513221169152"));
        query.setSort("ALL");
        Result<Page<GoodsListDTO>> goods = goodsService.search(query);

        GoodsQuery query2 = new GoodsQuery();
        query2.setPage(0);
        query2.setPerPage(10);
        query2.setCategoryIds(Collections.singletonList("929736560784576512"));
        query2.setSort("ALL");
        Result<Page<GoodsListDTO>> recommendGoodses = goodsService.search(query2);
        request.setAttribute("carousels", sliders.getData());//轮播图
        request.setAttribute("newGoodses", goods.getData());//新品
        request.setAttribute("recommendGoodses", recommendGoodses.getData());//推荐商品
        return "mall/index";
    }
}
