package com.example.demo.controller;

import com.example.demo.pojo.Dict;
import com.example.demo.pojo.Goods;
import com.example.demo.pojo.GoodsDetailImg;
import com.example.demo.service.DictService;
import com.example.demo.service.GoodsDetailImgService;
import com.example.demo.service.GoodsMgnService;
import com.example.demo.service.PageBeanService;
import com.example.demo.vo.PageBean;
import com.example.demo.vo.SessionUser;
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
@RequestMapping("/admin/goods")
public class GoodsMgnController {
    @Autowired
    DictService dictService;
    @Autowired
    GoodsMgnService goodsMgnService;

    @Autowired
    PageBeanService pageBeanService;
    @Autowired
    GoodsDetailImgService goodsDetailImgService;

    //跳到页面后再加载/admin/goods/list
    @RequestMapping("/list")
    public String toGoodsMgnPage(Model model){
        //获得分类
        List<Dict> dicts = dictService.listParent();
        model.addAttribute("dicts",dicts);

//        return "/admin/goodsmgn/goodsmgn";
        return "admin/goodsmgn/goodsmgn";   //避免运行jar时报错
    }

    @RequestMapping("/data")
    @ResponseBody
    public Map<String,Object> list(String page,String limit,Goods goods){
        Map<String,Object> map = new HashMap<>();
        //layui太强大，刷新后前台直接传来当前页码和当前每页大小page limit
        if(goods == null){
            //空的
            goods = new Goods();
        }
        if (page == null || limit == null){ //避免空指针报错
            page = 1 + "";
            limit = 10 + "";
        }
        goods.setIsDel(0);  //未删除
        PageBean<Goods> pageBean = pageBeanService.goodsPageQuery(goods, page, limit);
        map.put("code",0);
        map.put("msg","success");
        map.put("data",pageBean.getList());
        map.put("count",pageBean.getTotalCount());
        return map;
    }
    //打开添加页面
    @RequestMapping("/form")
    public String addGoods(Model model){
        //加载字典
        List<Dict> parentDicts = dictService.listParent();  //第一级
        List<List<Dict>> dictChildrens = new ArrayList<>(); //第二级下的集合
        for (int i = 0;i < parentDicts.size();i++){
            dictChildrens.add(dictService.listByDictType(parentDicts.get(i).getType()));
        }
        model.addAttribute("parentDicts",parentDicts);
        Dict chilDict = new Dict();
        Goods goods = new Goods();
        model.addAttribute("chilDict",chilDict);
        model.addAttribute("goods",goods);
//        return "/admin/goodsmgn/goodsForm";
        return "admin/goodsmgn/goodsForm";//去掉“/”避免linux执行jar时因tymeleaf引发错误
    }
    //打开编辑页面,将产品信息回显
    @RequestMapping("/edit")
    public String editGoods(int goodsId,Model model){
        Goods goods = goodsMgnService.queryGoodsByGoodsId(goodsId);
        List<Dict> parentDicts = dictService.listParent();  //第一级
        model.addAttribute("parentDicts",parentDicts);
        Dict chilDict = dictService.getChilDict(goods.getGoodsDictId());
        List<GoodsDetailImg> goodsDetailImgs = goodsDetailImgService.queryGoodsImgDetailListByGoodsId(goodsId);
        model.addAttribute("chilDict",chilDict);
        model.addAttribute("goods",goods);
        model.addAttribute("goodsDetailImgs",goodsDetailImgs);      //商品详情
//        return "/admin/goodsmgn/goodsForm";
        return "admin/goodsmgn/goodsForm";//去掉“/”避免linux执行jar时因tymeleaf引发错误
    }

    @RequestMapping("/save")
    @ResponseBody
    public String saveGoods(Goods goods,HttpServletRequest request){
        String result = "error";
        String imgUrls = request.getParameter("duotu");     //多图路径
        String[] split = imgUrls.split(",");
        GoodsDetailImg goodsDetailImg = new GoodsDetailImg();
        if(goods!=null){
            goodsMgnService.updateGoods(goods,request); //新增或者更新
            //往详情图片表加入数据
            goodsDetailImg.setGoodsId(goods.getGoodsId());      //因为在mapper中的语句加了相关参数，所以这里获取的是添加或者更新后产品的id
            for (int i = 0;i < split.length;i++){
                goodsDetailImg.setImgUrl(split[i]);
                result = goodsDetailImgService.add(goodsDetailImg);
            }
        }
        return result;
    }
    /*逻辑删除,把详情图和其下的评论也删*/
    @RequestMapping("/delete")
    @ResponseBody
    public String deleteGoods(int goodsId){
        String result = "error";
        Goods goods = goodsMgnService.queryGoodsByGoodsId(goodsId);
        if(goods!=null){
            result = goodsMgnService.deleteGoods(goodsId);

        }
        return result;
    }

    /**
     * 改变商品上下架状态
     * @param goodsId
     * @return
     */
    @RequestMapping("/updateStatus")
    @ResponseBody
    public String updateStatus(int goodsId){
        return goodsMgnService.updateStatus(goodsId);
    }
    /*
    * 批量删除
    * @param:goodsIds   选中的商品的id，以“，”分割
    * */
    @RequestMapping("/deleteBatch")
    @ResponseBody
    public String phyDeleteGoodsBatch(String goodsIds){
        String[] goodsIdArr = goodsIds.split(",");
        try {
            for (String goodsId:goodsIdArr) {
                goodsMgnService.deleteGoods(Integer.parseInt(goodsId));
            }
            return "success";
        }catch (Exception e) {
            e.printStackTrace();
        }
        return "error";
    }

}
