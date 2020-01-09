package com.elvis.demo;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

/**
 * @author Elvis
 * @create 2020-01-09 15:23
 *
 * 内置的四大核心函数式接口
 * Consumer<T> :消费型接口
 *           void accept(T t);
 * Supplier<T> :供给型接口
 *           T get();
 * Function<T,R> :函数型接口
 *           R apply(T t)
 * Predicate<T> :断言型接口
 *           boolean test(T t);
 */
public class TestLambda {
    private String[] userarray ={"周杰伦,35","刘德华,58","Elvis,30"};

    @Test
    public void ConsumerTest(){
        ConsumerMoney(100d,d-> System.out.println(d+2));

        for(String user:userarray){
            printUserInfo(user,s-> System.out.print("姓名:"+s.split(",")[0]+"  "),s1-> System.out.println("年龄:"+s1.split(",")[1]));
        }
    }

    private void ConsumerMoney(Double money, Consumer<Double> con){
        con.accept(money);
    }
    private void printUserInfo(String user,Consumer<String> name,Consumer<String> age){
        name.andThen(age).accept(user);
    }

    @Test
    public void testSupplier(){
        List<Integer> list = Supplier(10, () -> ThreadLocalRandom.current().nextInt(1, 99 + 1));
        for(Integer i : list){
            System.out.println(i);
        }
    }
    //生产num个数字加入集合
    private List<Integer> Supplier(Integer num, Supplier<Integer> sup){
        List<Integer> list =  new ArrayList<>();
        for (int i = 0; i < num; i++) {
            list.add(sup.get());
        }
        return list;
    }

    @Test
    public void testFunction(){
        System.out.println(fun("dsd代收款号888888地块好看sdjsdks3535d6hk",(a) -> a.replaceAll("\\d++","")));
    }
    //替换字符串中的数字
    private String fun(String old, Function<String,String> fun){
        return fun.apply(old);
    }

    @Test
    public void testPredicate(){
        System.out.println(pre(187,(x)->x>200));
    }

    private boolean pre(Integer x, Predicate<Integer> pre){
        return pre.test(x);
    }
}
