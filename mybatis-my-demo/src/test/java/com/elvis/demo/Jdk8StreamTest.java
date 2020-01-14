package com.elvis.demo;

import org.junit.Test;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @author Elvis
 * @create 2019-11-14 10:39
 */
public class Jdk8StreamTest {
    private List<Employee> employeeList = Arrays.asList(
            new Employee("张三", 3333.33, 30, Employee.Status.FREE, "酱油"),
            new Employee("李四", 4444.44, 31, Employee.Status.FREE, "酱油"),
            new Employee("王五", 5555.55, 28, Employee.Status.VACATION, "醋"),
            new Employee("赵六", 6666.66, 55, Employee.Status.BUSY, "醋"),
            new Employee("田七", 7777.77, 30, Employee.Status.FREE, "醋")
    );

    @Test
    public void TestStream(){
        List<String> stringList = Arrays.asList("a", "b", "c", "d", "e");
        stringList.stream().map(i->i+"!").filter(i->!i.equals("e!")).peek(System.out::println).collect(Collectors.toList());

        //按部门分组 然后按照年龄判断属于哪个阶段
        Map<String, Map<String, List<Employee>>> map = employeeList.stream().collect(Collectors.groupingBy(Employee::getDep, Collectors.groupingBy(e -> {
            if(e.getAge()<35){
                return "青年";
            }else if(e.getAge()<50){
                return "中年";
            }else{
                return "老年";
            }
        })));
        System.out.println(map.get("醋").get("老年"));

    }
    @Test
    public void test1(){
        /**
        给定一个数字列表,如何返回一个由每个数的平方构成的列表
        eg:给定[1,2,3,4,5,6] 返回 [1,4,9,16,25,36]
         */
        Integer[] array = {1,2,3,4,5,6};
        Arrays.stream(array).map(a -> a * a).collect(Collectors.toList()).forEach(System.out::println);


        /**
         * 统计employeeList中有多少个对象 使用map-reduce模式
         */
        Optional<Integer> reduce = employeeList.stream().map(m -> 1).reduce((x,y)->Integer.sum(x,y));
        System.out.println(reduce.get());

        /**
         * 统计工资大于5000的人的姓名
         */
        employeeList.stream().filter(e->e.getSalary()>5000).map(Employee::getName).forEach(System.out::println);

        /**
         * 统计总工资是多少
         */
        Optional<Double> r = employeeList.stream().map(Employee::getSalary).reduce(Double::sum);
        System.out.println("总工资为:"+r.get());
    }
}
