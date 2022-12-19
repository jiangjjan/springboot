package demo.web;

import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.github.shyiko.mysql.binlog.BinaryLogClient;
import com.github.shyiko.mysql.binlog.event.*;
import lombok.extern.slf4j.Slf4j;
import okhttp3.ConnectionPool;
import okhttp3.OkHttpClient;
import org.apache.commons.lang3.math.NumberUtils;
import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.client.exception.MQBrokerException;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.common.RemotingHelper;
import org.apache.rocketmq.remoting.exception.RemotingException;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.http.client.OkHttp3ClientHttpRequestFactory;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.time.Duration;
import java.util.Arrays;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

@Slf4j
public class UnitTest {

	RestTemplate restTemplate = new RestTemplate();

	{
		restTemplate.setRequestFactory(new OkHttp3ClientHttpRequestFactory(client()));
		int index = 0;

		for (int i = 0; i < restTemplate.getMessageConverters().size(); i++) {
			if (restTemplate.getMessageConverters().get(i) instanceof MappingJackson2HttpMessageConverter)
				index = i;
		}
		restTemplate.getMessageConverters().add(index,converter());
	}
	@Test
	public void ss() {


		IntStream.rangeClosed(1,255).parallel().forEach(e->{
			restTemplate.getForEntity("http://localhost:9301/biochemicalMaterial/decrTestNum?orgCode=1023&labGroupCode=0002&deviceNo=AU5811&itemCode=LP(A)",String.class);
		});

	}

	OkHttpClient client() {
		return new OkHttpClient().newBuilder()
				.connectionPool(new ConnectionPool(500, 300, TimeUnit.SECONDS))
				.connectTimeout(Duration.ofSeconds(5))
				.readTimeout(Duration.ofSeconds(20))
				.build();
	}

	MappingJackson2HttpMessageConverter converter() {
		JsonMapper json = new JsonMapper();
		json.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
		json.disable(SerializationFeature.FAIL_ON_EMPTY_BEANS);
		MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter(json);
		converter.setSupportedMediaTypes(Arrays.asList(MediaType.TEXT_PLAIN, MediaType.APPLICATION_JSON));
		return converter;
	}
	@Test
	public  void main() {
		BinaryLogClient client = new BinaryLogClient("127.0.0.1", 3307, "root", "123456");
		client.setServerId(3);

		client.registerEventListener(event -> {
			EventData data = event.getData();
			if (data instanceof TableMapEventData) {
				System.out.println("Table:");
				TableMapEventData tableMapEventData = (TableMapEventData) data;
				System.out.println(tableMapEventData.getTableId() + ": [" + tableMapEventData.getDatabase() + "-" + tableMapEventData.getTable() + "]");
			}
			if (data instanceof UpdateRowsEventData) {
				System.out.println("Update:");
				System.out.println(data.toString());
			} else if (data instanceof WriteRowsEventData) {
				System.out.println("Insert:");
				System.out.println(data.toString());
			} else if (data instanceof DeleteRowsEventData) {
				System.out.println("Delete:");
				System.out.println(data.toString());
			}
		});

		try {
			client.connect();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Test
	public void close() throws ClassNotFoundException, SQLException {
		Connection conn = null;
		Class.forName("com.mysql.cj.jdbc.Driver");
		String url = "jdbc:mysql://localhost:3306?serverTimezone=UTC";
		String username = "root";
		String password = "123456";
		conn = DriverManager.getConnection(url, username, password);

	}

	@Test
	public void k3Client() throws Exception {

//		K3CloudApi api = new K3CloudApi();
//		String body = "{\"FormId\":\"SP_PickMtrl\",\"OrderString\":\"FAPPROVEDATE desc\",\"TopRowCount\":0,\"StartRow\":0,\"Limit\":0,\"SubSystemId\":\"\",\"FilterString\":\"FMaterialID.FNumber = '11.004412' and FStockOrgId.FNumber = 1023 and FDate = '2022-11-07'\",\"FieldKeys\":\"FMaterialID.F_Manufacturer,FLot,FMaterialName,FExpiryDate,FAPPROVEDATE,FSpecification,FDate,FActualQty\"}";
//		List<List<Object>> ss = api.executeBillQuery(body);
//		System.out.println(ss);

	}


	@Test
	public void number() {
		Double s = 7D/2L;

		System.out.println(NumberUtils.isDigits(String.valueOf(4/2)));
	}

	static String topic = "data_sync_topic";
	static String server = "10.0.11.225:9876";
	static String tag = "data_sync_test";

	@Test
	public void producer() throws UnsupportedEncodingException, InterruptedException, RemotingException, MQClientException, MQBrokerException {

		// 初始化一个producer并设置Producer group name
		DefaultMQProducer producer = new DefaultMQProducer("producer-group-default"); //（1）
		// 设置NameServer地址
		producer.setNamesrvAddr(server);  //（2）
		// 启动producer
		producer.start();
		for (int i = 0; i < 999+1; i++) {
			// 创建一条消息，并指定topic、tag、body等信息，tag可以理解成标签，对消息进行再归类，RocketMQ可以在消费端对tag进行过滤
			Message msg = new Message(topic /* Topic */,
					tag /* Tag */,
					("Hello RocketMQ Clinical " + i).getBytes(RemotingHelper.DEFAULT_CHARSET) /* Message body */
			);   //（3）
			// 利用producer进行发送，并同步等待发送结果
			SendResult sendResult = producer.send(msg);   //（4）
			System.out.printf("%s%n", sendResult);
		}
		// 一旦producer不再使用，关闭producer
		producer.shutdown();

	}

	public static void main(String[] args) throws MQClientException {
		// 初始化consumer，并设置consumer group name
		DefaultMQPushConsumer consumer = new DefaultMQPushConsumer("data_sync_consumer_groups");

		// 设置NameServer地址
		consumer.setNamesrvAddr(server);
		//订阅一个或多个topic，并指定tag过滤条件，这里指定*表示接收所有tag的消息
		consumer.subscribe(topic, "*");
		//注册回调接口来处理从Broker中收到的消息
		consumer.registerMessageListener((MessageListenerConcurrently) (msgs, context) -> {

			FlatMessage flatMessage = JSON.parseObject(new String(msgs.get(0).getBody(), StandardCharsets.UTF_8), FlatMessage.class);
			// 返回消息消费状态，ConsumeConcurrentlyStatus.CONSUME_SUCCESS为消费成功
			log.info("Thread {} Message {}",Thread.currentThread().getId(),flatMessage);
			return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;


		});
		// 启动Consumer
		consumer.start();

	}


}
