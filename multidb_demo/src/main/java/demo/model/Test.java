package demo.model;


import lombok.Data;
import tk.mybatis.mapper.annotation.KeySql;

import javax.persistence.Table;
import java.time.LocalDateTime;


@Table(name="t_test")
@Data
public class Test {

    @KeySql(useGeneratedKeys=true)
    private  Long id;

    private String name;

    private LocalDateTime createTime;

    private Integer size;
}
