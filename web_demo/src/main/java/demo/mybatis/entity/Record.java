package demo.mybatis.entity;

import lombok.*;

import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Record {
    private Long id;
    private String name;
    private Set<Integer> barcodes;
}
