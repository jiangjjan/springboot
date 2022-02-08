package cm.redis.model;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class Test {
    private Long id;
    private String name;
    private LocalDateTime createTime;
}
