package demo.jackson;

import lombok.Data;

@Data
public class EnumModel {

    private StatusEnum status;

    private String name;
}
