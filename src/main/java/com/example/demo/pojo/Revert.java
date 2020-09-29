package com.example.demo.pojo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/*用户对评论的回复实体*/
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Revert {
    private int id;
    private int cid;    //评论实体主键
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date revertDate;    //回复时间
    private String revertContent;   //回复内容
    private int likeCount;  //点赞数量
    private int userId; //回复用户id

    /*vo*/
    private String userNickName;    //用户昵称
    private String userHeadUrl;     //用户头像
}
