package demo.jackson;

import org.springframework.core.convert.converter.Converter;
import org.springframework.core.convert.converter.ConverterFactory;
import org.springframework.stereotype.Component;

@Component
public class EnumConverterFactory  implements ConverterFactory<String, ConverterBaseEnum> {

    @Override
    public <T extends ConverterBaseEnum> Converter<String, T> getConverter(Class<T> targetType) {
        return source ->  {
            for (T e: targetType.getEnumConstants()) {
                if (e.getCovertValue().equals(source)) {
                    return e;
                }
            }
            return null;
        };
    }
}
