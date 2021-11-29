package demo.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
public class Event {

    @Getter
    @Setter
    private String msg;


    public Event(String msg) {
        this.msg = msg;
    }

}
