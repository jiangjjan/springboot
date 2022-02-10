package cm.javax_demo;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;


/**
 * 每次页面访问的时候会生成一个新的WebServer实例
 */
@Service
@ServerEndpoint("/ws")
@Slf4j
public class WebServer {

    public static Map<String, WebServer> serverSource = new ConcurrentHashMap<>();

    public static void broadCastMessage(String message) {
        serverSource.forEach((k, v) -> {
            if (v.getSession().isOpen()) {
                try {
                    v.getSession().getBasicRemote().sendText(message);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public static void sendSpecialOne(String id, String message) throws IOException {
        WebServer webServer = serverSource.get(id);
        if (webServer == null) {
            log.info("can't find session by id {}", id);
        } else {
            boolean open = webServer.session.isOpen();
            if (open) {
                webServer.session.getBasicRemote().sendText(message);
            } else {
                log.info("session has been closed, id is {}", id);
            }
        }
    }

    @Getter
    private Session session;

    /**
     * use @PathParam("key") String key add extra param
     * html url  ws:127.0.0.1/ws/param
     *
     */
    @OnOpen
    public void open(Session session) {
        System.out.println(this);
        serverSource.put(session.getId(), this);
        this.session = session;
        System.out.println(session.getId());
    }

    @OnClose
    public void onClose() {

        serverSource.remove(session.getId());
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
        serverSource.remove(session.getId());
        try {
            session.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        error.printStackTrace();
    }

}
