package com.yufu.yepshop.dto;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * @author wang
 * @date 2022/3/2 23:59
 */
@Getter
@Setter
public class MallIndexCarouselVO implements Serializable {

    private String carouselUrl;

    private String redirectUrl;
}
