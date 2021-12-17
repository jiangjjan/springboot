package demo.service;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Service;

//实现这个接口,会在初始化bean后,注入ApplicationContext
@Service
public class ContextHolder implements ApplicationContextAware {


    ApplicationContext context;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        context = applicationContext;
    }

    public ApplicationContext get() {
        return context;
    }
}
