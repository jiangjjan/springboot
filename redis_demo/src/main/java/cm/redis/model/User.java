package cm.redis.model;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class User<T> {
    private Long id;
    private String name;
    private Integer age;
    private T sex;
}
