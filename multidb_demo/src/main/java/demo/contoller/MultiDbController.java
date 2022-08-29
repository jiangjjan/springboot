package demo.contoller;

import demo.service.DBService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("multi")
@RequiredArgsConstructor
public class MultiDbController {

   final DBService mysqlDBService;

    @GetMapping("mysql/list")
    public Object mysqlDBList(){
        return mysqlDBService.list();
    }
}
