package demo.config;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.function.BiConsumer;
import java.util.stream.Collectors;

@Component
@ConfigurationProperties(prefix = "iteminfoconfig")
@Data
@Slf4j
public class ItemWrapper implements InitializingBean {

    List<ItemInfo> itemInfos;


    public static Map<String, Map<String, String>> itemMap;

    @Override
    public void afterPropertiesSet() {
        log.info("init itemMap");
        itemMap = Optional.ofNullable(itemInfos).orElse(new ArrayList<>()).stream().map(e -> {
            Map<String, Map<String, String>> res = new HashMap<>();
            Map<String, String> collect = e.items.stream()
                    .collect(Collectors.toMap(Item::getName, Item::getItemCode, (a, b) -> b));
            res.put(e.getOrgCode(), collect);
            return res;
        }).map(Map::entrySet).flatMap(Set::stream).distinct().collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (a, b) -> {
            a.forEach(b::putIfAbsent);
            return b;
        }));
        /**
         * <pre>

         itemMap = Optional.ofNullable(itemInfos).orElse(new ArrayList<>()).stream().map(e -> {

         Map<String, Map<String, String>> res = new HashMap<>();

         Map<String, String> collect = e.items.stream().collect(Collector.of(HashMap::new,
         (map, element) -> {
         String k = element.getName();
         String v = element.getItemCode();
         map.putIfAbsent(k, v);
         },
         (a, b) -> a,
         Collector.Characteristics.IDENTITY_FINISH
         ));
         res.put(e.orgCode, collect);
         return res;
         }).map(Map::entrySet).flatMap(Set::stream).distinct().collect(Collector.of(HashMap::new,
         (map, entry) -> {
         String k = entry.getKey();
         Map<String, String> v = entry.getValue();
         Map<String, String> stringStringMap = map.putIfAbsent(k, v);
         if (stringStringMap != null) {
         v.forEach(stringStringMap::putIfAbsent);
         }
         }, null, null));
         */
    }

    public BiConsumer<Map<String, String>, Item> accumulator() {
        return (map, element) -> {
            String k = element.getName();
            String v = element.getItemCode();
            map.putIfAbsent(k, v);
        };
    }

    @Data
    public static class ItemInfo {
        String orgCode;
        List<Item> items;
    }

    @Data
    public static class Item {
        String name;
        String itemCode;
    }


}
