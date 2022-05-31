package com.lh.springboot03.controller;


import com.lh.springboot03.bean.Admin;
import com.lh.springboot03.bean.SystemMessage;
import com.lh.springboot03.bean_admin.GoodsCategoryTree;
import com.lh.springboot03.service.Admin_SystemMessageService;
import com.lh.springboot03.utils.JsonResult;
import com.lh.springboot03.utils.TokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

@CrossOrigin(originPatterns = "*", allowCredentials = "true", allowedHeaders = "*")
@RestController
@RequestMapping("/admin/systemMessage")
public class Admin_SystemMessageController extends BaseController {

    @Autowired
    private Admin_SystemMessageService admin_systemMessageService;


    @RequestMapping("/releaseCommonSystemMessage")
    public JsonResult<SystemMessage> releaseCommonSystemMessage(@RequestBody SystemMessage systemMessage, HttpServletRequest request){
        JsonResult<SystemMessage> result = new JsonResult<>();
        Admin admin = TokenUtil.getAdminFromRequest(request);
        systemMessage.setAdminId(admin.getId());
        systemMessage.setAdminName(admin.getName());
        systemMessage.setCreateDate(new Date());
        systemMessage.setContentType(0);
        admin_systemMessageService.releaseCommonSystemMessage(systemMessage);
        result.setState(OK);
        result.setMessage("通知发送成功");
        return result;
    }

    @RequestMapping("/releaseBulletinBoardMessage")
    public JsonResult<SystemMessage> releaseBulletinBoardMessage(@RequestBody SystemMessage systemMessage, HttpServletRequest request){
        JsonResult<SystemMessage> result = new JsonResult<>();
        Admin admin = TokenUtil.getAdminFromRequest(request);
        systemMessage.setAdminId(admin.getId());
        systemMessage.setAdminName(admin.getName());
        systemMessage.setCreateDate(new Date());
        systemMessage.setContentType(1);
        admin_systemMessageService.releaseCommonSystemMessage(systemMessage);
        result.setState(OK);
        result.setMessage("公告发送成功");
        return result;
    }
}
