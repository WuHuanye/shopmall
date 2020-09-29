package com.example.demo.controller;

import com.example.demo.pojo.*;
import com.example.demo.service.CarouselService;
import com.example.demo.service.DictService;
import com.example.demo.service.GoodsMgnService;
import com.example.demo.service.IndexConfigService;
import com.example.demo.utils.RedisUtils;
import com.example.demo.vo.SessionUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class MainController {
    @Autowired
    CarouselService carouselService;
    @Autowired
    IndexConfigService indexConfigService;
    @Autowired
    GoodsMgnService goodsService;
    @Autowired
    DictService dictService;
    @Autowired
    RedisUtils redisUtils;

    @Value("${SHOPMALL_LOGO}")
    private String SHOPMALL_LOGO;
    @Value("${SHOPMALL_LINK}")
    private String SHOPMALL_LINK;


    /**
     * 因为首页是有管理员进行配置的，很少改变，所以放于redis中更好
     * @param request
     * @param model
     * @return
     */
    @RequestMapping({"/","/index"})
    public String toIndex(HttpServletRequest request, Model model){
        SessionUser user = (SessionUser)request.getSession().getAttribute("user");  //判断是否登录
        if (user == null){
            user = new SessionUser();
            //加入字典
            List<Dict> shoes = dictService.listByDictType("shoes");
            List<Dict> clothes = dictService.listByDictType("clothes");
            List<Dict> calligraphys = dictService.listByDictType("calligraphy");

            Map<String,List<Dict>> dictMap = new HashMap<>();
            /*dictMap.put("shoes",shoes);
            dictMap.put("clothes",clothes);*/
            dictMap.put("鞋子",shoes);
            dictMap.put("衣服",clothes);
            dictMap.put("字diy",calligraphys);
            user.setDictMap(dictMap);
            //渲染系统logo和联系我图片
            user.setLogoUrl(SHOPMALL_LOGO);
            user.setLinkUrl(SHOPMALL_LINK);
            request.getSession().setAttribute("user",user);     //放入session中
        }
        //渲染轮播图
        Carousel carousel = new Carousel();
        carousel.setIsDeleted(2);
        int size = 3;   //轮播图数量
        List<Carousel> carouselList = carouselService.showCarouselToIndex(size, carousel);
        if (carouselList.size() == 0){  //防止为空时前台报错
            carouselList = new ArrayList<>();
        }
        model.addAttribute("carousels",carouselList);
        //渲染商品配置
        List<IndexConfig> hostIndexConfigs = indexConfigService.selectTypeShow(2, 4);   //热销
        List<IndexConfig> newIndexConfigs = indexConfigService.selectTypeShow(1, 5);    //新品
        List<IndexConfig> recommendIndexConfigs = indexConfigService.selectTypeShow(3, 10);//推荐
        List<Goods> hostGoods = new ArrayList<>();
        List<Goods> newGoods = new ArrayList<>();
        List<Goods> recommendGoods = new ArrayList<>();
        for(int i = 0;i < hostIndexConfigs.size();i++){
            hostGoods.add(goodsService.queryGoodsByGoodsId(hostIndexConfigs.get(i).getGoodsId()));
        }
        for(IndexConfig newIndexConfig:newIndexConfigs){
            newGoods.add(goodsService.queryGoodsByGoodsId(newIndexConfig.getGoodsId()));
        }
        for (IndexConfig recommendConfig:recommendIndexConfigs){
            recommendGoods.add(goodsService.queryGoodsByGoodsId(recommendConfig.getGoodsId()));
        }
        //访问次数
        if (!redisUtils.hasKey("accessCount")){
            redisUtils.set("accessCount",0);
        }
        redisUtils.incr("accessCount",1);
        model.addAttribute("user",user);
        model.addAttribute("hostGoods",hostGoods);
        model.addAttribute("newGoods",newGoods);
        model.addAttribute("recommendGoods",recommendGoods);
        model.addAttribute("accessCount",redisUtils.get("accessCount"));
//        return "/index";
        return "index";//避免运行 jar时因tymeleaf 报错

    }
    /*
    * 跳转到管理员后台
    * */
    @RequestMapping("/admin")
    private String admin(HttpServletRequest request,Model model){
        SessionUser sessionUser = (SessionUser)request.getSession().getAttribute("user");
        model.addAttribute("user",sessionUser);
//        return "/admin/index";
        return "admin/index";   //去掉“/”避免linux执行jar时因tymeleaf引发错误
    }

    /**
     * 未登录页面提醒
     * @return
     */
    @RequestMapping("/nologin")
    public String nologin(){
        return "error/nologin";
    }

}
