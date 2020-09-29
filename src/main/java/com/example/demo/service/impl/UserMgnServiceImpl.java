package com.example.demo.service.impl;

import com.example.demo.mapper.UserMgnMapper;
import com.example.demo.pojo.User;
import com.example.demo.service.UserMgnService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserMgnServiceImpl implements UserMgnService {
    @Autowired
    UserMgnMapper userMgnMapper;
    @Override
    public List<User> findUserList() {
        return userMgnMapper.findUserList();
    }

    /**
     * 根据id删除用户
     * @param userId
     * @return
     */
    @Override
    public String delete(int userId) {
        if(userMgnMapper.delete(userId) > 0){
            return "success";
        }
        return "error";
    }

    /**
     * 密码重置
     * @param userId
     * @return
     */
    @Override
    public boolean resetPsw(int userId) {
        if(userMgnMapper.resetPsw(userId) > 0){
            return true;
        }
        return false;
    }

    @Override
    public User queryById(int userId) {
        return userMgnMapper.queryById(userId);
    }

    @Override
    public String save(User user) {
        if(userMgnMapper.save(user) > 0){
            return "success";
        }
        return "error";
    }
}
