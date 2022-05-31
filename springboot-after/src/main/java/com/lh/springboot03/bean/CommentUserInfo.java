package com.lh.springboot03.bean;


import java.io.Serializable;

public class CommentUserInfo implements Serializable {
    private Integer id;//主键
    private String nickName;
    private String avatar;

    public CommentUserInfo() {
    }

    public CommentUserInfo(Integer id, String nickName, String avatar) {
        this.id = id;
        this.nickName = nickName;
        this.avatar = avatar;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    @Override
    public String toString() {
        return "CommentUserInfo{" +
                "id='" + id + '\'' +
                ", nickName='" + nickName + '\'' +
                ", avatar='" + avatar + '\'' +
                '}';
    }
}
