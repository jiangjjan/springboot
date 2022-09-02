package demo.jdk8;

import demo.mybatis.entity.Record;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collector;
import java.util.stream.Collectors;

public class Demo {

    @Test
    public void toMap(){

        // value 不能为null, key不能重复
        List<Record> list1 = new ArrayList<>();
        list1.add(new Record(1l,"name1",null));
        list1.add(new Record(2l,"name2",null));
        list1.add(new Record(3l,"name3",null));
        Map<Long, String> collect1 = list1.stream().collect(Collectors.toMap(Record::getId, Record::getName));
        System.out.println(collect1);


        // 允许value 为null ,key 重复
        List<Record> list2 = new ArrayList<>();
        list2.add(new Record(1l,"name1",null));
        list2.add(new Record(2l,null,null));
        list2.add(new Record(3l,"name3",null));
        list2.add(new Record(1l,"name4",null));
        Map<Long, String> collect2 = list2.stream().collect(Collector.of(HashMap::new,
                (map,element)-> map.putIfAbsent(element.getId(),element.getName()),
                (a,b)->a,
                Collector.Characteristics.IDENTITY_FINISH));
        System.out.println(collect2);


    }
}
