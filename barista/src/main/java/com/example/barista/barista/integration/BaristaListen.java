package com.example.barista.barista.integration;


import com.example.barista.barista.model.CoffeeOrder;
import com.example.barista.barista.model.OrderState;
import com.example.barista.barista.repository.CoffeeOrderRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

/**
 * @author Elvis
 * @create 2019-05-28 15:43
 */
@Slf4j
@Component
@Transactional
public class BaristaListen {

    @Autowired
    private CoffeeOrderRepository coffeeOrderRepository;
    @Autowired
    private Barista barista;

    @StreamListener(Barista.NEW_ORDERS)
    public void NewOrdersComming(CoffeeOrder co){
        if(co==null){
            log.info("订单不存在,无法制作");
            return;
        }
        log.info("开始制作订单号:{}",co.getId());
        co.setBarista(UUID.randomUUID().toString());
        co.setStatus(OrderState.BREWED);
        //制作完成发送消息 以及更新数据库状态
        barista.finishedOrders().send(MessageBuilder.withPayload(co).build());
       coffeeOrderRepository.save(co);
        log.info("订单号:{}制作完成",co.getId());
    }
}
