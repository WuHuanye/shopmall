package com.example.demo.pojo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * 商品实体类
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Goods {
    private int goodsId;

    private String goodsName;

    private String goodsIntro;

    private int goodsDictId;//字典：：分类id

    private String goodsCoverImg;

    private String goodsCarousel;

    private Integer goodsPurPrice;//进价

    private Integer originalPrice;

    private Integer sellingPrice;

    private Integer stockNum;

    private String tag;

    private Integer goodsSellStatus;    //0上架1下架

    private Integer createUserId;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    private Integer updateUserId;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updateTime;

    private String goodsDetailContent;

    private String createUserUsername;
    private String updateUserUsername;

    private int isDel;
}
