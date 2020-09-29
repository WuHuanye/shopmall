package com.example.demo.controller;

import com.example.demo.pojo.Goods;
import com.example.demo.pojo.User;
import com.example.demo.service.PageBeanService;
import com.example.demo.service.UserMgnService;
import com.example.demo.service.UserService;
import com.example.demo.utils.MD5Utils;
import com.example.demo.utils.MailUtils;
import com.example.demo.vo.PageBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/admin/user")
public class UserMgnController {
    @Autowired
    UserMgnService userMgnService;
    @Autowired
    UserService userService;
    @Autowired
    private JavaMailSender mailSender;
    @Autowired
    PageBeanService pageBeanService;

    @Value("${HEAD_URL_INIT_FEMALE}")
    private String HEAD_URL_INIT_FEMALE;
    @Value("${HEAD_URL_INIT_MALE}")
    private String HEAD_URL_INIT_MALE;

    @RequestMapping("/list")
    public String toUserList(){

//        return "/admin/usermgn/usermgn";
        return "admin/usermgn/usermgn";//避免运行 jar时因tymeleaf 报错
    }

    @RequestMapping("/data")
    @ResponseBody
    public Map data(String page, String limit, User user){
//        System.out.println(page+"-"+limit+"--"+user);
        Map<String, Object> map = new HashMap<String, Object>();
        if (user == null){
            user = new User();
        }
        if (page == null || limit == null){ //避免空指针报错
            page = 1 + "";
            limit = 10 + "";
        }
        user.setIsDeleted(0);       //未删
        PageBean<User> pageBean = pageBeanService.userPageQuery(user, page, limit);
        map.put("code",0);
        map.put("msg","success");
        map.put("data",pageBean.getList());
        map.put("count",pageBean.getTotalCount());
        return map;
    }

    /**
     * 删除用户
     * @param userId
     * @return
     */
    @RequestMapping("/delete")
    @ResponseBody
    public String delete(int userId){
        return userMgnService.delete(userId);
    }

    /**
     * 重置密码
     * @param userId
     * @return
     */
    @RequestMapping("/resetPsw")
    @ResponseBody
    public String resetPsw(int userId){
        if (userMgnService.resetPsw(userId)){
            User user = userMgnService.queryById(userId);
            String newPsw = "123321";
            /**
             * 发送验证码到用户邮箱
             */
            SimpleMailMessage message = MailUtils.getMailMessage("1416478100@qq.com",
                    user.getEmail(),
                    "密码重置",
                    "您的密码被重置为【" + newPsw + "】，请登录后及时修改哈~\n");
            //发送
            mailSender.send(message);
            return "success";
        }
        return "error";
    }

    @RequestMapping("/form")
    public String form(Model model){

//        return "/admin/usermgn/form";
        return "admin/usermgn/form";//去掉“/”避免linux执行jar时因tymeleaf引发错误
    }
    @RequestMapping("/save")
    @ResponseBody
    public String save(User user){
        user.setUserPassword(MD5Utils.md5(user.getUserPassword()));
        if (user.getSex()==0){
            //女生
            user.setHeadUrl(HEAD_URL_INIT_FEMALE);
        }else {
            user.setHeadUrl(HEAD_URL_INIT_MALE);
        }
        user.setRoleId(0);  //设置角色为普通用户
        if (userService.findUserByUsername(user.getUsername()) != null){
            return "usernameExist";
        }
        if (userService.findUserByNickName(user.getNickName()) != null){
            return "usernameExist";
        }
        return userMgnService.save(user);
    }
}
