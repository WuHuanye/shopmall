package com.example.demo.service;

import com.example.demo.pojo.Comment;
import com.example.demo.pojo.Goods;
import org.apache.ibatis.annotations.Param;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public interface GoodsMgnService {
    String updateGoods(Goods goods, HttpServletRequest request);
    List<Goods> queryGoodsList();
    String phyDeleteGoods(int goodsId);
    String deleteGoods(int goodsId);
    Goods queryGoodsByGoodsId(int goodsId);
    String updateStatus(int goodsId);

    List<Goods> queryGoodsListByGoods(@Param("goods") Goods goods,@Param("start") int start,@Param("pagesize") int pageSize);

    //商品对应的评论及回复
    List<Comment> queryGoodsCommentsByGoodsId(int goodsId);
    //说明：服务于分页查询，仅为同步，实际未从这里调用
    List<Goods> queryGoodsListByGoodsDictPid(int goodsDictPid);
    int getGoodsTotalCount(Goods goods);
}
