package com.elvis.demo;

import com.elvis.demo.model.Coffee;
import com.elvis.demo.model.CoffeeOrder;
import com.elvis.demo.model.OrderStatus;
import com.elvis.demo.repository.CoffeeOrderRepository;
import com.elvis.demo.repository.CoffeeRepository;
import lombok.extern.slf4j.Slf4j;
import org.joda.money.CurrencyUnit;
import org.joda.money.Money;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;

@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class JpaComplexDemoApplicationTests {

	@Autowired
	CoffeeRepository coffeeRepository;
	@Autowired
	CoffeeOrderRepository coffeeOrderRepository;
	@Test
	public void contextLoads() {
		//创建咖啡菜单
		Coffee kbqn = Coffee.builder().name("卡布奇诺").price(Money.of(CurrencyUnit.of("CNY"),10)).build();
		Coffee msCoffee = Coffee.builder().name("美式大师咖啡").price(Money.of(CurrencyUnit.of("CNY"),20)).build();
		coffeeRepository.save(kbqn);
		coffeeRepository.save(msCoffee);
		//创建订单
		CoffeeOrder order = CoffeeOrder.builder().customer("周杰伦").status(OrderStatus.INIT).coffeelist(Arrays.asList(kbqn,msCoffee)).build();
		coffeeOrderRepository.save(order);
		log.info("order:{}",order);
		
	}

}
