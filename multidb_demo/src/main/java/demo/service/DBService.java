package demo.service;

import demo.config.DataSource;
import demo.mapper.TestMapper;
import demo.model.Test;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DBService {

    final TestMapper testMapper;


    @DataSource("slave")
    public List<Test> listTest(String name) {
        return testMapper.selectAll();
    }
}
