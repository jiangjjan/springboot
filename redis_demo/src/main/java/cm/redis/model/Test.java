package cm.redis.model;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Test {
    private Long id;
    private String name;
    private LocalDateTime createTime;
}
