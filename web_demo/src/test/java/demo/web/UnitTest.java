package demo.web;

import com.github.shyiko.mysql.binlog.BinaryLogClient;
import com.github.shyiko.mysql.binlog.event.*;
import com.kingdee.bos.webapi.sdk.K3CloudApi;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class UnitTest {

	@Test
	public void ss() throws IOException {


		List<String> strings = Files.readAllLines(Paths.get("D:/203d4e97-7068-4a8b-9958-c1cb58015f8e.json"));

		Set<String> ss = Collections.synchronizedSet(new HashSet<>());

		strings.parallelStream().forEach(e -> {

			ss.add(e.substring(e.indexOf("itemCode") + 9, e.lastIndexOf("\"")));


		});
		System.out.println(ss);

	}

	public static void main(String[] args) {
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

		K3CloudApi api = new K3CloudApi();
		String body = "{\"FormId\":\"SP_PickMtrl\",\"OrderString\":\"FAPPROVEDATE desc\",\"TopRowCount\":0,\"StartRow\":0,\"Limit\":0,\"SubSystemId\":\"\",\"FilterString\":\"FMaterialID.FNumber = '11.004412' and FStockOrgId.FNumber = 1023 and FDate = '2022-11-07'\",\"FieldKeys\":\"FMaterialID.F_Manufacturer,FLot,FMaterialName,FExpiryDate,FAPPROVEDATE,FSpecification,FDate,FActualQty\"}";
		List<List<Object>> ss = api.executeBillQuery(body);
		System.out.println(ss);

	}


}
