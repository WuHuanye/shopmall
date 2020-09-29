package com.example.demo.mapper;

import com.example.demo.pojo.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface UserMgnMapper {
    List<User> findUserList();
    int delete(int userId);
    int resetPsw(int userId);
    User queryById(int userId);
    int save(User user);
    /*分页展示*/
    int getUserTotalCount(@Param("user") User user);  //获得用户数量
    //获得数据列表
    List<User> queryUsersListByUser(@Param("user") User user, @Param("start") int start, @Param("pageSize") int pageSize);
}
