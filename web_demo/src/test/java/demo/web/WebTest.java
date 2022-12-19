package demo.web;

import com.alibaba.fastjson.JSON;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.client.exception.MQClientException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import java.nio.charset.StandardCharsets;

import static demo.web.UnitTest.server;
import static demo.web.UnitTest.topic;

@RunWith(SpringRunner.class)
@Slf4j
@SpringBootTest
@RequiredArgsConstructor
public class WebTest {

    final TestRestTemplate request;

    @Test
    public void interceptor() {
        String interceptor = request.getForObject("interceptor", String.class);
        System.err.println(interceptor);
    }

    public static void main(String[] args) throws MQClientException {
        // 初始化consumer，并设置consumer group name
        DefaultMQPushConsumer consumer = new DefaultMQPushConsumer("data_sync_consumer_groups");

        // 设置NameServer地址
        consumer.setNamesrvAddr(server);
        //订阅一个或多个topic，并指定tag过滤条件，这里指定*表示接收所有tag的消息
        consumer.subscribe("private-test", "*");
        //注册回调接口来处理从Broker中收到的消息
        consumer.registerMessageListener((MessageListenerConcurrently) (msgs, context) -> {
            FlatMessage flatMessage = JSON.parseObject(new String(msgs.get(0).getBody(), StandardCharsets.UTF_8), FlatMessage.class);
            // 返回消息消费状态，ConsumeConcurrentlyStatus.CONSUME_SUCCESS为消费成功
            log.info("Thread {} Message {}", Thread.currentThread().getId(), flatMessage);
            return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;


        });
        // 启动Consumer
        consumer.start();

    }

}
