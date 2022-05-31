package com.lh.springboot03.controller;

import com.alipay.api.internal.util.WebUtils;
import com.lh.springboot03.bean.Comment;
import com.lh.springboot03.bean.CommentUserInfo;
import com.lh.springboot03.bean.User;
import com.lh.springboot03.service.CommentService;
import com.lh.springboot03.utils.JsonResult;
import com.lh.springboot03.utils.TokenUtil;
import com.lh.springboot03.utils.WebSocketUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.websocket.Session;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

//解决跨域问题
@CrossOrigin(originPatterns = "*", allowCredentials = "true", allowedHeaders = "*")
@RestController
@RequestMapping("/comment")
public class CommentController extends BaseController {
    @Autowired
    private CommentService commentService;

    @RequestMapping("/home/getGoodsComment")
    public JsonResult<List<Comment>> getGoodsComment(String goodsId) {
        JsonResult<List<Comment>> result = new JsonResult<>();
        List<Comment> list = commentService.findCommentListByGoodsIdAndState(goodsId, "1");
        result.setState(OK);
        result.setMessage("评论列表获取成功");
        result.setData(list);
        return result;
    }

    @RequestMapping("/sendComment")
    public JsonResult<Void> sendComment(String content,
                                        String goodsId,
                                        String visited,
                                        String goodsCoverImage,
                                        String goodsName,
                                        Integer ownerId,
                                        HttpServletRequest request) {
        JsonResult<Void> result = new JsonResult<>();
        User user = TokenUtil.getUserFromRequest(request);
        Comment comment = new Comment();
//        comment.setId(null);
//        comment.setChildrenList(null);
//        comment.setTargetUser(null);
        comment.setContent(content);
        comment.setGoodsId(goodsId);
        comment.setGoodsCoverImage(goodsCoverImage);
        comment.setGoodsName(goodsName);
        comment.setState("1");
        comment.setParentId("0");
        comment.setCreateDate(new Date());
        CommentUserInfo commentUserInfo = new CommentUserInfo();
        commentUserInfo.setId(user.getUid());
        commentUserInfo.setAvatar(user.getAvatar());
        commentUserInfo.setNickName(user.getUsername());
        comment.setCommentUser(commentUserInfo);
        comment.setVisited(visited);
        commentService.saveComment(comment);
        result.setState(OK);
        result.setMessage("评论成功");
        //查看在线列表，若商品持有者在线，通知收到新评论
        commentService.updateUnreadMessageNum(ownerId);
        return result;
    }

    @RequestMapping("/replyComment")
    public JsonResult<Void> replyComment(@RequestBody Comment comment, HttpServletRequest request) {
        JsonResult<Void> result = new JsonResult<>();
        User user = TokenUtil.getUserFromRequest(request);
        CommentUserInfo commentUser = TokenUtil.getCommentUserInfoFromUser(user);
        comment.setCommentUser(commentUser);
        comment.setCreateDate(new Date());
        comment.setState("1");
        commentService.saveComment(comment);
        result.setState(OK);
        result.setMessage("回复评论成功");
        //查看在线列表，若被回复者在线，通知收到回复
        commentService.updateUnreadMessageNum(comment.getTargetUser().getId());
        return result;
    }

    @RequestMapping("/getUnreadMessageNum")
    public JsonResult<List<Long>> getUnreadMessageNum(HttpServletRequest request) {
        JsonResult<List<Long>> result = new JsonResult<>();
        User user = TokenUtil.getUserFromRequest(request);
        List<Long> list = commentService.getUnreadMessageNum(user.getUid());
        result.setData(list);
        result.setState(OK);
        result.setMessage("未读评论数获取成功");
        return result;
    }

    @RequestMapping("/getReplyMessageByUid")
    public JsonResult<List<Comment>> getReplyMessageByUid(HttpServletRequest request) {
        JsonResult<List<Comment>> result = new JsonResult<>();
        User user = TokenUtil.getUserFromRequest(request);
        List<Comment> commentList = commentService.getReplyMessageByUid(user.getUid());
        commentService.setUnreadReplyMessageToVisited(commentList);
        result.setData(commentList);
        result.setState(OK);
        result.setMessage("回复我的消息获取成功");
        return result;
    }

    @RequestMapping("/getOwnGoodsComment")
    public JsonResult<List<Comment>> getOwnGoodsComment(HttpServletRequest request) {
        JsonResult<List<Comment>> result = new JsonResult<>();
        User user = TokenUtil.getUserFromRequest(request);
        List<Comment> commentList = commentService.getOwnGoodsComment(user.getUid());
        commentService.setUnreadReplyMessageToVisited(commentList);
        result.setData(commentList);
        result.setState(OK);
        result.setMessage("我的商品评论获取成功");
        return result;
    }
}
