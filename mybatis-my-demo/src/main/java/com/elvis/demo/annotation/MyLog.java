package com.elvis.demo.annotation;

import java.lang.annotation.*;

/**
 * @author Elvis
 * @create 2019-06-20 15:58
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
@Documented
public @interface MyLog {
    /**
     * 日志描述信息
     *
     * @return
     */
    String title() default "";
}
