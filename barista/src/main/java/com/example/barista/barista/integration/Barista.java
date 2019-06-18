package com.example.barista.barista.integration;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.SubscribableChannel;

/**
 * @author Elvis
 * @create 2019-05-28 14:41
 */
public interface Barista {
    String NEW_ORDERS = "newOrders";
    String FINISHED_ORDERS = "finishedOrders";

    @Input(value = NEW_ORDERS)
    SubscribableChannel newOrders();

    @Output(value = FINISHED_ORDERS)
    MessageChannel finishedOrders();
}
