package com.lh.springboot03.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.lh.springboot03.bean.Comment;
import com.lh.springboot03.bean.CommentUserInfo;
import com.lh.springboot03.bean.Goods;
import com.lh.springboot03.repository.CommentRepository;
import com.lh.springboot03.service.CommentService;
import com.lh.springboot03.service.GoodsService;
import com.lh.springboot03.utils.WebSocketInstruct;
import com.lh.springboot03.utils.WebSocketUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;

import javax.websocket.RemoteEndpoint;
import javax.websocket.Session;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Future;




@Service
public class CommentServiceImpl implements CommentService {
    @Autowired
    private CommentRepository commentRepository;
    @Autowired
    private GoodsService goodsService;

    /**
     * 保存一个评论
     * @param comment 评论信息
     */
    public void saveComment(Comment comment){
        //如果需要自定义主键，可以在这里指定主键；如果不指定主键，MongoDB会自动生成主键
        //设置一些默认初始值。。。
        //调用dao
        commentRepository.insert(comment);
    }
    /**
     * 更新评论
     * @param comment 评论信息
     */
    public void updateComment(Comment comment){
        //调用dao
        commentRepository.save(comment);
    }
    /**
     * 根据id删除评论
     * @param id 评论id
     */
    public void deleteCommentById(String id){
        //调用dao
        commentRepository.deleteById(id);
    }
    /**
     * 查询所有评论
     * @return 所有评论
     */
    public List<Comment> findCommentList(){
        //调用dao
        return commentRepository.findAll();
    }
    /**
     * 根据id查询评论
     * @param id 评论id
     * @return 查询到的评论
     */
    public Comment findCommentById(String id){
        //调用dao
        return commentRepository.findById(id).get();
    }

    @Override
    public List<Comment> findCommentListByGoodsIdAndState(String goodsId, String state) {
        return commentRepository.findByGoodsIdAndState(goodsId,state);
    }

    @Override
    public List<Comment> findCommentListByGoodsIdAndParentIdAndState(String goodsId, String parentId, String state) {
        return commentRepository.findByGoodsIdAndParentIdAndState(goodsId,parentId,state);
    }

    @Override
    public List<Long> getUnreadMessageNum(Integer uid) {
        List<Long> result = new ArrayList<>();
        //回复给该用户的未读信息
        Comment comment = new Comment();
        CommentUserInfo commentUserInfo = new CommentUserInfo();
        commentUserInfo.setId(uid);
        comment.setTargetUser(commentUserInfo);
        comment.setVisited("0");
        ExampleMatcher matcher = ExampleMatcher.matching();
        Example<Comment> example = Example.of(comment, matcher);
        long count = commentRepository.count(example);
        result.add(count);
        count = 0;
        //该用户持有商品的未读评论
        List<Goods> goodsList = goodsService.getAllHoldGoodsByUid(uid);
        for (Goods goods : goodsList) {
            count += commentRepository.countCommentByGoodsIdAndVisitedAndParentId(goods.getId().toString(),"0", "0");
        }
        result.add(count);
        return result;
    }

    @Override
    public List<Comment> getReplyMessageByUid(Integer uid) {
        //回复给该用户的所有信息
        Comment comment = new Comment();
        CommentUserInfo commentUserInfo = new CommentUserInfo();
        commentUserInfo.setId(uid);
        comment.setTargetUser(commentUserInfo);
        ExampleMatcher matcher = ExampleMatcher.matching();
        Example<Comment> example = Example.of(comment, matcher);
        return commentRepository.findAll(example);
    }

    @Override
    public List<Comment> setUnreadReplyMessageToVisited(List<Comment> commentList) {
        List<Comment> list = new ArrayList<>();
        for (Comment comment : commentList) {
            if (comment.getVisited().equals("0")){
                comment.setVisited("1");
                list.add(comment);
            }
        }
        return commentRepository.saveAll(list);
    }

    @Override
    public List<Comment> getOwnGoodsComment(Integer uid) {
        List<Goods> goodsList = goodsService.getAllHoldGoodsByUid(uid);
        List<Comment> result = new ArrayList<>();
        for (Goods goods : goodsList) {
            result.addAll(commentRepository.findCommentByGoodsIdAndParentId(goods.getId().toString(), "0"));
        }
        return result;
    }

    @Override
    public void updateAvatarInCommentByUidAndAvatar(Integer uid, String avatar) {
        //改CommentUser
        Comment comment = new Comment();
        CommentUserInfo commentUserInfo = new CommentUserInfo();
        commentUserInfo.setId(uid);
        comment.setCommentUser(commentUserInfo);
        Example<Comment> example = Example.of(comment);
        List<Comment> commentList = commentRepository.findAll(example);
        for (Comment comment1 : commentList) {
            CommentUserInfo commentUser = comment1.getCommentUser();
            commentUser.setAvatar(avatar);
            comment1.setCommentUser(commentUser);
        }
        commentRepository.saveAll(commentList);

        //改TargetUser
        comment = new Comment();
        comment.setTargetUser(commentUserInfo);
        example = Example.of(comment);
        commentList = commentRepository.findAll(example);
        for (Comment comment1 : commentList) {
            CommentUserInfo commentUser = comment1.getTargetUser();
            commentUser.setAvatar(avatar);
            comment1.setTargetUser(commentUser);
        }
        commentRepository.saveAll(commentList);
    }

    @Override
    public boolean updateUnreadMessageNum(Integer uid) {
        Session session = WebSocketUtil.ONLINE_SESSION.get(uid);
        if (session != null){
            // getAsyncRemote()和getBasicRemote()异步与同步
            RemoteEndpoint.Async async = session.getAsyncRemote();
            //发送消息
            WebSocketInstruct<List<Long>> socketInstruct = new WebSocketInstruct<>();
            socketInstruct.setInstruct(1);
            socketInstruct.setMessage("新的未读信息");
            socketInstruct.setData(getUnreadMessageNum(uid));
            Future<Void> future = async.sendText(JSONObject.toJSONString(socketInstruct));
            boolean done = future.isDone();
            System.out.println("服务器发送消息给客户端" + session.getId() + "的消息:" + socketInstruct + "，状态为:" + done);
            return true;
        }
        return false;
    }

//    public Page<Comment> findCommentListByParentId(String parentId, int page, int size) {
//        return commentRepository.findByParentId(parentId, PageRequest.of(page-1,size));
//    }
}
