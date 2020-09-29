package com.example.demo.service.impl;

import com.example.demo.mapper.RevertMapper;
import com.example.demo.pojo.Revert;
import com.example.demo.service.RevertService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RevertServiceImpl implements RevertService {
    @Autowired
    RevertMapper revertMapper;
    @Override
    public List<Revert> queryRevertListByCommentId(int id) {
        return revertMapper.queryRevertByCid(id);
    }

    @Override
    public String deleteRevertsByCommentId(int commentId) {
        revertMapper.deleteRevertsByCommentId(commentId);
        return "success";
    }

    @Override
    public String deleteOneById(int id) {
        if (revertMapper.deleteById(id) > 0){
            return "success";
        }
        return "error";
    }

    @Override
    public String addRevert(Revert revert) {
        if (revertMapper.addRevert(revert) > 0){
            return "success";
        }
        return "error";
    }

    //评论下的回复数量
    @Override
    public int revertCountInCid(int cid) {
        return revertMapper.revertCountInCid(cid);
    }

    //更新点赞数量    1点赞 -1取消点赞
    @Override
    public String giveLike(int id, int type) {
        if (type == 1){
            if (revertMapper.increase(id) > 0){
//                System.out.println("点赞");
                return "success";
            }
            return "error";
        }else {
            if (revertMapper.decrease(id) > 0){
//                System.out.println("取消点赞");
                return "success";
            }
            return "error";
        }
    }
}
