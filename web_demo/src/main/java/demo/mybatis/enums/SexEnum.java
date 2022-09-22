package demo.mybatis.enums;

import com.fasterxml.jackson.annotation.JsonValue;
import demo.mybatis.BaseEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum SexEnum implements BaseEnum {
    MAN(1,"男"),
    WOMAN(0,"女"),
    UNKNOW(2,"不详"),
    ;

    @JsonValue
    public final int code;

    public final String desc;


    @Override
    public String getValue() {
        return String.valueOf(code);
    }
}
