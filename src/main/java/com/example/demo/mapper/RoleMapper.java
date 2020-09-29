package com.example.demo.mapper;

import com.example.demo.pojo.Role;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface RoleMapper {
    Role findRoleByRoleId(int roId);
}
