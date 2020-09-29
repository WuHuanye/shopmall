package com.example.demo.mapper;

import com.example.demo.pojo.Goods;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface GoodsMgnMapper {
    int addGoods(Goods goods);
    int updateGoods(Goods goods);
    List<Goods> queryGoodsList();
    int phyDeleteGoods(int goodsId);    //物理
    int deleteGoods(int goodsId);   //逻辑删除
    int updateStatus(int goodsId);  //更新上下架

    //mybatis不能返回对象:如果定义了resultMap，就要使用resultMap对应的值
    Goods queryGoodsByGoodsId(int goodsId);
    //根据商品名模糊查询
    List<Goods> queryGoodsLinkGoodsName(String goodsName);

    //通过分类id获取商品列表
    List<Goods> queryGoodsListByGoodsDictId(int goodsDictId);
    //获取数据数
    int getGoodsTotalCount(@Param("goods") Goods goods);
    //传入对象查询
    List<Goods> queryGoodsListByGoods(@Param("goods")Goods goods, @Param("start")int start, @Param("pageSize") int pageSize);


}
