package com.example.demo.service.impl;

import com.example.demo.mapper.*;
import com.example.demo.pojo.*;
import com.example.demo.service.PageBeanService;
import com.example.demo.vo.PageBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PageBeanServiceImpl implements PageBeanService {

    @Autowired
    UserMgnMapper userMgnMapper;
    @Autowired
    GoodsMgnMapper goodsMgnMapper;
    @Autowired
    IndexConfigMapper indexConfigMapper;
    @Autowired
    InfoMapper infoMapper;
    @Autowired
    LoginRecordMapper loginRecordMapper;

    /**
     * 用户列表分页
     * @param user
     * @param currentPageStr
     * @param pageSizeStr
     * @return
     */
    @Override
    public PageBean<User> userPageQuery(User user, String currentPageStr, String pageSizeStr) {
        int currentPage = Integer.parseInt(currentPageStr);
        int pageSize = Integer.parseInt(pageSizeStr);
        PageBean<User> pageBean = new PageBean<>();
        int start = (currentPage - 1) * pageSize;   //起始记录
        int userTotalCount = userMgnMapper.getUserTotalCount(user);  //总记录数
        int totalPageCount = userTotalCount % pageSize == 0 ? userTotalCount / pageSize : userTotalCount / pageSize +1;  //总页数
        List<User> userList = userMgnMapper.queryUsersListByUser(user, start, pageSize);
        pageBean.setList(userList);
        pageBean.setTotalPage(totalPageCount);
        pageBean.setTotalCount(userTotalCount);
        pageBean.setCurrentPage(currentPage);
        return pageBean;
    }

    /**
     * 商品分页展示
//     * @param goodSDictId  商品分类
     * @param currentPageStr 当前页码
     * @param pageSizeStr 每页多少条记录
     * @desc:鞋-》男鞋-》男1鞋，男2鞋...goodsDictId应该是男鞋的id，即数据库tb_dict的pid,即goods_info里的
     * @return
     */
    @Override
    public PageBean<Goods> goodsPageQuery(Goods goods, String currentPageStr, String pageSizeStr) {
        int currentPage = Integer.parseInt(currentPageStr);
        int pageSize = Integer.parseInt(pageSizeStr);
        PageBean<Goods> pageBean = new PageBean<>();
        int start = (currentPage - 1) * pageSize;   //起始记录
        int goodsTotalCount = goodsMgnMapper.getGoodsTotalCount(goods);  //总记录数
        int totalPageCount = goodsTotalCount % pageSize == 0 ? goodsTotalCount / pageSize : goodsTotalCount / pageSize +1;  //总页数
        List<Goods> goodsList = goodsMgnMapper.queryGoodsListByGoods(goods, start, pageSize);
        pageBean.setList(goodsList);
        pageBean.setTotalPage(totalPageCount);
        pageBean.setTotalCount(goodsTotalCount);
        pageBean.setCurrentPage(currentPage);
        return pageBean;
    }
    /*——————————————————商品首页配置——————————————————*/
    /**
     * 商品分页展示
     //     * @param 首页分类
     * @param currentPageStr 当前页码
     * @param pageSizeStr 每页多少条记录
     * @return
     */
    @Override
    public PageBean<IndexConfig> indexConfigPageQuery(IndexConfig indexConfig, String currentPageStr, String pageSizeStr) {
        int currentPage = Integer.parseInt(currentPageStr);
        int pageSize = Integer.parseInt(pageSizeStr);
        PageBean<IndexConfig> pageBean = new PageBean<>();
        int start = (currentPage - 1) * pageSize;   //起始记录
        int indexConfigTotalCount = indexConfigMapper.getIndexConfigTotalCount(indexConfig);    //总数
        int totalPageCount = indexConfigTotalCount % pageSize == 0 ? indexConfigTotalCount / pageSize : indexConfigTotalCount / pageSize +1;  //总页数
        List<IndexConfig> indexConfigList = indexConfigMapper.queryIndexConfigList(indexConfig,start,pageSize);
//        System.out.println(indexConfigList);
        pageBean.setList(indexConfigList);
        pageBean.setTotalPage(totalPageCount);
        pageBean.setTotalCount(indexConfigTotalCount);
        return pageBean;
    }


    /*——————————————————评论分页——————————————————*/
    @Override
    public PageBean<Info> infoPageQuery(Info info, String currentPageStr, String pageSizeStr) {
        int currentPage = Integer.parseInt(currentPageStr);
        int pageSize = Integer.parseInt(pageSizeStr);
        PageBean<Info> pageBean = new PageBean<>();
        int start = (currentPage - 1) * pageSize;   //起始记录
        int infoTotalCount = infoMapper.getInfoTotalCount(info);  //总记录数
        int totalPageCount = infoTotalCount % pageSize == 0 ? infoTotalCount / pageSize : infoTotalCount / pageSize +1;  //总页数
        List<Info> infoList = infoMapper.queryInfoListByInfo(info, start, pageSize);
        pageBean.setList(infoList);
        pageBean.setTotalPage(totalPageCount);
        pageBean.setTotalCount(infoTotalCount);
        pageBean.setCurrentPage(currentPage);
        return pageBean;
    }


    /**
     * 用户登录记录
     * @param loginRecord
     * @param currentPageStr
     * @param pageSizeStr
     * @return
     */
    @Override
    public PageBean<LoginRecord> loginRecordPageQuery(LoginRecord loginRecord, String currentPageStr, String pageSizeStr) {
        int currentPage = Integer.parseInt(currentPageStr);
        int pageSize = Integer.parseInt(pageSizeStr);
        PageBean<LoginRecord> pageBean = new PageBean<>();
        int start = (currentPage - 1) * pageSize;   //起始记录
        int loginRecordTotalCount = loginRecordMapper.getRecordTotalCount(loginRecord);  //总记录数
        int totalPageCount = loginRecordTotalCount % pageSize == 0 ? loginRecordTotalCount / pageSize : loginRecordTotalCount / pageSize +1;  //总页数
        List<LoginRecord> loginRecordList = loginRecordMapper.queryLoginRecords(loginRecord, start, pageSize);
        pageBean.setList(loginRecordList);
        pageBean.setTotalPage(totalPageCount);
        pageBean.setTotalCount(loginRecordTotalCount);
        pageBean.setCurrentPage(currentPage);
        return pageBean;
    }
}
