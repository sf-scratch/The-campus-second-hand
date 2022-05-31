package com.lh.springboot03.bean;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Document(collection = "comment")
public class Comment implements Serializable {
    //主键标识，该属性的值会自动对应mongodb的主键字段"_id"，如果该属性名就叫“id”,则该注解可以省略，否则必须写
    @Id
    private String id;//主键
    //该属性对应mongodb的字段的名字，如果一致，则无需该注解
    @Field("content")
    private String content;//内容
    private CommentUserInfo commentUser;//评论用户
    private CommentUserInfo targetUser;//被评论用户
    private String targetId;//被评论的评论Id
    private String targetContent;//被评论的评论内容
    private Date createDate;//评论的日期时间
    private String state;//状态 0：不可见；1：可见；
    private String visited;//浏览  0：未浏览；1：已浏览；
    private String parentId;//上级ID 如果为0表示商品的顶级评论
    private String parentContent;//上级评论内容
    private String goodsId;//所属商品id
    private String goodsCoverImage;
    private String goodsName;
    private List<Comment> childrenList;

    public Comment() {
    }

    public Comment(String id, String content, CommentUserInfo commentUser, CommentUserInfo targetUser, String targetId, String targetContent, Date createDate, String state, String visited, String parentId, String parentContent, String goodsId, String goodsCoverImage, String goodsName, List<Comment> childrenList) {
        this.id = id;
        this.content = content;
        this.commentUser = commentUser;
        this.targetUser = targetUser;
        this.targetId = targetId;
        this.targetContent = targetContent;
        this.createDate = createDate;
        this.state = state;
        this.visited = visited;
        this.parentId = parentId;
        this.parentContent = parentContent;
        this.goodsId = goodsId;
        this.goodsCoverImage = goodsCoverImage;
        this.goodsName = goodsName;
        this.childrenList = childrenList;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public CommentUserInfo getCommentUser() {
        return commentUser;
    }

    public void setCommentUser(CommentUserInfo commentUser) {
        this.commentUser = commentUser;
    }

    public CommentUserInfo getTargetUser() {
        return targetUser;
    }

    public void setTargetUser(CommentUserInfo targetUser) {
        this.targetUser = targetUser;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public List<Comment> getChildrenList() {
        return childrenList;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public void setChildrenList(List<Comment> childrenList) {
        this.childrenList = childrenList;
    }

    public String getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(String goodsId) {
        this.goodsId = goodsId;
    }

    public String getVisited() {
        return visited;
    }

    public void setVisited(String visited) {
        this.visited = visited;
    }

    public String getParentContent() {
        return parentContent;
    }

    public void setParentContent(String parentContent) {
        this.parentContent = parentContent;
    }

    public String getTargetContent() {
        return targetContent;
    }

    public void setTargetContent(String targetContent) {
        this.targetContent = targetContent;
    }

    public String getTargetId() {
        return targetId;
    }

    public void setTargetId(String targetId) {
        this.targetId = targetId;
    }

    public String getGoodsCoverImage() {
        return goodsCoverImage;
    }

    public void setGoodsCoverImage(String goodsCoverImage) {
        this.goodsCoverImage = goodsCoverImage;
    }

    public String getGoodsName() {
        return goodsName;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }

    @Override
    public String toString() {
        return "Comment{" +
                "id='" + id + '\'' +
                ", content='" + content + '\'' +
                ", commentUser=" + commentUser +
                ", targetUser=" + targetUser +
                ", targetId='" + targetId + '\'' +
                ", targetContent='" + targetContent + '\'' +
                ", createDate=" + createDate +
                ", state='" + state + '\'' +
                ", visited='" + visited + '\'' +
                ", parentId='" + parentId + '\'' +
                ", parentContent='" + parentContent + '\'' +
                ", goodsId='" + goodsId + '\'' +
                ", goodsCoverImage='" + goodsCoverImage + '\'' +
                ", goodsName='" + goodsName + '\'' +
                ", childrenList=" + childrenList +
                '}';
    }
}
