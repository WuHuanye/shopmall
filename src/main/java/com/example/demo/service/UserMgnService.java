package com.example.demo.service;

import com.example.demo.pojo.User;

import java.util.List;

public interface UserMgnService {
    List<User> findUserList();
    String delete(int userId);
    boolean resetPsw(int userId);
    User queryById(int userId);
    String save(User user);
}
