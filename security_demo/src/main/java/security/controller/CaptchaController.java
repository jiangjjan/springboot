package security.controller;

import com.google.code.kaptcha.Producer;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.time.Duration;

@RestController
@RequiredArgsConstructor
@Slf4j
public class CaptchaController {

	final RedisTemplate<String, Object> redisTemplate;
	final Producer producer;

	/**
	 * 获取验证码图片.前端需要回传的值, 会根据sessionId ->value 放进redis
	 * captcha url配置在白名单里面
	 */
	@GetMapping("captcha")
	public void getCaptcha(HttpServletRequest request, HttpServletResponse response) throws IOException {

		//设置内容类型
		response.setContentType("image/jpeg");
		//创建验证码文本
		String capText = producer.createText();
		log.info("capTxt :{}",capText);
		String txt = capText.substring(0, capText.lastIndexOf("?")+1);
		String code = capText.substring(capText.lastIndexOf("?"));
		redisTemplate.opsForValue().set(request.getRequestedSessionId(), code, Duration.ofMinutes(1));
		BufferedImage bi = producer.createImage(txt);//获取响应输出流
		ServletOutputStream out = response.getOutputStream();//将图片验证码数据写到响应输出流
		ImageIO.write(bi, "jpg", out);//推送并关闭响应输出流
		out.flush();
	}


}
