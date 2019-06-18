package com.elvis.demo.customerserver.feigninterface;

import com.elvis.demo.customerserver.service.FallbackCoffeeService;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author Elvis
 * @create 2019-05-17 14:58
 */
@FeignClient(name="waiter-server",contextId = "coffee",path = "coffee",fallback = FallbackCoffeeService.class)
//接口上最好不要加RequestMapping
public interface CoffeeService {
    @GetMapping("get")
    Object getCoffee(@RequestParam("coffeename") String coffeename);

}
