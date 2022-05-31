package com.lh.springboot03.controller;


import com.lh.springboot03.bean.SystemMessage;
import com.lh.springboot03.service.Admin_SystemMessageService;
import com.lh.springboot03.utils.JsonResult;
import com.lh.springboot03.utils.TokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;

@CrossOrigin(originPatterns = "*", allowCredentials = "true", allowedHeaders = "*")
@RestController
@RequestMapping("/systemMessage")
public class SystemMessageController extends BaseController {
    @Autowired
    private Admin_SystemMessageService admin_systemMessageService;

    @RequestMapping("/getSystemMessageList")
    public JsonResult<List<SystemMessage>> getSystemMessageList(){
        JsonResult<List<SystemMessage>> result = new JsonResult<>();
        result.setData(admin_systemMessageService.getSystemMessageList());
        result.setState(OK);
        result.setMessage("获取系统消息列表成功");
        return result;
    }

}
