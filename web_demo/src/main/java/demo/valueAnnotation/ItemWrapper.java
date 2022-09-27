package demo.valueAnnotation;

import lombok.Data;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.Collectors;

/**
 * use iteminfoconfig.yml to config item info,
 */
@Component
@ConfigurationProperties(prefix = "iteminfoconfig")
@Data
@Slf4j
public class ItemWrapper implements InitializingBean {

	public static  Map<String, Map<String, String>>  itemMap;


	Map<String, Map<String, String>> items;

	@Override
	public void afterPropertiesSet()  {
		itemMap=items;
		log.info("itemMap:{}", itemMap);
	}


}
