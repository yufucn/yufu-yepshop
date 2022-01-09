package com.yufu.yepshop.persistence.DO;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * @author wang
 * @date 2021/12/27 23:34
 */
@Getter
@Setter
@Entity(name = "yufu_user")
public class UserDO {
    @Id
    private Long id;
    private Long schoolId;
    private Integer followers;
    private Integer following;
    private Integer totalView;
    private Integer totalCollect;
    private Integer totalRelease;
    private Integer totalSold;
    private Integer totalBuy;
}