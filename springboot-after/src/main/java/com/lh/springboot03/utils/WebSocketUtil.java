package com.lh.springboot03.utils;


import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.websocket.RemoteEndpoint;
import javax.websocket.RemoteEndpoint.Async;
import javax.websocket.Session;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Future;



/**
 * @Author: TheBigBlue
 * @Description:
 * @Date: 2019/7/16
 **/
public class WebSocketUtil {

    private static final Logger LOGGER = LoggerFactory.getLogger(WebSocketUtil.class);

    /**
     * @Author: TheBigBlue
     * @Description: 使用map进行存储在线的session
     * @Date: 2019/7/16
     **/
    public static final Map<Integer, Session> ONLINE_SESSION = new ConcurrentHashMap<>();

    public static final Map<Integer, Session> ADMIN_ONLINE_SESSION = new ConcurrentHashMap<>();

    /**
     * @Author: TheBigBlue
     * @Description: 添加Session
     * @Date: 2019/7/16
     * @Param userKey:
     * @Param session:
     * @Return:
     **/
    public static void addSession(Integer uid, Session session) {
        ONLINE_SESSION.put(uid, session);
    }

    public static void addAdminSession(Integer aid, Session session) {
        ADMIN_ONLINE_SESSION.put(aid, session);
    }

    public static void remoteSession(Integer uid) {
        ONLINE_SESSION.remove(uid);
    }

    public static void remoteAdminSession(Integer aid) {
        ADMIN_ONLINE_SESSION.remove(aid);
    }

    /**
     * @Author: TheBigBlue
     * @Description: 向某个用户发送消息
     * @Date: 2019/7/16
     * @Param session:
     * @Param message:
     * @Return:
     **/
    public static Boolean sendMessage(Session session, String message) {
        if (session == null) {
            return false;
        }
        // getAsyncRemote()和getBasicRemote()异步与同步
        Async async = session.getAsyncRemote();
        //发送消息
        Future<Void> future = async.sendText(message);
        boolean done = future.isDone();
        LOGGER.info("服务器发送消息给客户端" + session.getId() + "的消息:" + message + "，状态为:" + done);
        return done;
    }

}


