package com.example.demo.controller;

import com.example.demo.mapper.LoginRecordMapper;
import com.example.demo.pojo.Dict;
import com.example.demo.pojo.Info;
import com.example.demo.pojo.LoginRecord;
import com.example.demo.pojo.User;
import com.example.demo.service.DictService;
import com.example.demo.service.InfoService;
import com.example.demo.service.UserService;
import com.example.demo.utils.*;
import com.example.demo.vo.SessionUser;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/user")
public class UserController {
    @Autowired
    private JavaMailSender mailSender;
    @Value("${HEAD_URL_INIT_FEMALE}")
    private String HEAD_URL_INIT_FEMALE;
    @Value("${HEAD_URL_INIT_MALE}")
    private String HEAD_URL_INIT_MALE;
    @Autowired
    UserService userService;
    @Autowired
    DictService dictService;
    @Autowired
    private RedisUtils redisUtils;
    @Autowired
    private InfoService infoService;
    @Autowired
    private LoginRecordMapper recordMapper;
    @Value("${SHOPMALL_LOGO}")
    private String SHOPMALL_LOGO;
    @Value("${SHOPMALL_LINK}")
    private String SHOPMALL_LINK;

    @RequestMapping("/register")
    @ResponseBody
    public String register(User user){
        if(userService.findUserByUsername(user.getUsername())!=null){
            return "usernameExist";
        }
        if(userService.findUserByNickName(user.getNickName())!=null){
            return "nickNameExist";
        }
        String code = user.getCode();       //获得前台传入的验证码
        //redis判断验证码
        String rcode = (String)redisUtils.get(user.getUsername());     //从redis中获得验证码
        if (!rcode.equals(code)){
            return "veCodeError";
        }
        if (user.getSex()==0){
            //女生
            user.setHeadUrl(HEAD_URL_INIT_FEMALE);
        }else {
            user.setHeadUrl(HEAD_URL_INIT_MALE);
        }
        user.setCreateTime(new Date());
        user.setRoleId(0);      //设置用户角色为普通用户
        user.setUserPassword(MD5Utils.md5(user.getUserPassword()));     //密码加密
        if (userService.register(user)){
            return "success";
        }
        return "error";
    }

    @RequestMapping("/login")
    @ResponseBody
    public String login(User user, HttpServletRequest request){
//        System.out.println(user.getUserPassword());
        user.setUserPassword(MD5Utils.md5(user.getUserPassword()));
        if (userService.login(user)){
            User user1 = userService.findUserByUsername(user.getUsername());    //用户名也是唯一的，这里是不是应该弄个sessionUser?
            SessionUser sessionUser = SessionUtils.getHeaderUser(user1);
            //加入字典
            List<Dict> shoes = dictService.listByDictType("shoes");
            List<Dict> clothes = dictService.listByDictType("clothes");
            List<Dict> calligraphys = dictService.listByDictType("calligraphy");
            Map<String,List<Dict>> dictMap = new HashMap<>();
            dictMap.put("鞋子",shoes);
            dictMap.put("衣服",clothes);
            dictMap.put("字diy",calligraphys);
            sessionUser.setDictMap(dictMap);
            //渲染系统logo
            sessionUser.setLogoUrl(SHOPMALL_LOGO);
            sessionUser.setLinkUrl(SHOPMALL_LINK);
            request.getSession().setAttribute("user",sessionUser);
            //记录登录记录
            LoginRecord loginRecord = new LoginRecord();
            loginRecord.setUsername(user1.getUsername());
            loginRecord.setNickName(user1.getNickName());
            loginRecord.setHeadUrl(user1.getHeadUrl());
            loginRecord.setSex(user1.getSex());
            loginRecord.setRole(user1.getRoleId());
            recordMapper.addLoginRecord(loginRecord);
            return "success";
        }
        return "error";
    }
    @RequestMapping("/getcode")
    @ResponseBody
    public String getCode(String username,String email){
        String code = (int) ((Math.random() * 9 + 1) * 100000) + "";
        System.out.println("redis验证码：："+code);
        redisUtils.set(username,code,300);       //验证码有效期5分钟,用邮箱标注验证码的唯一性
        /**
         * 发送验证码到用户邮箱
         */
        SimpleMailMessage message = MailUtils.getMailMessage("1416478100@qq.com",
                email,
                "欢迎注册【SHOPMALL】",
                "您的验证码是" + code + ",有效期为5分钟。\n");
        //发送
        mailSender.send(message);
        return "success";
    }
    @RequestMapping("/quit")
    private String quit(HttpServletRequest request){
        request.getSession().removeAttribute("user");
        return "redirect:/";
    }
    /**
     * 修改密码
     * @param newPsw
     * @param request
     * @return
     */
    @RequestMapping("/editPsw")
    @ResponseBody
    public String editPsw(String newPsw,HttpServletRequest request){
        SessionUser sessionUser = (SessionUser)request.getSession().getAttribute("user");
        User user = userService.findUserByUsername(sessionUser.getUsername());
        user.setUserPassword(newPsw);
        if(userService.editPsw(newPsw,user.getUserId())>0){
            return "success";
        }
        return "error";
    }
    @RequestMapping("/editUserCenter")
    @ResponseBody
    public String editUserCenter(@Param("user") User user, @Param("headPicFile") MultipartFile[] headPicFile,HttpServletRequest request){
        SessionUser sessionUser = (SessionUser)request.getSession().getAttribute("user");
        String imgurl = sessionUser.getHeadUrl();
        String filename = sessionUser.getUserId()+"";
        if (headPicFile != null){
            imgurl =  FtpUtils.uploadImg2(headPicFile, 1, request,"");   //上传图片获得访问路径
            user.setHeadUrl(imgurl);
        }
        user.setUserId(sessionUser.getUserId());
        if (user.getHeadUrl() == null){             //说明未改变头像，给头像赋上原来的值
            user.setHeadUrl(sessionUser.getHeadUrl());
        }
        if(userService.editUserCenter(user) > 0){
            sessionUser.setHeadUrl(imgurl);
            sessionUser.setNickName(user.getNickName());
            sessionUser.setSex(user.getSex());
            sessionUser.setBirthday(user.getBirthday());
            sessionUser.setEmail(user.getEmail());
            request.getSession().setAttribute("user",sessionUser);  //覆盖原来的session
            return "success";
        }
        return "error";
    }
    /**
     * 用户留言，直接发到管理员邮箱，数据库暂时不做处理
     * @param content
     * @param request
     * @return
     */
    @RequestMapping("/sendInfo")
    @ResponseBody
    private String sendInfo(String content,HttpServletRequest request){
        SessionUser user = (SessionUser)request.getSession().getAttribute("user");
        SimpleMailMessage message = MailUtils.getMailMessage("1416478100@qq.com",
                "3047714854@qq.com",
                "用户留言",
                "【"+user.getNickName()+"】反馈：【" + content + "】请尽快处理哈~\n");
        //发送
        mailSender.send(message);
        Info info = new Info();
        info.setContent(content);
        info.setUserId(user.getUserId());
        info.setNickNameVo(user.getNickName());
        info.setEmailVo(user.getEmail());
        infoService.save(info);
        return "success";
    }
}
