package demo.mapper;

import demo.config.DataSource;
import demo.model.Test;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface TestMapper extends BaseMapper<Test>{
}
