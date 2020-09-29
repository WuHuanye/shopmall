package com.example.demo.controller;

import com.example.demo.pojo.Comment;
import com.example.demo.pojo.Revert;
import com.example.demo.service.CommentService;
import com.example.demo.service.RevertService;
import com.example.demo.vo.SessionUser;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

/**
 * 评论
 */
@Controller
@RequestMapping("/user/comment")
public class CommentController {
    @Autowired
    CommentService commentService;
    @Autowired
    RevertService revertService;

    /**
     * 添加评论
     * @param content
     * @param goodsId
     * @param request
     * @return
     */
    @RequestMapping("/addComment")
    @ResponseBody
    public String addComment(@Param("content") String content, @Param("goodsId")String goodsId, HttpServletRequest request){
//        System.out.println(content+"---"+goodsId);
        SessionUser user = (SessionUser) request.getSession().getAttribute("user");
        Comment comment = new Comment();
        comment.setUserId(user.getUserId());
        comment.setUserNickName(user.getNickName());
        comment.setUserHeadUrl(user.getHeadUrl());
        comment.setCreateDate(new Date());
        comment.setContent(content);
        comment.setGoodsId(Integer.parseInt(goodsId));
        return commentService.add(comment);
    }

    /**
     * 点赞评论
     * @param type  点赞 1  取消点赞 -1
     * @param id    评论id 或者回复id
     * @param kind    判断点赞的是评论还是回复，如果评论和回复同在一张表就不用这样了 pl=评论     hf=回复
     * @return
     */
    @RequestMapping("/giveLike")
    @ResponseBody
    public String giveLike(@Param("type") String type,@Param("id") String id,@Param("kind") String kind){
        if (kind.equals("pl")){ //评论
            return commentService.giveLike(Integer.parseInt(id),Integer.parseInt(type));
        }
        //回复
        return revertService.giveLike(Integer.parseInt(id),Integer.parseInt(type));
    }

    /**
     * 删除评论，如果该评论下有回复，也将删除
     * @param commentId
     * @return
     */
    @RequestMapping("/delete")
    @ResponseBody
    public String deleteComment(@Param("commentId") String commentId){
        if (revertService.revertCountInCid(Integer.parseInt(commentId)) > 0){        //评论下面有回复则删除
            revertService.deleteRevertsByCommentId(Integer.parseInt(commentId));
        }
        return commentService.deleteById(Integer.parseInt(commentId));
    }

    /*-------------------------------回复-----------------------------------*/

    /**
     * 添加回复
     * @param revertContent 回复内容
     * @param cid   回复的评论id
     * @return
     */
    @RequestMapping("/addRevert")
    @ResponseBody
    public String addRevert(@Param("revertContent") String revertContent,@Param("cid") String cid,HttpServletRequest request){
//        System.out.println(revertContent+"-----"+cid);
        SessionUser user = (SessionUser) request.getSession().getAttribute("user");
        Revert revert = new Revert();
        revert.setUserId(user.getUserId());
        revert.setCid(Integer.parseInt(cid));
        revert.setRevertDate(new Date());
        revert.setRevertContent(revertContent);
        revert.setUserNickName(user.getNickName());
        revert.setUserHeadUrl(user.getHeadUrl());
        return revertService.addRevert(revert);
    }

    @RequestMapping("/deleteRevert")
    @ResponseBody
    public String deleteRevert(int revertId){
//        System.out.println("删除评论");
        return revertService.deleteOneById(revertId);
    }
}
