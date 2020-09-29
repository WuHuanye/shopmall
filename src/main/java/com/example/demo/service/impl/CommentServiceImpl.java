package com.example.demo.service.impl;

import com.example.demo.mapper.CommentMapper;
import com.example.demo.pojo.Comment;
import com.example.demo.service.CommentService;
import com.sun.mail.imap.protocol.ID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CommentServiceImpl implements CommentService {
    @Autowired
    CommentMapper commentMapper;
    @Override
    public String add(Comment comment) {
        if (commentMapper.add(comment) > 0){
            return "success";
        }
        return "error";
    }

    @Override
    public String deleteById(int id) {
        if (commentMapper.deleteById(id) > 0){
            return "success";
        }
        return "error";
    }

    /**
     *
     * @param id
     * @param type  点赞 1  取消点赞 -1
     * @return
     */
    @Override
    public String giveLike(int id,int type) {
        if (type == 1){
            if (commentMapper.increase(id) > 0){
                System.out.println("点赞");
                return "success";
            }
            return "error";
        }else {
            if (commentMapper.decrease(id) > 0){
                System.out.println("取消点赞");
                return "success";
            }
            return "error";
        }
    }
}
