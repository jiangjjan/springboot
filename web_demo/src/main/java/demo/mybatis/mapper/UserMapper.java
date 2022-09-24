package demo.mybatis.mapper;

import demo.mybatis.entity.Record;
import demo.mybatis.entity.User;
import demo.mybatis.param.Id;
import demo.mybatis.param.Name;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserMapper {

    @Select("select * from t_user")
    List<User> selectAll();

    @Insert("insert into t_record (name,barcodes) values (#{name},#{barcodes,typeHandler=demo.mybatis.typehandler.SetNumberHandler} )")
    void addRecord(Record record);

    @Select("select * from t_record")
    List<Record> listRecord( );

    @Select("select * from t_user where name =#{name} and id =#{id}")
    <R extends Id&Name> List<User> selectByName(R param);


}

