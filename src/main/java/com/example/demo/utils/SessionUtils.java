package com.example.demo.utils;

import com.example.demo.pojo.User;
import com.example.demo.vo.SessionUser;
import org.springframework.stereotype.Component;


@Component
public class SessionUtils {

    public static SessionUser getHeaderUser(User user){
        SessionUser sessionHeaderUser = new SessionUser();
        sessionHeaderUser.setUserId(user.getUserId());
        sessionHeaderUser.setUsername(user.getUsername());
        sessionHeaderUser.setRoleId(user.getRoleId());      //1为普通用户 2为管理员
        sessionHeaderUser.setNickName(user.getNickName());
        sessionHeaderUser.setHeadUrl(user.getHeadUrl());
        sessionHeaderUser.setAddress(user.getAddress());
        sessionHeaderUser.setBirthday(user.getBirthday());
        sessionHeaderUser.setSex(user.getSex());
        sessionHeaderUser.setEmail(user.getEmail());
        return sessionHeaderUser;
    }

}