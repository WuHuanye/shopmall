package com.example.demo.mapper;

import com.example.demo.pojo.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
@Mapper
public interface UserMapper {
    int register(User user);
    User findUserByUsername(String username);
    User findUserByNickName(String nickName);
    int login(User user);
    int isUserExist(User user); //修改密码判断用户是否存在
    int editPsw(@Param("userPassword") String userPassword,@Param("userId") int userId);
    int editUserCenter(@Param("user") User user);

}
