package com.yufu.yepshop.mall;

import com.yufu.yepshop.application.GoodsService;
import com.yufu.yepshop.common.Result;
import com.yufu.yepshop.dto.*;
import com.yufu.yepshop.types.dto.GoodsDTO;
import com.yufu.yepshop.types.dto.GoodsListDTO;
import com.yufu.yepshop.types.query.GoodsQuery;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author wang
 * @date 2022/3/6 12:53
 */
@Controller
public class GoodsWebController {

    private final GoodsService goodsService;

    public GoodsWebController(GoodsService goodsService) {
        this.goodsService = goodsService;
    }


    @GetMapping({"/add", "/add.html"})
    public String addPage(@RequestParam Map<String, Object> params, HttpServletRequest request) {
        return "mall/add";
    }

    @GetMapping({"/search", "/search.html"})
    public String searchPage(@RequestParam Map<String, Object> params, HttpServletRequest request) {
        if (StringUtils.isEmpty(params.get("page"))) {
            params.put("page", 1);
        }
        params.put("limit", Constants.GOODS_SEARCH_PAGE_LIMIT);
        //封装分类数据
        if (params.containsKey("goodsCategoryId") && !StringUtils.isEmpty(params.get("goodsCategoryId") + "")) {
//            Long categoryId = Long.valueOf(params.get("goodsCategoryId") + "");
//            SearchPageCategoryVO searchPageCategoryVO = newBeeMallCategoryService.getCategoriesForSearch(categoryId);
//            if (searchPageCategoryVO != null) {
//                request.setAttribute("goodsCategoryId", categoryId);
//                request.setAttribute("searchPageCategoryVO", searchPageCategoryVO);
//            }
        }
        //封装参数供前端回显
        if (params.containsKey("orderBy") && !StringUtils.isEmpty(params.get("orderBy") + "")) {
            request.setAttribute("orderBy", params.get("orderBy") + "");
        }
        String keyword = "";
        //对keyword做过滤 去掉空格
        if (params.containsKey("keyword") && !StringUtils.isEmpty((params.get("keyword") + "").trim())) {
            keyword = params.get("keyword") + "";
        }
        request.setAttribute("keyword", keyword);
        params.put("keyword", keyword);
        //搜索上架状态下的商品
        params.put("goodsSellStatus", Constants.SELL_STATUS_UP);
        //封装商品数据
        PageQueryUtil pageUtil = new PageQueryUtil(params);
        List<MallSearchGoodsVO> newBeeMallSearchGoods = new ArrayList<>();
        GoodsQuery query= new GoodsQuery();
        query.setKeyword(keyword);
        Result<Page<GoodsListDTO>> goods = goodsService.search(query);
        for (GoodsListDTO g :goods.getData()) {
            MallSearchGoodsVO vo = new MallSearchGoodsVO();
            vo.setGoodsId(Long.parseLong(g.getGoods().getId()));
            vo.setGoodsName(g.getGoods().getTitle());
            vo.setGoodsCoverImg(g.getGoods().getPicUrl());
            vo.setSellingPrice(g.getPrice());
            newBeeMallSearchGoods.add(vo);
        }

        PageResult pageResult = new PageResult(newBeeMallSearchGoods, 0, pageUtil.getLimit(), pageUtil.getPage());
        request.setAttribute("pageResult", pageResult);
        return "mall/search";
    }

    @GetMapping("/goods/detail/{goodsId}")
    public String detailPage(@PathVariable("goodsId") Long goodsId, HttpServletRequest request) {
        if (goodsId < 1) {
            return "error/error_5xx";
        }
        Result<GoodsDTO> goods = goodsService.get(goodsId);
        request.setAttribute("goodsDetail", goods.getData());
        return "mall/detail";
    }
}
