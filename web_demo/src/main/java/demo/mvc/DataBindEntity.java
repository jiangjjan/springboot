package demo.mvc;

import demo.springaop.OrgCode;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class DataBindEntity implements OrgCode {
    private String name;
    private Integer number;
    String orgCode;
    private LocalDate localDate;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime time;

}
