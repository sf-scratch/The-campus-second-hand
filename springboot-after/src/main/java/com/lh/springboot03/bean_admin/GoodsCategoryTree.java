package com.lh.springboot03.bean_admin;


import com.baomidou.mybatisplus.annotation.TableField;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;

public class GoodsCategoryTree implements Serializable {
    private Integer id;
    private String name;
    private Date createTime;
    private Date updateTime;
    @TableField(exist = false)
    private Integer fatherId;
    private ArrayList<GoodsCategoryTree> children;

    public GoodsCategoryTree() {
    }

    public GoodsCategoryTree(Integer id, String name, Date createTime, Date updateTime, Integer fatherId, ArrayList<GoodsCategoryTree> children) {
        this.id = id;
        this.name = name;
        this.createTime = createTime;
        this.updateTime = updateTime;
        this.fatherId = fatherId;
        this.children = children;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public ArrayList<GoodsCategoryTree> getChildren() {
        return children;
    }

    public void setChildren(ArrayList<GoodsCategoryTree> children) {
        this.children = children;
    }

    public Integer getFatherId() {
        return fatherId;
    }

    public void setFatherId(Integer fatherId) {
        this.fatherId = fatherId;
    }

    @Override
    public String toString() {
        return "GoodsCategoryTree{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                ", fatherId=" + fatherId +
                ", children=" + children +
                '}';
    }
}
