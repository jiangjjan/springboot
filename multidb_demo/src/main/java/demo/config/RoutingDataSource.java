package demo.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

@Slf4j
public class RoutingDataSource extends AbstractRoutingDataSource {

    private static final ThreadLocal<String> datasourceContext = new ThreadLocal<>();

    @Override
    protected Object determineCurrentLookupKey() {
        // 从ThreadLocal中取出key:
        return getDataSourceRoutingKey();
    }

    public static Object getDataSourceRoutingKey() {
        return datasourceContext.get();
    }

    public static void switchDataSource(String datasource) {
        System.out.println(String.format("determine target datasource: %s}", datasource));
        datasourceContext.set(datasource);
    }

}