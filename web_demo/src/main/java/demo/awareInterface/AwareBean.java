package demo.awareInterface;

import org.springframework.beans.factory.BeanNameAware;
import org.springframework.stereotype.Component;

@Component("beanName")
public class AwareBean implements BeanNameAware {

    String name;

    @Override
    public void setBeanName(String name) {
        this.name = name;
    }
}
