package com.lh.springboot03.bean_admin;

public class UserActiveInfo {
    private Integer currentMouthRegisterUser;
    private Integer currentMouthReleaseGoodsSum;
    private Integer currentDayReleaseGoodsSum;
    private Integer userSum;
    private Integer goodsSum;

    public UserActiveInfo() {
    }

    public Integer getCurrentMouthRegisterUser() {
        return currentMouthRegisterUser;
    }

    public void setCurrentMouthRegisterUser(Integer currentMouthRegisterUser) {
        this.currentMouthRegisterUser = currentMouthRegisterUser;
    }

    public Integer getCurrentMouthReleaseGoodsSum() {
        return currentMouthReleaseGoodsSum;
    }

    public void setCurrentMouthReleaseGoodsSum(Integer currentMouthReleaseGoodsSum) {
        this.currentMouthReleaseGoodsSum = currentMouthReleaseGoodsSum;
    }

    public Integer getCurrentDayReleaseGoodsSum() {
        return currentDayReleaseGoodsSum;
    }

    public void setCurrentDayReleaseGoodsSum(Integer currentDayReleaseGoodsSum) {
        this.currentDayReleaseGoodsSum = currentDayReleaseGoodsSum;
    }

    public Integer getUserSum() {
        return userSum;
    }

    public void setUserSum(Integer userSum) {
        this.userSum = userSum;
    }

    public Integer getGoodsSum() {
        return goodsSum;
    }

    public void setGoodsSum(Integer goodsSum) {
        this.goodsSum = goodsSum;
    }

    @Override
    public String toString() {
        return "UserActiveInfo{" +
                "currentMouthRegisterUser=" + currentMouthRegisterUser +
                ", currentMouthReleaseGoodsSum=" + currentMouthReleaseGoodsSum +
                ", currentDayReleaseGoodsSum=" + currentDayReleaseGoodsSum +
                ", userSum=" + userSum +
                ", goodsSum=" + goodsSum +
                '}';
    }
}
