package security.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonValue;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

import java.io.IOException;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class Result<R> {

	@JsonSerialize(using=HttpStatusSer.class)
	HttpStatus code = HttpStatus.OK;

	R data;

	public Result(R data){
		this.data=data;
	}

	public  static <R>  Result<R> success(R data){
		return new Result<>(data);
	}

	static class HttpStatusSer extends JsonSerializer<HttpStatus>{

		@Override
		public void serialize(HttpStatus value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
			gen.writeNumber(value.value());
		}
	}
}
