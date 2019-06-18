package com.elvis.demo.service;

import com.elvis.demo.integration.Barista;
import com.elvis.demo.mapper.CoffeeOrderMapper;
import com.elvis.demo.model.Coffee;
import com.elvis.demo.model.CoffeeOrder;
import com.elvis.demo.model.CoffeeOrderExample;
import com.elvis.demo.model.OrderState;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
@Slf4j
public class CoffeeOrderService {

	@Autowired
	private CoffeeOrderMapper com;
	@Autowired
	private Barista barista;
	//创建订单
	public CoffeeOrder create(String customer,List<Coffee> coffeelist) {
		CoffeeOrder co = CoffeeOrder.builder().customer(customer).isDelete(0).status(OrderState.INIT).createTime(new Date()).coffeelist(coffeelist).build();
		com.insertSelective(co);
		com.insertCoffeeAndOrderMapping(co);
		return co;
	}
	//顾客姓名查询订单
	public List<CoffeeOrder> getOrderByCustomer(String customer) {
		return com.selectByCustomer(customer);
	}

	//订单ID查询订单
	public List<CoffeeOrder> getOrderById(Long id) {
		CoffeeOrderExample coe = new CoffeeOrderExample();
		coe.createCriteria().andIdEqualTo(id);
		return com.selectByExample(coe);
	}
	//更新订单状态
	public boolean updateStatus(OrderState os,Long orderId) {
		CoffeeOrderExample coe = new CoffeeOrderExample();
		coe.createCriteria().andIdEqualTo(orderId);
		List<CoffeeOrder> list = com.selectByExample(coe);
		if (list != null) {
			CoffeeOrder coffeeOrder = list.get(0);
			//判断状态大小
			if (os.compareTo(coffeeOrder.getStatus()) <= 0) {
				log.warn("状态不能回滚,订单号:{},目前状态为{},待更新状态为{}", orderId, coffeeOrder.getStatus(), os);
				return false;
			}

			if(os==OrderState.PAID){
				//如果状态是已支付,发送消息通知咖啡师制作
				Message<CoffeeOrder> msg = MessageBuilder.withPayload(coffeeOrder).build();
				boolean sendstatus = barista.newOrders().send(msg);
				log.info("通知咖啡师{}",sendstatus==true?"成功":"失败");
			}
			coffeeOrder.setStatus(os);
			coffeeOrder.setUpdateTime(new Date());
			com.updateByExample(coffeeOrder, coe);
		}
		return true;
	}
}
