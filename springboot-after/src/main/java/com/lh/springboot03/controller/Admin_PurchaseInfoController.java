package com.lh.springboot03.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lh.springboot03.bean.PurchaseInfo;
import com.lh.springboot03.service.Admin_PurchaseInfoService;
import com.lh.springboot03.utils.JsonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@CrossOrigin(originPatterns = "*", allowCredentials = "true", allowedHeaders = "*")
@RestController
@RequestMapping("/admin/purchaseInfo")
public class Admin_PurchaseInfoController extends BaseController {
    @Autowired
    Admin_PurchaseInfoService admin_purchaseInfoService;

    @RequestMapping("/getPurchaseInfoList")
    public JsonResult<Page<PurchaseInfo>> getPurchaseInfoList(@RequestParam("currentPage") Integer currentPage,
                                                              @RequestParam("size") Integer size){
        Page<PurchaseInfo> purchaseInfoPage = admin_purchaseInfoService.getPurchaseInfoListByPage(currentPage, size);
        JsonResult<Page<PurchaseInfo>> result = new JsonResult<>();
        result.setState(OK);
        result.setMessage("求购信息获取成功");
        result.setData(purchaseInfoPage);
        return result;
    }

    @RequestMapping("/deletePurchaseInfoById")
    public JsonResult<Void> deletePurchaseInfoById(@RequestParam("purchaseInfoId") Integer purchaseInfoId){
        JsonResult<Void> result = new JsonResult<>();
        admin_purchaseInfoService.deletePurchaseInfoById(purchaseInfoId);
        result.setState(OK);
        result.setMessage("求购信息删除成功");
        return result;
    }

    //修改商品状态
    @RequestMapping("/updatePurchaseInfoStatusById")
    public JsonResult<Void> updatePurchaseInfoStatusById(@RequestParam("purchaseInfoId") Integer purchaseInfoId,
                                                       @RequestParam("status") Integer status){
        JsonResult<Void> result = new JsonResult<>();
        admin_purchaseInfoService.updatePurchaseInfoStatusById(purchaseInfoId,status);
        result.setState(OK);
        result.setMessage("求购信息状态修改成功");
        return result;
    }
}
