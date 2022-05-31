package com.lh.springboot03.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lh.springboot03.bean.Goods;
import com.lh.springboot03.bean.PurchaseInfo;
import com.lh.springboot03.mapper.PurchaseInfoMapper;
import com.lh.springboot03.service.PurchaseInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class PurchaseInfoServiceImpl implements PurchaseInfoService {
    @Autowired
    private PurchaseInfoMapper purchaseInfoMapper;

    @Override
    public void releasePurchaseInfo(PurchaseInfo purchaseInfo) {
        purchaseInfoMapper.insert(purchaseInfo);
    }

    @Override
    public Page<PurchaseInfo> getPurchaseInfoListByPage(int current, int size) {
        Page<PurchaseInfo> page = new Page<>(current,size);
        QueryWrapper<PurchaseInfo> wrapper = new QueryWrapper<>();
        //调用getRecords，取出集合数据
        purchaseInfoMapper.selectPage(
                page, wrapper.ge("status",0).orderByDesc("create_date "));
//        System.out.println("获取当前页:"+goodsPage.getCurrent());
//        System.out.println("获取总记录数:"+goodsPage.getTotal());
//        System.out.println("获取每页的条数:"+goodsPage.getSize());
//        System.out.println("获取每页数据的集合:"+goodsPage.getRecords());
//        System.out.println("获取总页数:"+goodsPage.getPages());
//        System.out.println("是否存在下一页:"+goodsPage.hasNext());
//        System.out.println("是否存在上一页:"+goodsPage.hasPrevious());
        return page;
    }
}
