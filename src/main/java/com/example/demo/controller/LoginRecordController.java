package com.example.demo.controller;

import com.example.demo.pojo.LoginRecord;
import com.example.demo.service.PageBeanService;
import com.example.demo.vo.PageBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/admin/record")
public class LoginRecordController {
    @Autowired
    private PageBeanService pageBeanService;

    @RequestMapping("/list")
    public String list(){

//        return "/admin/record/loginRecordmgn";
        return "admin/record/loginRecordmgn";//去掉“/”避免linux执行jar时因tymeleaf引发错误
    }
    @RequestMapping("/loginRecordDate")
    @ResponseBody
    public Map data(LoginRecord loginRecord,String page, String limit){
        Map<String, Object> map = new HashMap<String, Object>();
        if (loginRecord == null){
            loginRecord = new LoginRecord();
        }
        PageBean<LoginRecord> pageBean = pageBeanService.loginRecordPageQuery(loginRecord, page, limit);
        map.put("code",0);
        map.put("msg","success");
        map.put("data",pageBean.getList());
        map.put("count",pageBean.getTotalCount());
        return map;
    }
}
