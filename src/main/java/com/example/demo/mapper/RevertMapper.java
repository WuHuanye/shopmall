package com.example.demo.mapper;

import com.example.demo.pojo.Revert;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface RevertMapper {
    int addRevert(Revert revert);
    int deleteById(int id);     //根据id删除回复
    int deleteRevertsByCommentId(int cid);  //根据评论id删除其下的所有回复
    int increase(int id);   //点赞
    int decrease(int id);   //取消点赞
    int revertCountInCid(int cid);  //评论下的回复数量

    List<Revert> queryRevertByCid(int cid);     //通过评论的id获得其下的回复
}
