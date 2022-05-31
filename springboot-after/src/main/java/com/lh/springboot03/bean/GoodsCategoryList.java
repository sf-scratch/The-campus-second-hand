package com.lh.springboot03.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;

public class GoodsCategoryList implements Serializable {
    private Integer categoryId;
    private String categoryName;
    private Date categoryCreate;
    private Date categoryUpdate;
    private ArrayList<GoodsType> types;

    public GoodsCategoryList() {
    }

    public GoodsCategoryList(Integer categoryId, String categoryName, Date categoryCreate, Date categoryUpdate, ArrayList<GoodsType> types) {
        this.categoryId = categoryId;
        this.categoryName = categoryName;
        this.categoryCreate = categoryCreate;
        this.categoryUpdate = categoryUpdate;
        this.types = types;
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

    public ArrayList<GoodsType> getTypes() {
        return types;
    }

    public void setTypes(ArrayList<GoodsType> types) {
        this.types = types;
    }
}
