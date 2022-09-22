package demo.schedule;

import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.config.ScheduledTask;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("task")
public class TaskController implements SchedulingConfigurer {
    ScheduledTaskRegistrar taskRegistrar;

    @Override
    public void configureTasks(ScheduledTaskRegistrar taskRegistrar) {
        this.taskRegistrar=taskRegistrar;
    }

    @GetMapping("test")
    public void test(String taskName){
        Assert.notNull(taskName,"taskName can't be null");
        System.out.println(taskName);
       for(ScheduledTask task: taskRegistrar.getScheduledTasks()){
           System.out.println(task.toString());
            if("demo.schedule.ScheduleConf.task1".equals(task.toString())){
                task.cancel();
            }
       }
    }

}
