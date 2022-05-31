package com.lh.springboot03.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lh.springboot03.bean.PurchaseInfo;

public interface Admin_PurchaseInfoService {
    Page<PurchaseInfo> getPurchaseInfoListByPage(int current, int size);

    void deletePurchaseInfoById(Integer id);

    void updatePurchaseInfoStatusById(Integer purchaseInfoId, Integer status);
}
