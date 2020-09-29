package com.example.demo.service.impl;

import com.example.demo.mapper.CommentMapper;
import com.example.demo.mapper.GoodsMgnMapper;
import com.example.demo.pojo.Comment;
import com.example.demo.pojo.Goods;
import com.example.demo.service.GoodsMgnService;
import com.example.demo.vo.SessionUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;

@Service
public class GoodsMgnServiceImpl implements GoodsMgnService {
    @Autowired
    GoodsMgnMapper goodsMgnMapper;
    @Autowired
    CommentMapper commentMapper;
    @Override
    public String updateGoods(Goods goods, HttpServletRequest request) {
        SessionUser user = (SessionUser)request.getSession().getAttribute("user");
        if(goods.getGoodsId() == 0){//不存在，新增
            goods.setCreateTime(new Date());
            goods.setCreateUserId(user.getUserId());
            goods.setCreateUserUsername(user.getUsername()+"|"+user.getNickName());
            goods.setIsDel(0);  //不删
            goods.setGoodsSellStatus(0);        //初始化为下架状态
            goodsMgnMapper.addGoods(goods);
        }else {
            goods.setUpdateTime(new Date());
            goods.setUpdateUserId(user.getUserId());
            goods.setUpdateUserUsername(user.getUsername()+"|"+user.getNickName());
            goodsMgnMapper.updateGoods(goods);
        }
        return "success";
    }

    @Override
    public List<Goods> queryGoodsList() {
        return goodsMgnMapper.queryGoodsList();
    }

    @Override
    public String phyDeleteGoods(int goodsId) {
        if(goodsMgnMapper.phyDeleteGoods(goodsId)>0){
            return "success";
        }
        return "error";
    }

    @Override
    public String deleteGoods(int goodsId) {
        if(goodsMgnMapper.deleteGoods(goodsId)>0){
            return "success";
        }
        return "error";
    }

    @Override
    public Goods queryGoodsByGoodsId(int goodsId) {
        return goodsMgnMapper.queryGoodsByGoodsId(goodsId);
    }

    @Override
    public String updateStatus(int goodsId) {   //更新状态
        if (goodsMgnMapper.updateStatus(goodsId) > 0){
            return "success";
        }
        return "error";
    }

    @Override
    public List<Goods> queryGoodsListByGoods(Goods goods, int start, int pagesize) {
        return goodsMgnMapper.queryGoodsListByGoods(goods,start,pagesize);
    }

    /**
     * 通过商品id查找其评论及回复
     * @param goodsId
     * @return
     */
    @Override
    public List<Comment> queryGoodsCommentsByGoodsId(int goodsId) {
        List<Comment> commentList = commentMapper.queryByGoodsId(goodsId);
        return commentList;
    }

    @Override
    public List<Goods> queryGoodsListByGoodsDictPid(int goodsDictId) {
        return goodsMgnMapper.queryGoodsListByGoodsDictId(goodsDictId);
    }

    @Override
    public int getGoodsTotalCount(Goods goods) {
        return goodsMgnMapper.getGoodsTotalCount(goods);
    }
}
