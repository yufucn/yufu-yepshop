package com.yufu.yepshop.types.dto;

import com.yufu.yepshop.types.enums.Platform;
import lombok.Getter;
import lombok.Setter;

/**
 * @author wang
 * @date 2022/2/11 21:40
 */
@Getter
@Setter
public class SliderDTO {
    private String imageUrl;
    private String url;
    private String target;
    private String title;
    private Platform platform;
    private String pageId;
    private String positionId;
    private String description;
    private Integer sortId;
}
