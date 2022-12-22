package demo.mybatis.entity;

import demo.mybatis.enums.SexEnum;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import java.util.List;

@Data
public class User {

    Long id;

    @NotEmpty(message = "name不能为空")
    String name;

    SexEnum sex;

    @NotEmpty(message = "address not empty")
    List<String> address;
}
