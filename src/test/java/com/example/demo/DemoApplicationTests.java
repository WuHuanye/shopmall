package com.example.demo;

import com.example.demo.mapper.*;
import com.example.demo.pojo.*;
import com.example.demo.service.CommentService;
import com.example.demo.service.DictService;
import com.example.demo.service.GoodsDetailImgService;
import com.example.demo.service.GoodsMgnService;
import com.example.demo.utils.MD5Utils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Date;
@SpringBootTest
class DemoApplicationTests {
    @Autowired
    UserMapper userMapper;
    @Autowired
    UserMgnMapper userMgnMapper;
    @Autowired
    GoodsMgnMapper goodsMgnMapper;
    @Autowired
    GoodsMgnService goodsMgnService;
    @Autowired
    DictMapper1 dictMapper;
    @Autowired
    CarouselMapper carouselMapper;
    @Autowired
    IndexConfigMapper indexConfigMapper;
    @Autowired
    GoodsDetailImgService goodsDetailImgService;
    @Autowired
    CarItemMapper carItemMapper;

    @Autowired
    DictService dictService;
    @Autowired
    CommentMapper commentMapper;
    @Autowired
    RevertMapper revertMapper;
    @Autowired
    LoginRecordMapper loginRecordMapper;

    @Test
    void goods(){
        /*Goods goods = new Goods();
        goods.setGoodsSellStatus(1);
        goods.setGoodsCoverImg("32");
        goods.setGoodsCarousel("12");
        goodsMgnMapper.addGoods(goods);*/
        GoodsDetailImg goodsDetailImg = new GoodsDetailImg();

        goodsDetailImgService.add(goodsDetailImg);
    }
    @Test
    void md5(){
        System.out.println(MD5Utils.md5("test"));
//        System.out.println(MD5Utils.md5("user"));
    }
    //清除登录记录
    @Test
    void loginrecord(){
        loginRecordMapper.cleanLoginRecordBeforeSevenDays();
    }


}
