package demo.web;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.json.JsonMapper;
import lombok.AllArgsConstructor;
import lombok.Data;
import okhttp3.ConnectionPool;
import okhttp3.OkHttpClient;
import org.apache.commons.collections4.CollectionUtils;
import org.junit.jupiter.api.Test;
import org.springframework.http.*;
import org.springframework.http.client.OkHttp3ClientHttpRequestFactory;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class RestTemplateDemo {

	static OkHttpClient client = getUnsafeOkHttpClient()
			.connectionPool(new ConnectionPool(500, 300, TimeUnit.MINUTES))
			.connectTimeout(5, TimeUnit.SECONDS)
			.readTimeout(20, TimeUnit.SECONDS)
			.build();
	public static RestTemplate restTemplate = new RestTemplate();
	static JsonMapper json = new JsonMapper();

	static {

		json.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		json.disable(SerializationFeature.FAIL_ON_EMPTY_BEANS);
		MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter(json);

		converter.setSupportedMediaTypes(List.of(MediaType.APPLICATION_JSON, MediaType.TEXT_PLAIN));
		int index = 0;
		for (int i = 0; i < restTemplate.getMessageConverters().size(); i++) {
			HttpMessageConverter<?> httpMessageConverter = restTemplate.getMessageConverters().get(i);
			if (httpMessageConverter instanceof MappingJackson2HttpMessageConverter) {
				index = i;
				break;
			}
		}

		restTemplate.setRequestFactory(new OkHttp3ClientHttpRequestFactory(client));
		restTemplate.getMessageConverters().add(index, converter);

	}


	public static OkHttpClient.Builder getUnsafeOkHttpClient() {

		try {

			final TrustManager[] trustAllCerts = new TrustManager[]{

					new X509TrustManager() {

						@Override

						public void checkClientTrusted(java.security.cert.X509Certificate[] chain, String authType) {

						}

						@Override

						public void checkServerTrusted(java.security.cert.X509Certificate[] chain, String authType) {

						}

						@Override

						public java.security.cert.X509Certificate[] getAcceptedIssuers() {

							return new java.security.cert.X509Certificate[]{};

						}

					}

			};

			final SSLContext sslContext = SSLContext.getInstance("SSL");

			sslContext.init(null, trustAllCerts, new java.security.SecureRandom());

			final javax.net.ssl.SSLSocketFactory sslSocketFactory = sslContext.getSocketFactory();

			OkHttpClient.Builder builder = new OkHttpClient.Builder();

			builder.sslSocketFactory(sslSocketFactory, (X509TrustManager) trustAllCerts[0]);
			builder.hostnameVerifier((hostname, session) -> true);

			return builder;

		} catch (Exception e) {

			throw new RuntimeException(e);

		}

	}

	@Test
	public void ss() {

		List<UseNull> list = new ArrayList<>(List.of(new UseNull("za"), new UseNull("b"), new UseNull(null)));
		list.sort(Comparator.comparing(UseNull::getName,Comparator.nullsLast(String::compareTo)));
		System.out.println(list);
	}

	@Data
	@AllArgsConstructor
	static class UseNull{
		String name;
	}


	@Test
	public void login() {

//      String login = "https://limscloud-test.adicon.com.cn:1090/api-auth/oauth/token";
//        MultiValueMap<String,String> req = new LinkedMultiValueMap<>();
//        req.add("client_id","username");
//        req.add("client_secret","username");
//        req.add("scope","all");
//        req.add("username","lpy");
//        req.add("password","92f37119cd401e3f2a7f9ccdbe4cf429");
//        req.add("grant_type","password");
//
//
//        String s = restTemplate.postForObject(login, req, String.class);
//        System.out.println(s);

		String url = "https://limscloud-test.adicon.com.cn:1090/api-test/biochemicalMaterial/execSyncK3";
		HttpHeaders headers = new HttpHeaders();
		headers.add("X-Auth-Token", "eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCIsImtpZCI6IjYxNjU3NzczOTk0NTI3NjYwMSJ9.eyJ1c2VyX25hbWUiOiJscHkiLCJyb2xlcyI6W3sib3JnQ29kZSI6IjEwMjUiLCJyb2xlQ29kZSI6IkFEMDA1In0seyJvcmdDb2RlIjoiMTAyNSIsInJvbGVDb2RlIjoiYWxsIn1dLCJzY29wZSI6WyJhbGwiXSwiZGlzcGxheV9uYW1lIjoi5p2O6bmP6IuxIiwiZXhwIjoxNjYyMDk3NTY1LCJqdGkiOiJiZmQ2ZmQyZC0zNTkyLTQ3NWYtODYyNi02ZjgwYThiZTg1N2MiLCJjbGllbnRfaWQiOiJ1c2VybmFtZSJ9.MioTNaZeufBHAZid5J-rQiiOKQerlroFS9Wk5qW8ofP5i5COfCoEGBK0FaYiKN3CwQFQUYsdHffenyNY4sv0MvXeU8Z2Gzd5Zcg4kAJ2_Fk-GKqZEtld6ut_ODhydjE2V2ZokJWFolEDHV8popLiQRsKGVRINZ6ejNKAZw__Ph8");
		headers.add("X-Lab-Group-Code", "0002");
		headers.add("X-Lego-Version", "1.0.0");
		headers.add("X-Lego-environment", "legotest");
		headers.add("X-Lego-environment", "legotest");
		headers.add("X-Org-Code", "1023");

		HttpEntity<MultiValueMap<String, String>> req = new HttpEntity<>(null, headers);

		ResponseEntity<String> exchange = restTemplate.exchange(url, HttpMethod.GET, req, String.class);
		System.out.println(exchange);

	}

	@Test
	public void uat() {
		String url = "https://lego-uat.adicon.com.cn:31222/api-test/biochemicalMaterial/execSyncK3";
		HttpHeaders headers = new HttpHeaders();
		String token = "eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCIsImtpZCI6IjYxNjU3NzczOTk0NTI3NjYwMSJ9.eyJkaXNwbGF5X25hbWUiOiLmnY7lqJ8iLCJleHAiOjE2NjIxMDAyNzAsInVzZXJfbmFtZSI6Imp1YW4ubGkwNSIsImp0aSI6ImRiNzdjOTI1LThhZmEtNGRmNC04YmU1LTE0NjYxM2NjNzYxNSIsImNsaWVudF9pZCI6InVzZXJuYW1lIiwic2NvcGUiOlsiYWxsIl19.IJPtvt5uEJdB6XCfjR4RV3ea_MTiRzuARQDmBa4NoGCKx_rIUihMed8FUnlnKIBRD0lmaq-6hsD87OQwAJmq5p_kKnS0J2m_6qDePQKmBcreAPxH-a4NW0sw86yYzZaZXROLKG8rqF7mU2qM-CTM8sQL42Y0XEaDhubjwH2wIPo";
		headers.add("X-Auth-Token", token);
		headers.add("X-Lab-Group-Code", "0002");
		headers.add("X-Lego-Version", "1.0.0");
		headers.add("X-Lego-environment", "legouat");
		headers.add("X-Org-Code", "1023");

		HttpEntity<MultiValueMap<String, String>> req = new HttpEntity<>(null, headers);

		ResponseEntity<String> exchange = restTemplate.exchange(url, HttpMethod.GET, req, String.class);
		System.out.println(exchange);
	}


}
