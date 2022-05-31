package com.lh.springboot03.component;

import com.lh.springboot03.utils.WebSocketInstruct;
import com.lh.springboot03.utils.WebSocketUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;

/**
 * @Author: TheBigBlue
 * @Description: 向app端实时推送业务状态信息
 * @Date: 2019/7/16
 **/
//由于是websocket 所以原本是@RestController的http形式
//直接替换成@ServerEndpoint即可，作用是一样的 就是指定一个地址
//表示定义一个websocket的Server端
@Component
@ServerEndpoint(value = "/websocket/{uid}")
public class WebSocketServer {

    private static final Logger LOGGER = LoggerFactory.getLogger(WebSocketServer.class);

    /**
     * @Author: TheBigBlue
     * @Description: 加入连接
     * @Date: 2019/7/16
     * @Param appNo: 申请单号
     * @Param relTyp: 关系人类型
     * @Param session:
     * @Return:
     **/
    @OnOpen
    public void onOpen(@PathParam("uid") Integer uid, Session session) {
        LOGGER.info("用户[" + uid + "]加入连接!");
        WebSocketUtil.addSession(uid, session);
    }

    /**
     * @Author: TheBigBlue
     * @Description: 断开连接
     * @Date: 2019/7/16
     * @Param appNo:
     * @Param relTyp:
     * @Param session:
     * @Return:
     **/
    @OnClose
    public void onClose(@PathParam("uid") Integer uid, Session session) {
        LOGGER.info("用户[" + uid + "]断开连接!");
        WebSocketUtil.remoteSession(uid);
    }

    /**
     * @Author: TheBigBlue
     * @Description: 发送消息
     * @Date: 2019/7/16
     * @Param appNo: 申请单号
     * @Param relTyp: 关系人类型
     * @Param message: 消息
     * @Return:
     **/
    @OnMessage
    public void OnMessage(@PathParam("uid") Integer uid, String message) {
        String messageInfo = "服务器对用户[" + uid + "]发送消息：" + message;
        LOGGER.info(messageInfo);
        Session session = WebSocketUtil.ONLINE_SESSION.get(uid);
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


