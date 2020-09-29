/**
 * 严肃声明：
 * 开源版本请务必保留此注释头信息，若删除我方将保留所有法律责任追究！
 * 本系统已申请软件著作权，受国家版权局知识产权以及国家计算机软件著作权保护！
 * 可正常分享和学习源码，不得用于违法犯罪活动，违者必究！
 * Copyright (c) 2019-2020 十三 all rights reserved.
 * 版权所有，侵权必究！
 */
package com.example.demo.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 购物车页面购物项VO
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CartItemVO implements Serializable {

    private int carItemId;

    private int goodsId;

    private int goodsCount;

    private String goodsName;

    private String goodsCoverImg;

    private int sellingPrice;

}
