package demo.springaop;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Service
@Qualifier("sc")
public class AopService {

	@Param("@sc.check(#name)")
	@LogConsumerTime
	public String param(String name, Long age){
		return "aop";
	}

	public String check(String value){
		return value+"{{{{{{}}}}}";
	}
}
