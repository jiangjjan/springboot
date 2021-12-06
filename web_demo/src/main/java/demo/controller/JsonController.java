package demo.controller;

import demo.entity.Event;
import demo.entity.ValueEntity;
import demo.service.ContextHolder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@Slf4j
@RestController
@RequestMapping("web")
public class JsonController {

    ValueEntity entity;

    public JsonController(ValueEntity entity) {
        this.entity = entity;
    }

    @RequestMapping(value = "json")
    public ValueEntity value() {

        log.info(entity.toString());

        return entity;
    }

    @Autowired
    ContextHolder holder;

    @Autowired
    ApplicationEventPublisher publishEvent;


    @GetMapping("get")
    public Object event(ModelAndView mod) {
            log.info(String.valueOf(mod.getModel()));
        publishEvent.publishEvent(new Event("aa"));
        return holder.get().getBean(ValueEntity.class);
    }




}
