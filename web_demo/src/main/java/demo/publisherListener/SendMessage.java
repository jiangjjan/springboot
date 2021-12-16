package demo.publisherListener;

import lombok.Data;
import lombok.NonNull;

@Data
public class SendMessage {
    @NonNull
    private String message;
}
