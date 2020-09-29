package com.example.demo.controller;

import com.example.demo.pojo.User;
import com.example.demo.utils.FtpUtils;
import com.example.demo.utils.SessionUtils;
import com.example.demo.vo.SessionUser;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;

//测试
@Controller
public class TestController {
    @RequestMapping("/header")
    private String header(){
//        return "/fragement/header";
        return "fragement/header";//避免运行 jar时因tymeleaf 报错
    }


    @RequestMapping("/admin1")
    private String admin(){

//        return "/admin/index";
        return "admin/index";//避免运行 jar时因tymeleaf 报错
    }

    @RequestMapping("/goods")
    private String goods(){
//        return "/mall/mall-goods";
        return "mall/mall-goods";//避免运行 jar时因tymeleaf 报错
    }


    @RequestMapping("/test")
    private String t(HttpServletRequest request, Model model){

//        return "/test/test";
        return "error/nologin";//避免运行 jar时因tymeleaf 报错

    }
    @RequestMapping("/admin/uploadImgTest")
    @ResponseBody
    public Map<String,Object> upload(MultipartFile[] file,HttpServletRequest request){
        String imgUrl = FtpUtils.uploadManyImg(file,  2, request);
//        System.out.println(imgUrl);
        Map<String,Object> map = new HashMap<>();
        map.put("code",0);
        map.put("data",imgUrl);
        map.put("msg","上传图片成功");
        return map;
    }
    @RequestMapping("/logo")
    private String logo(HttpServletRequest request, Model model){
        return "admin/indexconf/system";
    }

    /**
     * 订单详情
     * @return
     */
//    @RequestMapping("/settle")
    public String toJiesuan(){
        return "mall/mall-order-detail";
    }
//    @RequestMapping("/b")
    public String toerweima(){
        return "/mall/mall-order-list";
    }
//    @RequestMapping("/c")
    public String c(){
        return "/mall/mall-myorder-list";
    }

}
