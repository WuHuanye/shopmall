package com.example.demo.pojo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * 留言反馈:目前没有存放到数据库，只是用户留言时直接发送给管理员  原因：懒
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Info {
    private int rid;    //主键
    private int userId;
    private String content; //  反馈内容
    private String revertContent;    //回复内容
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createDate;    //反馈时间
    private int status;     //处理状态
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date revertDate;    //回复时间

    private String nickNameVo;  //用户昵称,便于展示
    private String emailVo;   // 用户email,便于管理员回复
}
