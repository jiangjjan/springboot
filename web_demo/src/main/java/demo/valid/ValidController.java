package demo.valid;

import demo.mybatis.entity.User;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Delegate;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@RestController("valid")
public class ValidController {

	@PostMapping("test")
	public void test(@Validated @RequestBody ValidationList<User> users){

		System.out.println(users);
	}

	@PostMapping("user")
	public void testJson(@Validated @RequestBody User user){

		System.out.println(user);
	}


	public static class ValidationList<E> implements List<E> {

		@Delegate // @Delegate是lombok注解 ,1.18.6以上版本可支持
		@Valid // 一定要加@Valid注解
		@Getter
		final List<E> list = new ArrayList<>();

		// 一定要记得重写toString方法
		@Override
		public String toString() {
			return list.toString();
		}
	}

}
