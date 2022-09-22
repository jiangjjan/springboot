package security.config;

import org.springframework.http.HttpHeaders;
import org.springframework.security.web.header.HeaderWriter;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class HeaderWriterCopy implements HeaderWriter {


	String headerParam="X-Param";

	@Override
	public void writeHeaders(HttpServletRequest request, HttpServletResponse response) {
		response.setHeader(HttpHeaders.CONNECTION, "close");
		String header = request.getHeader(headerParam);
		if (!StringUtils.isEmpty(header))
			response.setHeader(headerParam, header);
	}

}
