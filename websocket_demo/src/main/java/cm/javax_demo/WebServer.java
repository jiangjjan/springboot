package cm.javax_demo;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


/**
 * 每次页面访问的时候会生成一个新的WebServer实例
 */
@Service
@ServerEndpoint("/ws")
@Slf4j
public class WebServer {

    public static List<WebServer> serverSource = Collections.synchronizedList(new ArrayList<>());

    public static void sendMessage1(final String message) {
        serverSource.forEach(se -> se.sendMessage(message));
    }

    private Session session;

    /**
     * use @PathParam("key") String key add extra param
     * html url  ws:127.0.0.1/ws/param
     *
     * @param session
     */
    @OnOpen
    public void open(Session session) {
        System.out.println(this);
        serverSource.add(this);
        this.session = session;
        System.out.println(session.getId());
    }

    @OnClose
    public void onClose() {
        log.info("close webSocket");
        serverSource.remove(this);
        try {
            this.session.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @OnMessage
    public void onMessage(String message, Session session) {
        System.out.println("来自客户端的消息:" + message + session.getId());
    }

    @OnError
    public void onError(Session session, Throwable error) {
        System.out.println("发生错误");
        error.printStackTrace();
    }

    public void sendMessage(String message) {
        try {
            this.session.getBasicRemote().sendText(message);
            session.getId();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
