package security.config;

import com.google.code.kaptcha.text.impl.DefaultTextCreator;

import java.util.Random;

public class KaptchaTextCreator extends DefaultTextCreator {

	private static final String[] NUMBER = "0,1,2,3,4,5,6,7,8,9,10".split(",");

	@Override
	public String getText() {
		int result = 0;//结果
		Random random = new Random();
		int x = random.nextInt(10);
		int y = random.nextInt(10);
		StringBuilder chinese = new StringBuilder();
		int randomOpp = (int) random.nextInt(4);
		//判断结果生成加减乘除
		switch (randomOpp) {
			case 0:
				result = x * y;
				chinese.append(NUMBER[x]);
				chinese.append("*");
				chinese.append(NUMBER[y]);
				break;
			case 1:
				if (x!= 0 && y % x == 0) {
					result = y / x;
					chinese.append(NUMBER[y]);
					chinese.append("/");
					chinese.append(NUMBER[x]);
				} else {
					result = x + y;
					chinese.append(NUMBER[x]);
					chinese.append("+");
					chinese.append(NUMBER[y]);
				}
				break;
			case 2:
				if (x >= y) {
					result = x - y;
					chinese.append(NUMBER[x]);
					chinese.append("-");
					chinese.append(NUMBER[y]);
				} else {
					result = y - x;
					chinese.append(NUMBER[y]);
					chinese.append("-");
					chinese.append(NUMBER[x]);
				}
				break;
			default:
				result = x + y;
				chinese.append(NUMBER[x]);
				chinese.append("+");
				chinese.append(NUMBER[y]);
		}
		//拼接结果返回
		chinese.append("=?").append(result);
		return chinese.toString();
	}
}