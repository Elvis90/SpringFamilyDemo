package com.example.barista.barista;


import com.example.barista.barista.integration.Barista;
import com.example.barista.barista.repository.CoffeeOrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.stream.annotation.EnableBinding;

@SpringBootApplication
@EnableBinding(Barista.class)
public class BaristaApplication {

    @Autowired
    CoffeeOrderRepository coffeeOrderRepository;
    public static void main(String[] args) {
        SpringApplication.run(BaristaApplication.class, args);
    }


}
