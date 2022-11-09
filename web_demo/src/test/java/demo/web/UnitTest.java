package demo.web;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class UnitTest {

	@Test
	public void ss() throws IOException {


		List<String> strings = Files.readAllLines(Paths.get("D:/203d4e97-7068-4a8b-9958-c1cb58015f8e.json"));

		Set<String> ss = Collections.synchronizedSet(new HashSet<>());

		strings.parallelStream().forEach(e->{

			ss.add(e.substring(e.indexOf("itemCode")+9,e.lastIndexOf("\"")));


		});
		System.out.println(ss);

	}

}
