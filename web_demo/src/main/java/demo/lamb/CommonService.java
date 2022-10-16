package demo.lamb;

import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.function.Predicate;

@Service
@RequiredArgsConstructor
public class CommonService {

	final ApplicationContext apl;

	@PostConstruct
	public void ss(){
		Predicate<String> a = (Predicate<String>)apl.getBean("a");
		a.test("s");

	}
}
