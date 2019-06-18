package com.elvis.demo.customerserver.service;

import com.elvis.demo.customerserver.feigninterface.CoffeeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
/**
 * @author Elvis
 * @create 2019-05-27 11:33
 */
@Component
@Slf4j
public class FallbackCoffeeService implements CoffeeService {
    @Override
    public Object getCoffee(String coffeename) {
        log.warn("FallbackCoffeeService---getCoffee---coffeeName="+coffeename);
        return null;
    }
}
