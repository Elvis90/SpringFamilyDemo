package com.elvis.demo.integration;

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

    @Input(value = FINISHED_ORDERS)
    SubscribableChannel finishedOrders();

    @Output(value = NEW_ORDERS)
    MessageChannel newOrders();
}
