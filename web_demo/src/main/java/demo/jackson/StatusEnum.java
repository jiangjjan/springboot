package demo.jackson;

import com.fasterxml.jackson.annotation.JsonCreator;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum StatusEnum implements ConverterBaseEnum{

    SUCCESS(2),
    FAIL(4),
    ;

    public final Integer code;

    @JsonCreator(mode = JsonCreator.Mode.DELEGATING)
    public static StatusEnum create(Integer code){

        for(StatusEnum e:StatusEnum.values()){
            if(e.code.equals(code))
                return e;
        }

        return null;
    }

    @Override
    public String getCovertValue() {
        return code.toString();
    }
}
