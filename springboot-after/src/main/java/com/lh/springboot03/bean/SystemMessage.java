package com.lh.springboot03.bean;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.util.Date;

@TableName("system_message")
public class SystemMessage {
    @TableId(type = IdType.AUTO)
    private Integer id;
    private String title;
    private String content;
    private String htmlContent;
    private Integer contentType;
    private Integer adminId;
    private String adminName;
    private Date createDate;

    public SystemMessage() {
    }

    public SystemMessage(Integer id, String title, String content, String htmlContent, Integer contentType, Integer adminId, String adminName, Date createDate) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.htmlContent = htmlContent;
        this.contentType = contentType;
        this.adminId = adminId;
        this.adminName = adminName;
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

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Integer getAdminId() {
        return adminId;
    }

    public void setAdminId(Integer adminId) {
        this.adminId = adminId;
    }

    public String getAdminName() {
        return adminName;
    }

    public void setAdminName(String adminName) {
        this.adminName = adminName;
    }

    public Integer getContentType() {
        return contentType;
    }

    public void setContentType(Integer contentType) {
        this.contentType = contentType;
    }

    public String getHtmlContent() {
        return htmlContent;
    }

    public void setHtmlContent(String htmlContent) {
        this.htmlContent = htmlContent;
    }

    @Override
    public String toString() {
        return "SystemMessage{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", htmlContent='" + htmlContent + '\'' +
                ", contentType=" + contentType +
                ", adminId=" + adminId +
                ", adminName='" + adminName + '\'' +
                ", createDate=" + createDate +
                '}';
    }
}
