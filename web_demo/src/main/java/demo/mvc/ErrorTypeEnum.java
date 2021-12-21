package demo.mvc;

import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum ErrorTypeEnum {

    IllaParam("10001","参数不符");

    @JsonValue
    public final  String code;

    public final String message;


}
