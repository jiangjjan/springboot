package demo.mybatis.entity;

import demo.mybatis.param.Id;
import demo.mybatis.param.Name;
import lombok.*;

import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Record implements Id, Name {
    private Long id;
    private String name;
    private Set<Integer> barcodes;
}
