package com.elvis.demo.service;

import com.elvis.demo.mapper.CoffeeOrderMapper;
import com.elvis.demo.model.Coffee;
import com.elvis.demo.model.CoffeeOrder;
import com.elvis.demo.model.CoffeeOrderExample;
import com.elvis.demo.model.OrderState;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class CoffeeOrderService {

	@Autowired
	private CoffeeOrderMapper com;
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
	public void updateStatus(OrderState os,Long orderId) {
		CoffeeOrderExample coe = new CoffeeOrderExample();
		coe.createCriteria().andIdEqualTo(orderId);
		List<CoffeeOrder> list = com.selectByExample(coe);
		if(list!=null) {
			CoffeeOrder coffeeOrder = list.get(0);
			coffeeOrder.setStatus(os);
			coffeeOrder.setUpdateTime(new Date());
			com.updateByExample(coffeeOrder, coe);
		}
		
	}
}
