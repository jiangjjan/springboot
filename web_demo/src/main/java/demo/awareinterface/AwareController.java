package demo.awareinterface;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AwareController {

    final AwareBean awareBean;

    final ResourceLoader resourceLoader;

    public AwareController(AwareBean awareBean, ResourceLoader resourceLoader) {
        this.awareBean = awareBean;
        this.resourceLoader = resourceLoader;
    }

    @RequestMapping("aware")
    public String test() {
        Resource resource = resourceLoader.getResource("classpath:file/file.txt");

        return awareBean.name + "||" + resource.isOpen();

    }
}
