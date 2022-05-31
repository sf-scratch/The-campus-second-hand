package com.lh.springboot03.bean;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.util.Date;

@TableName("goods_type")
public class GoodsType implements Serializable {
    @TableId(type = IdType.AUTO)
    private Integer typeId;
    private Integer categoryId;
    private String typeName;
    private Date typeCreate;
    private Date typeUpdate;

    public GoodsType() {
    }

    public GoodsType(Integer typeId, Integer categoryId, String typeName, Date typeCreate, Date typeUpdate) {
        this.typeId = typeId;
        this.categoryId = categoryId;
        this.typeName = typeName;
        this.typeCreate = typeCreate;
        this.typeUpdate = typeUpdate;
    }

    public Integer getTypeId() {
        return typeId;
    }

    public void setTypeId(Integer typeId) {
        this.typeId = typeId;
    }

    public Integer getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public Date getTypeCreate() {
        return typeCreate;
    }

    public void setTypeCreate(Date typeCreate) {
        this.typeCreate = typeCreate;
    }

    public Date getTypeUpdate() {
        return typeUpdate;
    }

    public void setTypeUpdate(Date typeUpdate) {
        this.typeUpdate = typeUpdate;
    }

    @Override
    public String toString() {
        return "GoodsType{" +
                "typeId=" + typeId +
                ", categoryId=" + categoryId +
                ", typeName='" + typeName + '\'' +
                ", typeCreate=" + typeCreate +
                ", typeUpdate=" + typeUpdate +
                '}';
    }
}
