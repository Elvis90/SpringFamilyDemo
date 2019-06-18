package com.elvis.demo.integration;

import com.elvis.demo.model.CoffeeOrder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.stereotype.Component;

/**
 * @author Elvis
 * @create 2019-05-28 14:42
 */
@Component
@Slf4j
public class OrderListener {

    @StreamListener(Barista.FINISHED_ORDERS)
    public void listenFinishedOrders(CoffeeOrder co){
        log.info("完成订单,订单ID为:{}",co.getId());
    }
}
