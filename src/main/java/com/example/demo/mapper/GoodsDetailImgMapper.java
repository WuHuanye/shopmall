package com.example.demo.mapper;

import com.example.demo.pojo.GoodsDetailImg;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface GoodsDetailImgMapper {
    int add(@Param("goodsDetailImg")GoodsDetailImg goodsDetailImg);
    List<GoodsDetailImg> queryGoodsImgDetailListByGoodsId(int goodsId);
    List<GoodsDetailImg> queryGoodsImgDetail(int id);
}
