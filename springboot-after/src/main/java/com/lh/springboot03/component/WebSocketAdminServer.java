package com.lh.springboot03.component;

import com.lh.springboot03.utils.WebSocketUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;

//由于是websocket 所以原本是@RestController的http形式
//直接替换成@ServerEndpoint即可，作用是一样的 就是指定一个地址
//表示定义一个websocket的Server端
@Component
@ServerEndpoint(value = "/websocketAdmin/{aid}")
public class WebSocketAdminServer {
    private static final Logger LOGGER = LoggerFactory.getLogger(WebSocketServer.class);

    @OnOpen
    public void onOpen(@PathParam("aid") Integer aid, Session session) {
        LOGGER.info("管理员[" + aid + "]加入连接!");
        WebSocketUtil.addAdminSession(aid, session);
    }

    @OnClose
    public void onClose(@PathParam("aid") Integer aid, Session session) {
        LOGGER.info("管理员[" + aid + "]断开连接!");
        WebSocketUtil.remoteAdminSession(aid);
    }

    @OnMessage
    public void OnMessage(@PathParam("aid") Integer aid, String message) {
        String messageInfo = "服务器对管理员[" + aid + "]发送消息：" + message;
        LOGGER.info(messageInfo);
        Session session = WebSocketUtil.ONLINE_SESSION.get(aid);
//        if("heart".equalsIgnoreCase(message)){
//            LOGGER.info("客户端向服务端发送心跳");
//            //向客户端发送心跳连接成功
//            message = "success";
//        }
        //发送普通信息
        WebSocketUtil.sendMessage(session, message);
    }

    @OnError
    public void onError(Session session, Throwable throwable) {
        LOGGER.error(session.getId() + "异常:", throwable);
        try {
            session.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        throwable.printStackTrace();
    }
}
