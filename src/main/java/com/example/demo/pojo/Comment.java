package com.example.demo.pojo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/*评论实体*/
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Comment {
    private int id;     //主键
    private int userId;     //发布人id

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createDate;    //发布时间
    private String content; //内容
    private int likeCount;  // 点赞数
    private int goodsId;    //关联商品id

    /*便于前台展示*/
    private String userNickName;    //用户昵称
    private String userHeadUrl;     //用户头像
}
