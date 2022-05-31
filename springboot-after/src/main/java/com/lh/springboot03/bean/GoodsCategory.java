package com.lh.springboot03.bean;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.util.Date;

@TableName("goods_category")
public class GoodsCategory implements Serializable {
    @TableId(type = IdType.AUTO)
    private Integer categoryId;
    private String categoryName;
    private Date categoryCreate;
    private Date categoryUpdate;

    public GoodsCategory() {
    }

    public GoodsCategory(Integer categoryId, String categoryName, Date categoryCreate, Date categoryUpdate) {
        this.categoryId = categoryId;
        this.categoryName = categoryName;
        this.categoryCreate = categoryCreate;
        this.categoryUpdate = categoryUpdate;
    }

    public Integer getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public Date getCategoryCreate() {
        return categoryCreate;
    }

    public void setCategoryCreate(Date categoryCreate) {
        this.categoryCreate = categoryCreate;
    }

    public Date getCategoryUpdate() {
        return categoryUpdate;
    }

    public void setCategoryUpdate(Date categoryUpdate) {
        this.categoryUpdate = categoryUpdate;
    }
}
