package com.lh.springboot03.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.lh.springboot03.bean.SystemMessage;
import com.lh.springboot03.mapper.Admin_SystemMessageMapper;
import com.lh.springboot03.service.Admin_SystemMessageService;
import com.lh.springboot03.utils.WebSocketInstruct;
import com.lh.springboot03.utils.WebSocketUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.websocket.RemoteEndpoint;
import javax.websocket.Session;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.Future;



@Service
public class Admin_SystemMessageServiceImpl implements Admin_SystemMessageService {

    @Autowired
    private Admin_SystemMessageMapper admin_systemMessageMapper;

    @Override
    public void releaseCommonSystemMessage(SystemMessage systemMessage) {
        admin_systemMessageMapper.insert(systemMessage);

        Collection<Session> sessions = WebSocketUtil.ONLINE_SESSION.values();
        for (Session session : sessions) {
            // getAsyncRemote()和getBasicRemote()异步与同步
            RemoteEndpoint.Async async = session.getAsyncRemote();
            //发送消息
            WebSocketInstruct<SystemMessage> socketInstruct = new WebSocketInstruct<>();
            socketInstruct.setInstruct(3);
            socketInstruct.setMessage("系统通知");
            socketInstruct.setData(systemMessage);
            Future<Void> future = async.sendText(JSONObject.toJSONString(socketInstruct));
            boolean done = future.isDone();
            System.out.println("服务器发送消息给客户端" + session.getId() + "的消息:" + socketInstruct + "，状态为:" + done);
        }
    }

    @Override
    public List<SystemMessage> getSystemMessageList() {
        QueryWrapper<SystemMessage> queryWrapper = new QueryWrapper<>();
        return admin_systemMessageMapper.selectList(queryWrapper.orderByDesc("create_date "));
    }
}
