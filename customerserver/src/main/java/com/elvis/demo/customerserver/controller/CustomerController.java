package com.elvis.demo.customerserver.controller;

import com.elvis.demo.customerserver.feigninterface.CoffeeService;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Elvis
 * @create 2019-05-17 16:29
 */
@RestController
@Slf4j
public class CustomerController {

    @Autowired
    CoffeeService cs;
    @GetMapping(name = "/coffee/get")
    @ResponseBody
    public Object get(@RequestParam("name") String name){
        return cs.getCoffee(name);
    }

    @GetMapping("hystrix")
    @HystrixCommand(fallbackMethod = "fallbackMethod")
    public Object hystrix(@RequestParam("a") int a) throws Exception {
        log.info("进入方法hystrix");

            if(a==1) {
                throw new Exception("发生异常");
            }

        return "hystrix完成 a="+a;
    }

    //需要跟原方法一样或者兼容的参数以及相同的返回值
    public Object fallbackMethod(int a){
        log.warn("进入fallbackMethod");
        return "hystrix完成熔断 a="+a;
    }
}
