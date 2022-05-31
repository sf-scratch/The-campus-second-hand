package com.lh.springboot03.repository;

import com.lh.springboot03.Springboot03Application;
import com.lh.springboot03.bean.Comment;
import com.lh.springboot03.bean.CommentUserInfo;
import com.lh.springboot03.bean.Comment_obsolete;
import com.lh.springboot03.service.CommentService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

//SpringBoot的Junit集成测试
@RunWith(SpringRunner.class)
//SpringBoot的测试环境初始化，参数：启动类
@SpringBootTest(classes = Springboot03Application.class)
public class CommentRepositoryTests {
    @Autowired
    private CommentService commentService;
    @Autowired
    private CommentRepository commentRepository;

    @Test
    public void test1(){
//        List<Comment_obsolete> commentList = commentService.findCommentList();
//        for (Comment_obsolete comment : commentList) {
//            System.out.println(comment);
//        }

    }

    @Test
    public void test2(){
        Comment_obsolete comment=new Comment_obsolete();
//        comment.setContent("测试添加的数据");
//        comment.setPublishTime(new Date());
//        comment.setUserid("1001");
//        comment.setUsername("迷恋雷哥");
//        comment.setState("1");
//        comment.setLikeNum(0);
//        comment.setReplyNum(0);
//        comment.setParentId("0");
//        comment.setGoodsId("100000");
        comment.setContent("测试回复6200e5f2bcb640411e96b480(2)");
        comment.setPublishTime(new Date());
        comment.setUserid("123");
        comment.setUsername("dudu");
        comment.setState("1");
        comment.setLikeNum(0);
        comment.setReplyNum(0);
        comment.setParentId("6200e5f2bcb640411e96b480");
        comment.setGoodsId("100000");
//        commentService.saveComment(comment);
    }

    @Test
    public void test3(){
//        List<Comment_obsolete> commentList = commentRepository.findByParentIdAndGoodsId("0", "100000");
//        for (Comment_obsolete comment : commentList) {
//            System.out.println(comment);
//        }
    }

    @Test
    public void test4(){
        Comment comment = new Comment();
        CommentUserInfo commentUserInfo = new CommentUserInfo();
        commentUserInfo.setId(7);
        comment.setTargetUser(commentUserInfo);
        comment.setVisited("0");

        ExampleMatcher matcher = ExampleMatcher
                .matching();

        Example<Comment> example = Example.of(comment, matcher);

        List<Comment> all = commentRepository.findAll(example);
        for (Comment comment1 : all) {
            System.out.println(comment1);
        }
//        System.out.println(count);
    }

    @Test
    public void test5(){
//        Integer integer = commentRepository.countByParentIdAndGoodsId("6200e5f2bcb640411e96b480", "100000");
//        System.out.println(integer);
    }

    @Test
    public void test6(){
        Comment comment = new Comment();
        comment.setContent("开始行动");
        comment.setContent("测试测试芜湖");
        comment.setCreateDate(new Date());
        CommentUserInfo commentUserInfo = new CommentUserInfo();
        commentUserInfo.setId(1);
        commentUserInfo.setNickName("雷哥");
        commentUserInfo.setAvatar("touxiang1");
        comment.setCommentUser(commentUserInfo);
        CommentUserInfo commentUserInfo1 = new CommentUserInfo();
        commentUserInfo1.setId(2);
        commentUserInfo1.setNickName("浩哥");
        commentUserInfo1.setAvatar("touxiang2");
        comment.setTargetUser(commentUserInfo1);
        comment.setParentId("123");
        comment.setState("0");
//        List<Comment> list = new ArrayList<>();
//        list.add(comment);
//        comment.setChildrenList(list);
        commentRepository.save(comment);
    }

    @Test
    public void test7(){
        List<Comment> list = commentRepository.findByGoodsIdAndParentIdAndState("28", "0", "1");
        for (Comment comment : list) {
            System.out.println(comment);
        }
    }

    @Test
    public void test8(){
//        Optional<Comment> optional = commentRepository.findById("62067d591f7ccf704dcb8fcd");
//        Comment comment = optional.get();
//        comment.setVisited("0");
//        Comment save = commentRepository.save(comment);
//        System.out.println(save);
        List<Comment> commentList = commentService.getReplyMessageByUid(7);
        List<Comment> list = commentService.setUnreadReplyMessageToVisited(commentList);
        for (Comment comment : list) {
            System.out.println(comment);
        }
    }
}
