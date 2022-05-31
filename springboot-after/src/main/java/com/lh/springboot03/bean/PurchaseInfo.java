package com.lh.springboot03.bean;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;

import java.io.Serializable;
import java.util.Date;

public class PurchaseInfo implements Serializable {
    @TableId(type = IdType.AUTO)
    private Integer id;
    private String title;
    private String content;
    private String htmlContent;
    private Integer userId;
    private String userName;
    private Integer status;
    private Date createDate;

    public PurchaseInfo() {
    }

    public PurchaseInfo(Integer id, String title, String content, String htmlContent, Integer userId, String userName, Integer status, Date createDate) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.htmlContent = htmlContent;
        this.userId = userId;
        this.userName = userName;
        this.status = status;
        this.createDate = createDate;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getHtmlContent() {
        return htmlContent;
    }

    public void setHtmlContent(String htmlContent) {
        this.htmlContent = htmlContent;
    }

    @Override
    public String toString() {
        return "PurchaseInfo{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", htmlContent='" + htmlContent + '\'' +
                ", userId=" + userId +
                ", userName='" + userName + '\'' +
                ", status=" + status +
                ", createDate=" + createDate +
                '}';
    }
}
