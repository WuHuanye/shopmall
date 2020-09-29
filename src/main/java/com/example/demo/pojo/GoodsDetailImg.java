package com.example.demo.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/*
* 商品详情图片
* */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class GoodsDetailImg {
    private int id;
    private int goodsId;
    private String imgUrl;
}
