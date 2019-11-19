package com.elvis.demo;

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
}
