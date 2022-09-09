package cm.redis.model;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Test implements Id {
    private Long id;
    private String name;
    private LocalDateTime createTime;
}
