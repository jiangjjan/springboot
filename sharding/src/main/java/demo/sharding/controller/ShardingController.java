package demo.sharding.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@CacheConfig
@RequestMapping("sharding")
public class ShardingController {

	@GetMapping("check")
	public String health(){
		return "success";
	}
}
