package demo.mvc;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

import static demo.mvc.ErrorTypeEnum.IllaParam;

@RestController
@RequestMapping("bind")
@Slf4j
public class WebDataBindController {

    @RequestMapping("web")
    public Object webData(DataBindEntity param) {
        System.out.println(param);
        return param;
    }

    @RequestMapping("json")
    public Object jsonData(@RequestBody DataBindEntity param) {
        System.out.println(LocalDateTime.now());
        System.out.println(param);
        return param;
    }

    @ExceptionHandler(JacksonConfig.JsonMessageConverterException.class)
    @ResponseStatus(HttpStatus.OK)
    public Object ex() {
        ErrorResponse response = new ErrorResponse();
        response.setCode(IllaParam);
        response.setMessage(IllaParam.message);
        return response;
    }
}
