package demo.publisherListener.entity;

import lombok.Data;
import lombok.NonNull;

@Data
public class SendMessage {
    @NonNull
    private String message;
}
