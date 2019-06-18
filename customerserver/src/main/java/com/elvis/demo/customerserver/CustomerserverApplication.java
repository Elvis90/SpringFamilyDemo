package com.elvis.demo.customerserver;

import com.elvis.demo.customerserver.feigninterface.CoffeeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients
@EnableCircuitBreaker
@Slf4j
public class CustomerserverApplication{

    @Autowired
    CoffeeService cs;
    public static void main(String[] args) {
        SpringApplication.run(CustomerserverApplication.class, args);
    }

//    @Bean
//    public IRule MyRule(){
//        /* RandomRule为Ribbon中自带规则实现之一，随机规则 */
//        return new RandomRule();
//    }
//    @Override
//    public void run(ApplicationArguments args) throws Exception {
//        Object o = cs.getCoffee("王水");
//        log.info("咖啡信息为:"+o.toString());
//    }
}
