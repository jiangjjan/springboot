package demo.mybatis.enums;

import com.fasterxml.jackson.annotation.JsonValue;
import demo.mybatis.BaseEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum SexEnum implements BaseEnum {
    MAN(1),
    WOMAN(0),
    UNKNOW(2),
    ;

    @JsonValue
    public final int code;


    @Override
    public String getValue() {
        return String.valueOf(code);
    }
}
