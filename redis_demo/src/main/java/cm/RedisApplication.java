package cm;

import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.annotation.MapperScan;
import org.redisson.api.RScheduledExecutorService;
import org.redisson.api.RedissonClient;
import org.redisson.api.redisnode.RedisNodes;
import org.redisson.api.redisnode.RedisSingle;
import org.redisson.config.Config;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.stereotype.Repository;

import java.io.Serializable;
import java.util.Collections;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@SpringBootApplication
@EnableScheduling
@MapperScan(annotationClass = Repository.class)
@Slf4j
public class RedisApplication {

    public static void main(String[] args) {
        ConfigurableApplicationContext run = SpringApplication.run(RedisApplication.class, args);

        //        System.out.println(LocalDateTime.now());
//        HikariDataSource dataSource = new HikariDataSource();
//        dataSource.setUsername("root");
//        dataSource.setPassword("123456");
//        dataSource.setJdbcUrl("jdbc:mysql://localhost:3307/test?useLegacyDatetimeCode=false&serverTimezone=Asia/Shanghai&useUnicode=true&characterEncoding=utf-8&allowMultiQueries=true&useSSL=false&allowPublicKeyRetrieval=true");
//
//        Configuration configuration = new Configuration();
//        configuration.addMappers("cm.redis.mapper");
//        Environment environment = new Environment("enviroment",new JdbcTransactionFactory(),dataSource);
//        configuration.setEnvironment(environment);
//
//        SqlSessionFactoryBuilder builder = new SqlSessionFactoryBuilder();
//        SqlSessionFactory factory = builder.build(configuration);
//
//        SqlSession sqlSession = factory.openSession(true);
//        TestMapper mapper = sqlSession.getMapper(TestMapper.class);


//        List<cm.redis.model.Test> users = mapper.listTest();
//        System.out.println(users);
    }

}
