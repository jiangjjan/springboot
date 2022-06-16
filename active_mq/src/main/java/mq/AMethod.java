package mq;

import org.springframework.stereotype.Component;

@Component("a")
public class AMethod implements ApiMethod<String>{

    @Override
    public String getMethod() {
        return "a";
    }

}
