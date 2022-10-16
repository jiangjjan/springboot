package security.config;

import org.springframework.http.HttpHeaders;
import org.springframework.security.web.header.HeaderWriter;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

public class HeaderWriterCopy implements HeaderWriter {


	List<String>  headerParam=List.of("X-Param");

	@Override
	public void writeHeaders(HttpServletRequest request, HttpServletResponse response) {
		response.setHeader(HttpHeaders.CONNECTION, "close");

		headerParam.forEach(headName->{
			String header = request.getHeader(headName);
			if (!StringUtils.isEmpty(header))
				response.setHeader(headName, header);
		});

	}

}
