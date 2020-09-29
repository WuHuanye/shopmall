package com.example.demo.service.impl;

import com.example.demo.mapper.UserMapper;
import com.example.demo.pojo.User;
import com.example.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    UserMapper userMapper;
    @Override
    public boolean register(User user) {
        if (user.getHeadUrl() == null){ //  如果不设置头像，则根据性别设置默认头像
            if (user.getSex()==1){
                user.setHeadUrl("男士.png");
            }else {
                user.setHeadUrl("女士.png");
            }
        }
//        user.setRoleId(0);      //注册的用户为普通用户
        if (userMapper.register(user)>0){
            return true;
        }
        return false;
    }

    @Override
    public User findUserByUsername(String username) {
        return userMapper.findUserByUsername(username);
    }

    @Override
    public User findUserByNickName(String nickName) {
        return userMapper.findUserByNickName(nickName);
    }

    @Override
    public boolean login(User user) {
        if (userMapper.login(user) > 0){
            return true;
        }
        return false;
    }

    @Override
    public boolean isUserExist(User user) {
        if (userMapper.login(user) > 0){
            return true;
        }
        return false;
    }

    @Override
    public int editPsw(String userPassword, int userId) {
        return userMapper.editPsw(userPassword,userId);
    }

    @Override
    public int editUserCenter(User user) {
        return userMapper.editUserCenter(user);
    }
}
