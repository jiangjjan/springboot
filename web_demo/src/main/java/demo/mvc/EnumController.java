package demo.mvc;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import demo.mybatis.enums.SexEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequestMapping("enums")
public class EnumController {

	@GetMapping("v")
	public Object v() {

		return new Model();
	}

	@Data
	@AllArgsConstructor
	@NoArgsConstructor
	static class Model {

		@JsonSerialize(using =SexEnumSer.class)
		SexEnum sex;

		static class SexEnumSer extends JsonSerializer<SexEnum> {


			@Override
			public void serialize(SexEnum value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
				gen.writeString(value.desc);
			}
		}
	}
}
