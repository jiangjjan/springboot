package demo.contoller;

import demo.mapper.TestMapper;
import demo.model.Test;
import demo.service.DBService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("multi")
@RequiredArgsConstructor
@Slf4j
public class MultiDbController {

    final DBService dbService;
    final TestMapper testMapper;

    @GetMapping("master/list")
    public Object mysqlDBList(String p) {
        dbService.updateTest(p);
        Test test = testMapper.selectByName(p,p);
        log.info("test result {}", test);
        return dbService.listTest("");
    }

    @GetMapping("slave/list")
    public Object slaveDB(String p) {
        dbService.updateRecordById(p);
        return dbService.listRecord();
    }

    @GetMapping("name")
    public Object selectByName(String name) {
      return  testMapper.selectByName(name,name);
    }
}
