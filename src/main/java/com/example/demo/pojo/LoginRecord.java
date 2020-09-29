package com.example.demo.pojo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * 登录记录
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginRecord {
    private int id;
    private String username;
    private String nickName;
    private int sex;
    private String headUrl;
    private int role;   //用户角色
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date loginDate;
}
