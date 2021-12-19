package demo.awareinterface;

import org.springframework.beans.factory.BeanNameAware;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

@Component("beanName")
public class AwareBean implements BeanNameAware {

    String name;

    @Override
    public void setBeanName(String name) {
        this.name = name;
    }
}
