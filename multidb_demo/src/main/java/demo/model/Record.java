package demo.model;

import demo.typehandler.SetNumberHandler;
import lombok.Data;
import tk.mybatis.mapper.annotation.ColumnType;

import javax.persistence.Id;
import javax.persistence.Table;
import java.time.LocalDateTime;
import java.util.Set;

@Data
@Table(name="t_record")
public class Record {

    @Id
    private Long id;
    private String name;
    @ColumnType(typeHandler = SetNumberHandler.class)
    private Set<Integer> barcodes;
    private String status;
    private LocalDateTime createTime;

    private Boolean isValid;
    private Boolean dead;
}
