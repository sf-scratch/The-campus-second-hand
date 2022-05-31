package com.lh.springboot03.bean;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.io.Serializable;
import java.util.Date;

public class Collect implements Serializable {
    private int userId;
    private int goodsId;
    private Date collectTime;

    public Collect() {
    }

    public Collect(int userId, int goodsId, Date collectTime) {
        this.userId = userId;
        this.goodsId = goodsId;
        this.collectTime = collectTime;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(int goodsId) {
        this.goodsId = goodsId;
    }

    public Date getCollectTime() {
        return collectTime;
    }

    public void setCollectTime(Date collectTime) {
        this.collectTime = collectTime;
    }

    @Override
    public String toString() {
        return "collect{" +
                "userId=" + userId +
                ", goodsId=" + goodsId +
                ", collectTime=" + collectTime +
                '}';
    }
}
