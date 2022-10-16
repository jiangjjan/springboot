package demo.lamb;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.context.annotation.Bean;

import java.util.function.Predicate;

@SpringBootConfiguration
@RequiredArgsConstructor
public class JudgeConfig {



	final DoAction doAction;

	@Bean({"b", "c", "a"})
	public Predicate<String> aJudge() {
		System.out.println("=====================");
		System.out.println(doAction.get());
		System.out.println("=====================");
		return "sss"::equals;
	}

}
