package com.elvis.demo;

import com.elvis.demo.function.MyFunction;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Elvis
 * @create 2019-11-14 10:39
 */
public class Jdk8StreamTest {

    @Test
    public void TestStream(){
        List<String> stringList = Arrays.asList("a", "b", "c", "d", "e");
        stringList.stream().map(i->i+"!").filter(i->!i.equals("e!")).peek(i-> System.out.println(i)).collect(Collectors.toList());
    }

    @Test
    public void testLambad(){
        Long value = handle(100l, 200l, (x, y) -> x * y);
        System.out.println(value);
    }

    private Long handle(Long t1, Long t2, MyFunction<Long,Long> f){
        return f.getValue(t1,t2);
    }
}
