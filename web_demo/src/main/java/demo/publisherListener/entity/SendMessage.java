package demo.publisherListener.entity;

import lombok.Data;
import lombok.NonNull;

@Data
public class SendMessage implements SendMessageFlag{
    @NonNull
    private String message;
}
