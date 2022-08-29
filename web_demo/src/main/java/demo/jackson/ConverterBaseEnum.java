package demo.jackson;

/**
 * 枚举实现  ConverterBaseEnum ,springmvc 就可以将转换枚举
 */
public interface ConverterBaseEnum {

    String getCovertValue();
}
