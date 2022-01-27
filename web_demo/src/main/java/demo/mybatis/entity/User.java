package demo.mybatis.entity;

import demo.mybatis.enums.SexEnum;
import lombok.Data;

@Data
public class User {

    private Long id;
    private String name;
    private SexEnum sex;
}
