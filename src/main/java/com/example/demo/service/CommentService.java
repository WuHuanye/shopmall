package com.example.demo.service;

import com.example.demo.pojo.Comment;

public interface CommentService {
    String add(Comment comment);
    String deleteById(int id);
    String giveLike(int id,int type);      //更新点赞数量
}
