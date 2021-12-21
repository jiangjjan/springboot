package demo.publisherListener.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SynchronousEvent {

    @NonNull
    private String message;

    private boolean error;
}
