package com.example.demo.controller;

import com.example.demo.pojo.Carousel;
import com.example.demo.pojo.Dict;
import com.example.demo.pojo.IndexConfig;
import com.example.demo.service.CarouselService;
import com.example.demo.service.DictService;
import com.example.demo.service.IndexConfigService;
import com.example.demo.service.PageBeanService;
import com.example.demo.vo.PageBean;
import com.example.demo.vo.SessionUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Controller
@RequestMapping("/admin/indexconf")
public class IndexConfController {
    @Autowired
    CarouselService carouselService;
    @Autowired
    PageBeanService pageBeanService;

    @Autowired
    DictService dictService;
    @Autowired
    IndexConfigService indexConfigService;
    @RequestMapping("/list")
    public String list(){
        return "admin/indexconf/carousel";
    }

    @RequestMapping("/carousel/data")
    @ResponseBody
    public Map<String,Object> carouselData(Carousel carousel){
        Map<String,Object> map = new HashMap<>();
        if (carousel == null){
            carousel = new Carousel();
        }
        carousel.setIsDeleted(2);
        List<Carousel> carouselList = carouselService.queryCarouselList(carousel);
        map.put("code",0);
        map.put("msg","success");
        map.put("data",carouselList);
        return map;
    }
    @RequestMapping("/carousel/save")
    @ResponseBody
    public String save(Carousel carousel, HttpServletRequest request){
        SessionUser user = (SessionUser) request.getSession().getAttribute("user");
        String result = "error";
        if(carousel == null){
            return result;
        }

        if(carousel.getCarouselId()==0){
            carousel.setCreateUser(user.getUsername()+"|"+user.getNickName());
            carousel.setIsDeleted(2);       //新增
            result = carouselService.addCarousel(carousel,request);
        }else {
            //更新编辑
            carousel.setUpdateUser(user.getUsername()+"|"+user.getNickName());
            result = carouselService.editCarousel(carousel);
        }
        return result;
    }
    //打开form表单，添加动作或者编辑动作
    @RequestMapping("/carousel/form")
    public String form(Model model,int carouselId){
        Carousel carousel = null;
        if(carouselId == 0){
            carousel = new Carousel();
        }else {
            carousel = carouselService.queryCarouselOne(carouselId);
        }
        model.addAttribute("carousel",carousel);
        return "admin/indexconf/carouselForm";//避免运行jar时报错
    }
    /**
     * 打开编辑页面
     * @param carouselId
     * @return
     */
    @RequestMapping("/carousel/edit")
    @ResponseBody
    public Carousel editCarousel(int carouselId){
        Carousel carousel = carouselService.queryCarouselOne(carouselId);
        System.out.println(carousel);
        return carousel;
    }

    @RequestMapping("/carousel/del")
    @ResponseBody
    public String delCarousel(int carouselId){
        String result = carouselService.delCarousel(carouselId);
        System.out.println(result);
        return result;
    }

    /*--————————————————————商品首页配置：：热销 新品 推荐—————————————————————————*/
    @RequestMapping("/indexgoodsConfig")
    public String toGoodsIndex(Model model){
        List<Dict> dictList = dictService.listByDictType("indexconf");
        model.addAttribute("dictList",dictList);
//        return "/admin/indexconf/indexgoodsConfig";
        return "admin/indexconf/indexgoodsConfig"; //避免运行jar时报错
    }

    @RequestMapping("/indexgoodsConfig/form")
    public String toForm(int configId,Model model){
        IndexConfig indexConfig = null;
        if (configId == 0){
            indexConfig = new IndexConfig();
        }else {
            indexConfig = indexConfigService.queryIndexConfig(configId);
        }
        List<Dict> dicts = dictService.listByDictType("indexconf");
        model.addAttribute("indexConfig",indexConfig);
        model.addAttribute("dicts",dicts);
        return "admin/indexconf/indexgoodsConfigForm";//避免运行jar时报错
    }
    @RequestMapping("/indexgoodsConfig/save")
    @ResponseBody
    public String save(IndexConfig indexConfig,HttpServletRequest request){
        SessionUser user = (SessionUser) request.getSession().getAttribute("user");
        String result = "error";
        if (indexConfig.getConfigId() == 0){
            //新增
            indexConfig.setIsDeleted(0);    //未删
            indexConfig.setCreateUser(user.getUsername()+"|"+user.getNickName());
            result = indexConfigService.addIndexConfig(indexConfig);
        }else {//更新
            indexConfig.setUpdateUser(user.getUsername()+"|"+user.getNickName());
            result = indexConfigService.updateIndexConfig(indexConfig);
        }
        return result;
    }
    /**
     *
     * @param indexConfig
     * @param limit 每页多少条数据
     * @param page  当前页码
     * @return
     */
    @RequestMapping("/indexgoodsConfig/data")
    @ResponseBody
    public Map<String,Object> data(IndexConfig indexConfig,String limit,String page){
        if(indexConfig == null){
            indexConfig = new IndexConfig();
        }
        if (indexConfig.getConfigType() == 0){
            indexConfig.setConfigType(1);   //设置初始类型为1 新品
        }
        indexConfig.setIsDeleted(0);    //未删除的
        Map<String,Object> map = new HashMap<>();
        PageBean<IndexConfig> indexConfigPageBean = pageBeanService.indexConfigPageQuery(indexConfig, page, limit);
        map.put("code",0);
        map.put("msg","success");
        map.put("data",indexConfigPageBean.getList());
        map.put("count",indexConfigPageBean.getTotalCount());
        return map;
    }

    @RequestMapping("/indexgoodsConfig/del")
    @ResponseBody
    public String delIndexConfig(int configId){
        return indexConfigService.delIndexConfig(configId);
    }

    @RequestMapping("/sysPic")
    public String sysPic(){
        return "admin/indexconf/system";
    }

}
