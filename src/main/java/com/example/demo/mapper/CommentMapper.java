package com.example.demo.mapper;

import com.example.demo.pojo.Comment;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface CommentMapper {
    int add(Comment comment);
    int deleteById(int id);

    int increase(int id);   //点赞
    int decrease(int id);   //取消点赞

    List<Comment> queryByGoodsId(int goodsId);
}
