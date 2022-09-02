import demo.model.Test;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.stream.Collectors;

public class TestDemo {

    public static void main(String[] args) {
        List<Test> list = new ArrayList<>();

        Test a = new Test();
        a.setName("asda");
        a.setSize(11);
        list.add(a);

        a = new Test();
        a.setName("asda");
        a.setSize(22);
        list.add(a);

        a = new Test();
        a.setName("asda");
        a.setSize(33);
        list.add(a);
        list.add(a);


        Map<String, Test> collect = list.stream().collect(Collectors.toMap(Test::getName, Function.identity(), (aa, b) ->{
            System.out.println("aa" +aa);
            System.out.println("b" +b);
            return aa;


        }));
        System.out.println(collect);

    }



}
