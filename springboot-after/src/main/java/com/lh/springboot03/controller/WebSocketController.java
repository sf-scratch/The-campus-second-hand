package com.lh.springboot03.controller;

import com.lh.springboot03.service.CommentService;
import com.lh.springboot03.utils.JsonResult;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;



@CrossOrigin(originPatterns = "*", allowCredentials = "true", allowedHeaders = "*")
@RestController
@RequestMapping("/webSocketController")
public class WebSocketController extends BaseController {

    @Autowired
    private CommentService commentService;

    @RequestMapping("/notifyUnreadMessageNum")
    public JsonResult<Void> notifyUnreadMessageNum(Integer uid) {
        JsonResult<Void> result = new JsonResult<>();
        if (commentService.updateUnreadMessageNum(uid)){
            result.setMessage("发送成功");
            result.setState(OK);
        }else {
            result.setMessage("用户不在线");
            result.setState(123);
        }
        return result;
    }

}
