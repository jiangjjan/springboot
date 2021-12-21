package demo.mvc;

import lombok.Data;


@Data
public class ErrorResponse {

    private ErrorTypeEnum code;

    private String message;
}
