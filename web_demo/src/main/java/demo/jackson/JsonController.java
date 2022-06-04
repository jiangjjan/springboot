package demo.jackson;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class JsonController {

    final ObjectMapper json;

    @GetMapping("enums")
    public Object covertEnum(@RequestBody EnumModel param) throws JsonProcessingException {
        EnumModel p = new EnumModel();
        p.setName("name");
        p.setStatus(StatusEnum.FAIL);
        System.out.println(json.writeValueAsString(p));
        return param;
    }

    @GetMapping("form/enum")
    public Object covertEnumForm( EnumModel param) throws JsonProcessingException {
        EnumModel p = new EnumModel();
        p.setName("name");
        p.setStatus(StatusEnum.FAIL);
        System.out.println(json.writeValueAsString(p));
        return param;
    }

}
