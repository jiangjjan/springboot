package demo.mapper;

import demo.model.Test;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface TestMapper extends BaseMapper<Test> {
    Test selectByName(String name);
}
