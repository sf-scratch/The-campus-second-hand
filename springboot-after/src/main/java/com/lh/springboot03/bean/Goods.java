package com.lh.springboot03.bean;

import com.baomidou.mybatisplus.annotation.FieldStrategy;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.io.Serializable;
import java.util.Date;


public class Goods implements Serializable {
    @TableId(type = IdType.AUTO)
    private Integer id;
    private String coverImage;
    private String image1;
    private String image2;
    private String image3;
    private Integer typeId;
    private Integer categoryId;
    private String name;
    private Integer num;
    private Double price;
    private Double originalPrice;
    private Integer status;
    private String content;
    private Integer ownerId;
    private Date createDate;
    private String contactQq;
    private String contactWx;
    private String warnMessage;

    public Goods() {
    }

    public Double getOriginalPrice() {
        return originalPrice;
    }

    public void setOriginalPrice(Double originalPrice) {
        this.originalPrice = originalPrice;
    }

    public Goods(Integer id, String coverImage, String image1, String image2, String image3, Integer typeId, Integer categoryId, String name, Integer num, Double price, Double originalPrice, Integer status, String content, Integer ownerId, Date createDate, String contactQq, String contactWx, String warnMessage) {
        this.id = id;
        this.coverImage = coverImage;
        this.image1 = image1;
        this.image2 = image2;
        this.image3 = image3;
        this.typeId = typeId;
        this.categoryId = categoryId;
        this.name = name;
        this.num = num;
        this.price = price;
        this.originalPrice = originalPrice;
        this.status = status;
        this.content = content;
        this.ownerId = ownerId;
        this.createDate = createDate;
        this.contactQq = contactQq;
        this.contactWx = contactWx;
        this.warnMessage = warnMessage;
    }

    public String getCoverImage() {
        return coverImage;
    }

    public void setCoverImage(String coverImage) {
        this.coverImage = coverImage;
    }

    public Integer getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getImage1() {
        return image1;
    }

    public void setImage1(String image1) {
        this.image1 = image1;
    }

    public String getImage2() {
        return image2;
    }

    public void setImage2(String image2) {
        this.image2 = image2;
    }

    public String getImage3() {
        return image3;
    }

    public void setImage3(String image3) {
        this.image3 = image3;
    }

    public Integer getTypeId() {
        return typeId;
    }

    public void setTypeId(Integer typeId) {
        this.typeId = typeId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getNum() {
        return num;
    }

    public void setNum(Integer num) {
        this.num = num;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Integer getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(Integer ownerId) {
        this.ownerId = ownerId;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public String getContactQq() {
        return contactQq;
    }

    public void setContactQq(String contactQq) {
        this.contactQq = contactQq;
    }

    public String getContactWx() {
        return contactWx;
    }

    public void setContactWx(String contactWx) {
        this.contactWx = contactWx;
    }

    public String getWarnMessage() {
        return warnMessage;
    }

    public void setWarnMessage(String warnMessage) {
        this.warnMessage = warnMessage;
    }

    @Override
    public String toString() {
        return "Goods{" +
                "id=" + id +
                ", coverImage='" + coverImage + '\'' +
                ", image1='" + image1 + '\'' +
                ", image2='" + image2 + '\'' +
                ", image3='" + image3 + '\'' +
                ", typeId=" + typeId +
                ", categoryId=" + categoryId +
                ", name='" + name + '\'' +
                ", num=" + num +
                ", price=" + price +
                ", originalPrice=" + originalPrice +
                ", status=" + status +
                ", content='" + content + '\'' +
                ", ownerId=" + ownerId +
                ", createDate=" + createDate +
                ", contactQq='" + contactQq + '\'' +
                ", contactWx='" + contactWx + '\'' +
                ", warnMessage='" + warnMessage + '\'' +
                '}';
    }
}
