package demo.web;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class WebTest {

    @Autowired
    TestRestTemplate request;

    @Test
    public void interceptor() {
        String interceptor = request.getForObject("interceptor", String.class);
        System.err.println(interceptor);
    }
}
