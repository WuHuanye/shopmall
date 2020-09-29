package com.example.demo.service;

import com.example.demo.pojo.Revert;

import java.util.List;

public interface RevertService {
    List<Revert> queryRevertListByCommentId(int id);
    String deleteRevertsByCommentId(int commentId);
    String deleteOneById(int id);
    String addRevert(Revert revert);
    int revertCountInCid(int cid);

    String giveLike(int id,int type);      //更新点赞数量
}
