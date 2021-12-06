package demo.publisherListener;

import lombok.Data;
import lombok.NonNull;
import org.springframework.boot.SpringBootConfiguration;

@Data
public class SendMessage {
    @NonNull
    private String message;
}
