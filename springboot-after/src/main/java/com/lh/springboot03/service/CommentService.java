package com.lh.springboot03.service;

import com.lh.springboot03.bean.Comment;

import java.util.List;

public interface CommentService {
    /**
     * 保存一个评论
     * @param comment 评论信息
     */
    void saveComment(Comment comment);
    /**
     * 更新评论
     * @param comment 评论信息
     */
    void updateComment(Comment comment);
    /**
     * 根据id删除评论
     * @param id 评论id
     */
    void deleteCommentById(String id);
    /**
     * 查询所有评论
     * @return 所有评论
     */
    List<Comment> findCommentList();
    /**
     * 根据id查询评论
     * @param id 评论id
     * @return 查询到的评论
     */
    Comment findCommentById(String id);

//    Page<Comment> findCommentListByParentId(String parentId, int page, int size);

    List<Comment> findCommentListByGoodsIdAndState(String goodsId, String state);

    List<Comment> findCommentListByGoodsIdAndParentIdAndState(String goodsId, String parentId, String state);

    List<Long> getUnreadMessageNum(Integer uid);

    List<Comment> getReplyMessageByUid(Integer uid);

    List<Comment>  setUnreadReplyMessageToVisited(List<Comment> commentList);

    List<Comment> getOwnGoodsComment(Integer uid);

    void updateAvatarInCommentByUidAndAvatar(Integer uid,String avatar);

    /**
     * 更新在线的该用户的未读信息数
     * @param uid 用户id
     * @return 是否在线
     */
    boolean updateUnreadMessageNum(Integer uid);
}
