package com.example.demo.service;

import com.example.demo.pojo.GoodsDetailImg;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface GoodsDetailImgService {
    String add(GoodsDetailImg goodsDetailImg);
    List<GoodsDetailImg> queryGoodsImgDetailListByGoodsId(int goodsId);
    List<GoodsDetailImg> queryGoodsImgDetail(int id);

}
