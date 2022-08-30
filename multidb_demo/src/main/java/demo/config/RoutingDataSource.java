package demo.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

import java.sql.Connection;
import java.sql.SQLException;

@Slf4j
public class RoutingDataSource extends AbstractRoutingDataSource {

    private static final ThreadLocal<String> datasourceContext = new ThreadLocal<>();

    @Override
    public Connection getConnection() throws SQLException {
        return super.getConnection();
    }

    @Override
    protected Object determineCurrentLookupKey() {

        // 从ThreadLocal中取出key:
        return datasourceContext.get();

    }

    public static void switchDataSource(String datasource) {

        log.info("change db {}",datasource);
        datasourceContext.set(datasource);

    }

}