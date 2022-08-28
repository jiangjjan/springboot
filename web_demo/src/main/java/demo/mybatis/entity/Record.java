package demo.mybatis.entity;

import lombok.Getter;
import lombok.Setter;

import java.util.Set;


@Getter
@Setter
public class Record {
    private Long id;
    private String name;
    private Set<Integer> barcodes;
}
