package com.example.demo.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/*
* 购物车
* */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CarItem {
    private int carItemId;

    private int userId;

    private int goodsId;

    private int goodsCount;

    private int isDeleted;

    private Date createTime;

    private Date updateTime;

}
