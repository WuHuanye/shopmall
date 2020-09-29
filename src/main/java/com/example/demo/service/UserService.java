package com.example.demo.service;

import com.example.demo.pojo.User;

import java.util.List;

public interface UserService {
    boolean register(User user);
    User findUserByUsername(String username);
    User findUserByNickName(String nickName);
    boolean login(User user);
    boolean isUserExist(User user);

    int editPsw(String userPassword,int userId);
    int editUserCenter(User user);
}
