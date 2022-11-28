package demo.mybatis.entity;

import demo.mybatis.enums.SexEnum;
import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
public class User {

    private Long id;

    @NotEmpty
    private String name;
    private SexEnum sex;
}
