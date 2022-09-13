package demo.jdk8;

import demo.mybatis.entity.Record;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.util.*;
import java.util.stream.Collector;
import java.util.stream.Collectors;

@Slf4j
public class Demo {


    @Test // List去重
    public void distinct() {

        List<Record> list = new ArrayList<>();
        list.add(new Record(1L, "name1", null));
        list.add(new Record(1L, "name2", null));
        list.add(new Record(2L, "name2", null));
        list.add(new Record(2L, "name2", null));

        //依赖对象自身的 equals 方法
        List<Record> collect = list.stream().distinct().collect(Collectors.toList());
        System.out.println(collect);

        // 根据指定属性去重
        collect = list.stream().collect(Collectors.collectingAndThen(
                Collectors.toCollection(() -> new TreeSet<>(Comparator.comparing(Record::getId))),
                ArrayList::new));
        log.info("collect :{}", collect);
        log.info("list1 :{}", list);
        collect = list.stream().collect(Collectors.collectingAndThen(
                Collectors.toCollection(() -> new TreeSet<>(Comparator.comparing(Record::getName))),
                ArrayList::new));
        System.out.println(collect);

    }

    @Test
    public void toMap() {

        // value 不能为null, key不能重复
        List<Record> list1 = new ArrayList<>();
        list1.add(new Record(1L, "name1", null));
        list1.add(new Record(2L, "name2", null));
        list1.add(new Record(3L, "name3", null));
        Map<Long, String> collect1 = list1.stream().collect(Collectors.toMap(Record::getId, Record::getName));
        System.out.println(collect1);

        // value 不能为null, key能重复 key 具体映射的值由 第三个参数决定
        list1.add(new Record(3L, "name4", null));
        Map<Long, String> collect3 = list1.stream().collect(Collectors.toMap(Record::getId, Record::getName, (a, b) -> b));
        System.out.println(collect3);

        // 允许value 为null ,key 重复
        List<Record> list2 = new ArrayList<>();
        list2.add(new Record(1L, "name1", null));
        list2.add(new Record(2L, null, null));
        list2.add(new Record(3L, "name3", null));
        list2.add(new Record(1L, "name4", null));
        Map<Long, String> collect2 = list2.stream().collect(Collector.of(HashMap::new,
                (map, element) -> map.putIfAbsent(element.getId(), element.getName()),
                (a, b) -> a, //这个函数不会参与计算
                Collector.Characteristics.IDENTITY_FINISH));
        System.out.println(collect2);

    }

    @Test
    public void mergeMap() {

        List<Map<String, Map<String, String>>> list = new ArrayList<>();
        Map<String, Map<String, String>> _1025 = new HashMap<>();
        list.add(_1025);
        Map<String, String> _1025Value = new HashMap<>();
        _1025Value.put("_1025aKey", "_1025aValue");
        _1025Value.put("_1025bKey", "_1025bValue");
        _1025Value.put("_1025cKey", "vcValue");
        _1025.put("1025", _1025Value);
        log.info("1025 :{}", _1025);

        Map<String, Map<String, String>> _1026 = new HashMap<>();
        list.add(_1026);
        Map<String, String> _1026Value = new HashMap<>();
        _1026Value.put("_1026aKey", "_1026aValue");
        _1026Value.put("_1026bKey", "_1026bValue");
        _1026Value.put("_1026cKey", "_1026cValue");
        _1026.put("1026", _1026Value);
        log.info("1026 :{}", _1026);


        Map<String, Map<String, String>> _1025Repeat = new HashMap<>();
        list.add(_1025Repeat);
        Map<String, String> _1025RepeatValue = new HashMap<>();
        _1025RepeatValue.put("_1025aKey", "_1025RepeatValue");
        _1025Repeat.put("1025", _1025RepeatValue);
        log.info("_1025Repeat :{}", _1025Repeat);

        //\值可以为null
        Map<String, Map<String, String>> collect = list.stream().map(Map::entrySet)
                .flatMap(Set::stream).collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (a, b) -> {
            log.info("a :{}", a);
            log.info("b :{}", b);
            b.forEach(a::putIfAbsent);
            log.info("b :{}", b);

            return a;
        }));
        log.info("collect :{}", collect);

    }
}
