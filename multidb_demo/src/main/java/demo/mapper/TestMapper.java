package demo.mapper;

import demo.model.Test;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface TestMapper extends BaseMapper<Test> {
    Test selectByName( String name,String id);
}
