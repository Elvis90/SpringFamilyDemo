package com.elvis.demo.function;

/**
 * @author Elvis
 * @create 2020-01-09 11:05
 */
@FunctionalInterface
public interface MyFunction<T,p> {
    p getValue(T t1, T t2);
}
