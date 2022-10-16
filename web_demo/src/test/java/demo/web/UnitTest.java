package demo.web;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

public class UnitTest {

	@Test
	public void ss() {

		Map<String,String> aa= new HashMap<>();


		String orDefault = aa.getOrDefault("a", "b");
		System.out.println(orDefault);

	}

	@Data
	@Builder
	@NoArgsConstructor
	@AllArgsConstructor
	static class AA{
		String name;
		Long age;
	}
}
