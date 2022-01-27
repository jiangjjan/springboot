package demo.mybatis.mapper;

import demo.mybatis.entity.User;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserMapper {

    @Select("select * from t_user")
    List<User> selectAll();

    @Select("select * from t_user where name =#{name} and id =#{id}")
    List<User> selectByName(String name,Long id);
}

