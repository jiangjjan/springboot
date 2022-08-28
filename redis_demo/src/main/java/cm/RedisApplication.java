package cm;

import cm.redis.mapper.TestMapper;
import com.zaxxer.hikari.HikariDataSource;
import org.apache.ibatis.mapping.Environment;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.apache.ibatis.transaction.jdbc.JdbcTransactionFactory;
import org.junit.jupiter.api.Test;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.stereotype.Repository;
import org.apache.ibatis.session.Configuration;

import java.time.LocalDateTime;
import java.util.List;

@SpringBootApplication
@EnableScheduling
@MapperScan(annotationClass = Repository.class)
public class RedisApplication {

    public static void main(String[] args) {
        SpringApplication.run(RedisApplication.class, args);
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
