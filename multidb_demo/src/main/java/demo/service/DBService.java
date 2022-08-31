package demo.service;

import demo.config.DataSource;
import demo.mapper.RecordMapper;
import demo.mapper.TestMapper;
import demo.model.Record;
import demo.model.Test;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Transactional 必须与 DataSource 一起使用,同时不然
 */
@Repository
@RequiredArgsConstructor
@DataSource
@Slf4j
@Transactional
public class DBService {

    final TestMapper testMapper;
    final RecordMapper recordMapper;

    public List<Test> listTest(String name) {
        return testMapper.selectAll();
    }

    public void updateTest(String name) {

        List<Test> tests = testMapper.selectAll();
        Test test = tests.get(0);
        test.setName(name);
        testMapper.updateByPrimaryKeySelective(test);
        if ("throw".equals(name))
            throw new RuntimeException("throw roll back test");
    }

    @DataSource("slave")
    public List<Record> listRecord() {
        return recordMapper.selectAll();
    }

    @DataSource("slave")
    public void updateRecordById(String p) {

        List<Record> tests = recordMapper.selectAll();
        Record test = tests.get(0);
        test.setName(p);
        recordMapper.updateByPrimaryKeySelective(test);
        if (p.equals("throw"))
            throw new RuntimeException("throw roll back test");
    }
}
