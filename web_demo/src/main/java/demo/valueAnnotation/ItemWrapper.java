package demo.valueAnnotation;

import lombok.Data;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.boot.context.properties.ConfigurationProperties;
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

	@Getter
	Map<String, Map<ItemName, String>> itemInfos;


	public static Map<String, Map<String, String>> itemMap;

	@Override
	public void afterPropertiesSet() {

//		log.info("init itemMap");
//		itemMap = Optional.ofNullable(itemInfos).orElse(new ArrayList<>()).stream().map(e -> {
//			Map<String, Map<String, String>> res = new HashMap<>();
//			Map<String, String> collect = e.items.stream()
//					.collect(Collectors.toMap(Item::getName, Item::getItemCode,
//							(a, b) -> b));
//			res.put(e.getOrgCode(), collect);
//			return res;
//		}).map(Map::entrySet).flatMap(Set::stream).distinct().collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue,
//				(a, b) -> {
//					a.forEach(b::putIfAbsent);
//					return b;
//				}));

	}

	@Data
	public static class ItemInfo {
		String orgCode;
		Map<ItemName, String> items;
	}

	@Data
	public static class Item {
		String name;
		String itemCode;
	}


	public static List<String> get(String orgCode, List<ItemName> itemNames) {

		Objects.requireNonNull(itemNames, "itemName can not be null");
		List<String> res = new ArrayList<>(itemNames.size());
		for (ItemName item : itemNames) {
			res.add(get(orgCode, item));
		}

		return res;
	}

	public static String get(String orgCode, ItemName itemName) {

		Objects.requireNonNull(orgCode, "orgCode can't be null");
		Objects.requireNonNull(itemName, "itemName can't be null");
		Map<String, String> orgInfo = itemMap.get(orgCode);

		if (orgInfo == null) { //没有配置该机构的数据
			log.info("没有配置 {} 项目信息", orgCode);
			return itemName.defaultItemCode;
		}

		String s = orgInfo.get(itemName.name());
		if (s == null) {

			log.info("{} 没有配置 {} 项目对应的Code", orgCode, itemName);
			return itemName.defaultItemCode;
		}
		return s;
	}


}
