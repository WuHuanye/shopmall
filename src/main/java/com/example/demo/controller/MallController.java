package com.example.demo.controller;

import com.example.demo.pojo.*;
import com.example.demo.service.*;
import com.example.demo.vo.CartItemVO;
import com.example.demo.vo.PageBean;
import com.example.demo.vo.SessionUser;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/mall")
public class MallController {
    @Autowired
    GoodsMgnService goodsMgnService;
    @Autowired
    GoodsDetailImgService goodsDetailImgService;
    @Autowired
    CarItemService carItemService;
    @Autowired
    PageBeanService pageBeanService;
    @Autowired
    RevertService revertService;
    /**
     * 商品详情查看
     * @param goodsId
     * @param model
     * @return
     */
    @RequestMapping("/mall-goods-detail")
    private String goodsDetail(int goodsId, Model model,HttpServletRequest request){
        SessionUser user = (SessionUser)request.getSession().getAttribute("user");
        if (user == null){  //避免用户未经首页直接进入该页面报错导致日志记录变大
            user = new SessionUser();
        }
        Goods goods = goodsMgnService.queryGoodsByGoodsId(goodsId);
        List<GoodsDetailImg> goodsDetailImgs = goodsDetailImgService.queryGoodsImgDetailListByGoodsId(goodsId);
        if (goodsDetailImgs.size() == 0){   //如果没有详情图，则把主图作为详情图
            GoodsDetailImg goodsDetailImg = new GoodsDetailImg();
            goodsDetailImg.setGoodsId(goodsId);
            goodsDetailImg.setImgUrl(goods.getGoodsCoverImg());
            goodsDetailImgs = new ArrayList<>();
            goodsDetailImgs.add(goodsDetailImg);
        }
        //加载评论
        Map<String,List<Revert>> revertMap = new HashMap<>();
        List<Comment> commentList = goodsMgnService.queryGoodsCommentsByGoodsId(goodsId);
        //map.put("评论id",评论id下对应的回复revertList)
        for (Comment comment:commentList){
            revertMap.put(comment.getId()+"",revertService.queryRevertListByCommentId(comment.getId()));
        }
//        System.out.println("revertMap::"+revertMap);
        model.addAttribute("goods",goods);
        model.addAttribute("goodsDetailImgs",goodsDetailImgs);
        model.addAttribute("user",user);    //渲染header

        model.addAttribute("commentList",commentList);  //所有评论
        model.addAttribute("revertMap",revertMap);  //所有评论对应的回复
//        return "/mall/mall-goods-detail";
        return "mall/mall-goods-detail";
    }
    /**
     * 加入购物车：：因为不支持在线购买，所以该功能相当于 收藏
     * @param goodsId
     * @param model
     * @return
     */
    @RequestMapping("/mall_goods_addToCar")
    @ResponseBody
    private String addGoodsToCar(int goodsId, int goodsCount,Model model, HttpServletRequest request){
        SessionUser user = (SessionUser)request.getSession().getAttribute("user");
        if(user == null){
            return "redirect:/"; //未登录
        }
        CarItem carItem = new CarItem();
        carItem.setGoodsId(goodsId);
        carItem.setIsDeleted(2);
        carItem.setUserId(user.getUserId());
        carItem.setGoodsCount(goodsCount);
        String result = carItemService.addCarItem(carItem);
        return result;
    }
    /**
     * 跳转到购物车
     * @param request
     * @param model
     * @return
     */
    @RequestMapping("/mall_car")
    private String toCar(HttpServletRequest request,Model model){
        SessionUser user = (SessionUser)request.getSession().getAttribute("user");
        List<CartItemVO> cartItemVOs = new ArrayList<>();   //视图
        Goods goods = new Goods();
        int allCount = 0;   //商品总数量
        int totalPrice = 0; //总价
        List<CarItem> carItems = carItemService.queryCarItemsByUserId(user.getUserId());
        for (CarItem carItem : carItems){
            CartItemVO cartItemVO = new CartItemVO();
            goods = goodsMgnService.queryGoodsByGoodsId(carItem.getGoodsId());  //这么做是不是太恶心了，会不会过度耗费数据库资源
            /*if (goods == null){
                goods = new Goods();
            }*/
            cartItemVO.setCarItemId(carItem.getCarItemId());
            cartItemVO.setGoodsCount(carItem.getGoodsCount());
            cartItemVO.setGoodsCoverImg(goods.getGoodsCoverImg());
            cartItemVO.setSellingPrice(goods.getSellingPrice());
            cartItemVO.setGoodsName(goods.getGoodsName());
            cartItemVO.setGoodsId(goods.getGoodsId());
            cartItemVOs.add(cartItemVO);

            allCount += cartItemVO.getGoodsCount();
            totalPrice += cartItemVO.getSellingPrice() * cartItemVO.getGoodsCount();
        }
        model.addAttribute("cartItemVOs",cartItemVOs);
        model.addAttribute("allCount",allCount);
        model.addAttribute("totalPrice",totalPrice);
        model.addAttribute("user",user);
//        return "/mall/mall-car";
        return "mall/mall-car";//避免运行 jar时因tymeleaf 报错
    }

    @RequestMapping("/del_carItem")
    @ResponseBody
    private String delCarItem(int carItemId){
        return carItemService.delCarItemById(carItemId);
    }

    /**
     * 搜索商品
     * @param page  默认第一页
     * @param limit 默认10条
     * @param typeId
     * @param model
     * @return
     */
    @RequestMapping("/searchGoods")
    private String searchGoods(String page, String limit, @Param("typeId") String typeId, Model model,@Param("goodsName") String goodsName,HttpServletRequest request){

        Goods goods = new Goods();
        if(page == "" || page == null){
            page = "1"; //初始化当前页
        }
        if(limit == "" || limit == null){
            limit = "10";   //初始化每页数据量
        }
        if(goodsName != null&&!("".equals(goodsName))){
            goods.setGoodsName(goodsName);
        }
        if (typeId != null&&!("".equals(typeId))){
            goods.setGoodsDictId(Integer.parseInt(typeId));
        }
        goods.setIsDel(0);
        goods.setGoodsSellStatus(1);    //1上架0下架
        PageBean<Goods> pageBean = pageBeanService.goodsPageQuery(goods, page, limit);
        model.addAttribute("pageBean",pageBean);
        SessionUser user = (SessionUser)request.getSession().getAttribute("user");
        model.addAttribute("user",user);
//        return "/mall/mall-goods";
        return "mall/mall-goods";//避免运行 jar时因tymeleaf 报错
    }
}
