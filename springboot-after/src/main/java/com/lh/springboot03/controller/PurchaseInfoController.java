package com.lh.springboot03.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lh.springboot03.bean.PurchaseInfo;
import com.lh.springboot03.bean.SystemMessage;
import com.lh.springboot03.bean.User;
import com.lh.springboot03.service.PurchaseInfoService;
import com.lh.springboot03.utils.JsonResult;

import com.lh.springboot03.utils.TokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;


@CrossOrigin(originPatterns = "*", allowCredentials = "true", allowedHeaders = "*")
@RestController
@RequestMapping("/purchaseInfo")
public class PurchaseInfoController extends BaseController {
    @Autowired
    private PurchaseInfoService purchaseInfoService;

    @RequestMapping("/releasePurchaseInfo")
    public JsonResult<Void> releasePurchaseInfo(@RequestBody PurchaseInfo purchaseInfo, HttpServletRequest request){
        JsonResult<Void> result = new JsonResult<>();
        User user = TokenUtil.getUserFromRequest(request);
        purchaseInfo.setId(null);
        purchaseInfo.setUserId(user.getUid());
        purchaseInfo.setUserName(user.getUsername());
        purchaseInfo.setCreateDate(new Date());
        purchaseInfo.setStatus(0);
        purchaseInfoService.releasePurchaseInfo(purchaseInfo);
        result.setState(OK);
        result.setMessage("求购信息发布成功");
        return result;
    }

    @RequestMapping("/getPurchaseInfoList")
    public JsonResult<Page<PurchaseInfo>> getPurchaseInfoList(@RequestParam("currentPage") Integer currentPage,
                                                @RequestParam("size") Integer size){
        JsonResult<Page<PurchaseInfo>> result = new JsonResult<>();
        Page<PurchaseInfo> purchaseInfoPage = purchaseInfoService.getPurchaseInfoListByPage(currentPage, size);
        result.setState(OK);
        result.setMessage("求购信息获取成功");
        result.setData(purchaseInfoPage);
        return result;
    }
}
