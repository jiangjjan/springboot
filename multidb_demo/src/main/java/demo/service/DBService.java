package demo.service;

import demo.config.DataSource;
import demo.mapper.TestMapper;
import demo.model.Test;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@DataSource
public class DBService {

   final TestMapper testMapper;

   public List<Test> list(){
       return testMapper.selectAll();
   }
}
