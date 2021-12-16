package demo.publisherListener;

import org.springframework.stereotype.Repository;

@Repository
public interface TestMapper {
    Integer addOne(String name);
}
