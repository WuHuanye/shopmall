package com.example.demo.service;

import com.example.demo.pojo.*;
import com.example.demo.vo.PageBean;

public interface PageBeanService {
    //用户列表分页
    PageBean<User> userPageQuery(User user, String currentPageStr, String pageSizeStr);
    //商品分页v1.1
    PageBean<Goods> goodsPageQuery(Goods goods, String currentPageStr, String pageSizeStr);
    //首页商品配置分页
    PageBean<IndexConfig> indexConfigPageQuery(IndexConfig indexConfig,String currentPageStr, String pageSizeStr);
    //评论管理列表分页
    PageBean<Info> infoPageQuery(Info info,String currentPageStr,String pageSizeStr);
    //用户登录记录
    PageBean<LoginRecord> loginRecordPageQuery(LoginRecord loginRecord, String currentPageStr, String pageSizeStr);

}
