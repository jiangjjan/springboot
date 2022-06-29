package demo.filetrans;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Paths;


@RestController
@RequestMapping("file")
public class FileController {

    @PostMapping("upload")
    public void receiveFile(MultipartFile file) throws IOException {
        file.transferTo(Paths.get("a"));
        System.out.println(file.getOriginalFilename());
        byte[] bytes = file.getBytes();
        System.out.println(new String(bytes));
    }
}
