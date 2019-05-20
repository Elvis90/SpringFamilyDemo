package com.elvis.demo.customerserver.controller;

import com.elvis.demo.customerserver.feigninterface.CoffeeService;
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
public class CustomerController {

    @Autowired
    CoffeeService cs;
    @GetMapping(name = "/coffee/get")
    @ResponseBody
    public Object get(@RequestParam("name") String name){
        return cs.getCoffee(name);
    }
}
