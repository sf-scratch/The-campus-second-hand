package com.lh.springboot03.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lh.springboot03.bean.PurchaseInfo;

import java.util.List;

public interface PurchaseInfoService {
    void releasePurchaseInfo(PurchaseInfo purchaseInfo);

    Page<PurchaseInfo> getPurchaseInfoListByPage(int current, int size);
}
