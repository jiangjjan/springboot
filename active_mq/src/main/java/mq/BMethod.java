package mq;

import org.springframework.stereotype.Component;

@Component("b")
public class BMethod implements ApiMethod<Integer>{

    @Override
    public Integer getMethod() {
        return 1;
    }
}
