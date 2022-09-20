package demo.springaop;

import org.springframework.stereotype.Service;

@Service
public class AopService {

	@Param
	@LogConsumerTime
	public String aop(String name,Long age){
		return "aop";
	}
}
