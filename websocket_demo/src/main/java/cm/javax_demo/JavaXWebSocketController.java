package cm.javax_demo;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Controller
@RequestMapping("websocket")
public class JavaXWebSocketController {

    @GetMapping("page")
    public Object openWebSocketHtml(){
        new Thread(() -> {
            while (true) {
                try {
                    Thread.sleep(  1000);
                    WebServer.broadCastMessage("{\"date\":\""+ DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss").format(LocalDateTime.now())+"\"}");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
        return "websocket";
    }
}
