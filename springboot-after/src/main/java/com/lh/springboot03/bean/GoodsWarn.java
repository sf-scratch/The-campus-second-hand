package com.lh.springboot03.bean;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.util.Date;

@TableName("goods_warn")
public class GoodsWarn {
    @TableId(type = IdType.AUTO)
    private Integer warnId;
    private String warnName;
    private String warnDetail;
    private Date warnCreate;
    private Date warnUpdate;

    public GoodsWarn() {
    }

    public GoodsWarn(Integer warnId, String warnName, String warnDetail, Date warnCreate, Date warnUpdate) {
        this.warnId = warnId;
        this.warnName = warnName;
        this.warnDetail = warnDetail;
        this.warnCreate = warnCreate;
        this.warnUpdate = warnUpdate;
    }

    public Integer getWarnId() {
        return warnId;
    }

    public void setWarnId(Integer warnId) {
        this.warnId = warnId;
    }

    public String getWarnName() {
        return warnName;
    }

    public void setWarnName(String warnName) {
        this.warnName = warnName;
    }

    public String getWarnDetail() {
        return warnDetail;
    }

    public void setWarnDetail(String warnDetail) {
        this.warnDetail = warnDetail;
    }

    public Date getWarnCreate() {
        return warnCreate;
    }

    public void setWarnCreate(Date warnCreate) {
        this.warnCreate = warnCreate;
    }

    public Date getWarnUpdate() {
        return warnUpdate;
    }

    public void setWarnUpdate(Date warnUpdate) {
        this.warnUpdate = warnUpdate;
    }

    @Override
    public String toString() {
        return "GoodsWarn{" +
                "warnId=" + warnId +
                ", warnName='" + warnName + '\'' +
                ", warnDetail='" + warnDetail + '\'' +
                ", warnCreate=" + warnCreate +
                ", warnUpdate=" + warnUpdate +
                '}';
    }
}
