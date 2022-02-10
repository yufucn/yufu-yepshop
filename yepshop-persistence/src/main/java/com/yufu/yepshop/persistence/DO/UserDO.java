package com.yufu.yepshop.persistence.DO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * @author wang
 * @date 2021/12/27 23:34
 */
@Getter
@Setter
@Entity(name = "yufu_user")
@DynamicInsert
@DynamicUpdate
public class UserDO {
    @Id
    private Long id;

    @Column(nullable = false, columnDefinition = "int default 0")
    private Integer totalGoods;

    @Column(nullable = false, columnDefinition = "int default 0")
    private Integer totalUp;

    @Column(nullable = false, columnDefinition = "int default 0")
    private Integer totalSold;

    @Column(nullable = false, columnDefinition = "int default 0")
    private Integer totalBuy;

    @Column(nullable = false, columnDefinition = "int default 0")
    private Integer followers;

    @Column(nullable = false, columnDefinition = "int default 0")
    private Integer following;
    @Column(nullable = false, columnDefinition = "int default 0")
    private Integer totalView;

    @Column(nullable = false, columnDefinition = "int default 0")
    private Integer totalCollect;
}