package cm.redis.mapper;

import cm.redis.model.Test;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TestMapper {

    @Select("select * from t_test where id = #{id}")
    Optional<Test> selectOne(Test param);

    @Select("select * from t_test")
    List<Test> listTest();

    @Options(useGeneratedKeys=true,keyProperty="id")
    @Insert("insert into t_test (name, create_time) values (#{name},#{createTime})")
    int insertTest(Test param);
}
