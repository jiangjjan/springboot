package security.config;

import com.google.code.kaptcha.impl.DefaultKaptcha;
import com.google.code.kaptcha.text.impl.DefaultTextCreator;
import com.google.code.kaptcha.util.Config;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Properties;
import java.util.Random;

import static com.google.code.kaptcha.Constants.*;

@Configuration
public class CaptchaConfig {
	@Bean(name = "captchaProducerMath")
	public DefaultKaptcha getKaptchaBeanMath() {
		DefaultKaptcha defaultKaptcha = new DefaultKaptcha();
		Properties properties = new Properties();
		// 图片边框
		properties.setProperty(KAPTCHA_BORDER, "yes");
		// 边框颜色
		properties.setProperty(KAPTCHA_BORDER_COLOR, "105,179,90");
		// 文本颜色
		properties.setProperty(KAPTCHA_TEXTPRODUCER_FONT_COLOR, "black");
		// 图片宽度
		properties.setProperty(KAPTCHA_IMAGE_WIDTH, "130");
		// 图片高度
		properties.setProperty(KAPTCHA_IMAGE_HEIGHT, "32");
		// 文本字符大小
		properties.setProperty(KAPTCHA_TEXTPRODUCER_FONT_SIZE, "28");
		// KAPTCHA_SESSION_KEY
		properties.setProperty(KAPTCHA_SESSION_CONFIG_KEY, "KAPTCHA_SESSION_KEY");
		// 验证码文本生成器
		properties.setProperty(KAPTCHA_TEXTPRODUCER_IMPL, "security.config.KaptchaTextCreator");
		// 文本字符间距
		properties.setProperty(KAPTCHA_TEXTPRODUCER_CHAR_SPACE, "3");
		// 文本字符长度
		properties.setProperty(KAPTCHA_TEXTPRODUCER_CHAR_LENGTH, "6");
		// 文本字体样式
		properties.setProperty(KAPTCHA_TEXTPRODUCER_FONT_NAMES, "Arial,Courier");
		// 干扰颜色
		properties.setProperty(KAPTCHA_NOISE_COLOR, "white");
		// 干扰实现类
		properties.setProperty(KAPTCHA_NOISE_IMPL, "com.google.code.kaptcha.impl.NoNoise");
		// 图片样式
		properties.setProperty(KAPTCHA_OBSCURIFICATOR_IMPL, "com.google.code.kaptcha.impl.ShadowGimpy");
		Config config = new Config(properties);
		defaultKaptcha.setConfig(config);
		return defaultKaptcha;
	}


}