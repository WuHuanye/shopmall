package com.example.demo.service.impl;

import com.example.demo.mapper.GoodsDetailImgMapper;
import com.example.demo.pojo.GoodsDetailImg;
import com.example.demo.service.GoodsDetailImgService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GoodsDetailServiceImpl implements GoodsDetailImgService {
    @Autowired
    GoodsDetailImgMapper goodsDetailImgMapper;
    @Override
    public String add(GoodsDetailImg goodsDetailImg) {
        if(goodsDetailImgMapper.add(goodsDetailImg) > 0){
            return "success";
        }
        return "error";
    }

    @Override
    public List<GoodsDetailImg> queryGoodsImgDetailListByGoodsId(int goodsId) {
        return goodsDetailImgMapper.queryGoodsImgDetailListByGoodsId(goodsId);
    }

    @Override
    public List<GoodsDetailImg> queryGoodsImgDetail(int id) {
        return goodsDetailImgMapper.queryGoodsImgDetail(id);
    }
}
