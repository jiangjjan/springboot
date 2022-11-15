package demo.jackson;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Part;
import java.io.IOException;
import java.util.Map;
import java.util.UUID;
import java.util.stream.IntStream;

@Slf4j
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
    public Object covertEnumForm(EnumModel param, @RequestHeader(required = false) String token, @RequestHeader String appId) throws JsonProcessingException {
        EnumModel p = new EnumModel();
        p.setName("name");
        p.setStatus(StatusEnum.FAIL);
        System.out.println(json.writeValueAsString(p));
        param.setName(token);
        System.out.println(appId);
        return param;
    }

    @GetMapping("req")
    public Object aa(@RequestBody Map<String, String> param) throws JsonProcessingException {
        System.out.println(param);
        return param;
    }

    @GetMapping("compression")
    public String compression() {
        StringBuilder sb = new StringBuilder();
        IntStream.rangeClosed(1, 4028).forEach(sb::append);
        return sb.toString();
    }

    @PostMapping("upload")
    public void addDataByFile(String token, Part file) throws IOException, InterruptedException {
        System.err.println("token:"+token);

        String submittedFileName = file.getSubmittedFileName();
        System.out.println(submittedFileName);
        file.write("D:/"+ UUID.randomUUID().toString() +submittedFileName);
        log.info("end addDataByFile");

    }
}
