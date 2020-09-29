package com.example.demo.controller;

import com.example.demo.pojo.Info;
import com.example.demo.service.InfoService;
import com.example.demo.service.PageBeanService;
import com.example.demo.utils.MailUtils;
import com.example.demo.vo.PageBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 评论管理
 * @date 2020-09-07
 */
@Controller
@RequestMapping("/admin/info")
public class InfoController {
    @Autowired
    InfoService infoService;
    @Autowired
    PageBeanService pageBeanService;
    @Autowired
    private JavaMailSender mailSender;

    @RequestMapping("/list")
    public String list(){

//        return "/admin/infomgn/infomgn";
        return "admin/infomgn/infomgn";//避免运行jar时报错
    }
    @RequestMapping("/date")
    @ResponseBody
    public Map date(Info info,String page, String limit){
        Map<String, Object> map = new HashMap<String, Object>();
        if (info == null){
            info = new Info();
        }
        PageBean<Info> infoPageBean = pageBeanService.infoPageQuery(info, page, limit);
        map.put("code",0);
        map.put("msg","success");
        map.put("data",infoPageBean.getList());
        map.put("count",infoPageBean.getTotalCount());
        return map;
    }
    /**
     * 管理员回复反馈内容
     * @param id    反馈用户的id
     * @param revertContent
     * @return
     */
    @RequestMapping("/revert")
    @ResponseBody
    public String revert(int id,String revertContent,String email){
        Info info = new Info();
        info.setRevertContent(revertContent);
        info.setStatus(1);  //已处理
        info.setRevertDate(new Date());
        info.setRid(id);
        if (infoService.update(info)){
            //这里应该要给用户发一个邮件通知
            SimpleMailMessage message = MailUtils.getMailMessage("1416478100@qq.com",
                    email,
                    "用户留言",
                    "@SHOPMALL回复：【" + revertContent + "】感谢您的留言~\n");
            //发送
            mailSender.send(message);
            return "success";
        }
        return "error";
    }
    @RequestMapping("/delete")
    @ResponseBody
    public String delete(int rid){
        return infoService.deleteById(rid);
    }
}
