package demo.mybatis;

import demo.mybatis.entity.Record;
import demo.mybatis.mapper.UserMapper;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
public class UserController {

   final UserMapper userMapper;

   @GetMapping("queryUser")
   public Object  queryUser( ){
       return userMapper.selectAll();
   }

    @GetMapping("queryUserByName")
    public Object queryUserByName( ){

       Record p = new Record();
       p.setName("hua");
       p.setId(2L);

        return userMapper.selectByName(p);
    }


}
