package demo.config;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import lombok.var;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.PropertySource;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

@Component
@EnableAutoConfiguration(exclude = DataSourceAutoConfiguration.class)
@ConditionalOnProperty("multidata.enable")
public class MultiDataSourceBuilder {

    @Bean
    @ConfigurationProperties("spring.datasource.master")
    HikariConfig masterProperties() {
        return new HikariConfig();
    }

    @Bean("masterDataSource")
    public DataSource masterDataSource(@Qualifier("masterProperties") HikariConfig config) {
        return new HikariDataSource(config);
    }

    @Bean
    @ConfigurationProperties("spring.datasource.slave")
    HikariConfig slaveProperties() {
        return new HikariConfig();
    }

    @Bean
    public DataSource slaveDataSource(@Qualifier("slaveProperties") HikariConfig config) {
        return new HikariDataSource(config);
    }

    @Primary
    @Bean
    DataSource dataSource(
            DataSource masterDataSource,
            DataSource slaveDataSource) {
        var ds = new RoutingDataSource();
        Map<Object, Object> dataSourceMap = new HashMap<>();
        dataSourceMap.put("master", masterDataSource);
        dataSourceMap.put("slave", slaveDataSource);
        // 关联两个DataSource:
        ds.setTargetDataSources(dataSourceMap);
        // 默认使用masterDataSource:
        ds.setDefaultTargetDataSource(masterDataSource);
        return ds;
    }

    @Bean
    DataSourceTransactionManager dataSourceTransactionManager(DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }
}

