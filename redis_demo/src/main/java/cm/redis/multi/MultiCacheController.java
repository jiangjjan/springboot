package cm.redis.multi;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("multi")
@RequiredArgsConstructor
@Slf4j
public class MultiCacheController {


    final MultiCacheService multiCacheService;

    @GetMapping("cache")
    public Object list() {
        log.info("multi list cache");
        return multiCacheService.list();
    }
}
