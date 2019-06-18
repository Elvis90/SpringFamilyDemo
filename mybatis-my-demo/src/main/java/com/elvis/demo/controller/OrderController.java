package com.elvis.demo.controller;

import com.elvis.demo.handle.StatusEnumTypeHandler;
import com.elvis.demo.model.Coffee;
import com.elvis.demo.model.CoffeeOrder;
import com.elvis.demo.model.OrderState;
import com.elvis.demo.service.CoffeeOrderService;
import com.elvis.demo.service.CoffeeService;
import com.elvis.demo.utils.R;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("order")
@Slf4j
public class OrderController {

	@Autowired
	CoffeeOrderService cos;
	@Autowired
	CoffeeService cs;
	
	//创建一个订单
	@PostMapping("add")
	@ResponseBody
	public Object create(@RequestParam("customer") String customer,@RequestParam("coffeeIdArray") String coffeeIdArray) {
		log.info("customer:{};coffeeArray{}",customer,coffeeIdArray);
		if(StringUtils.isBlank(customer) || StringUtils.isBlank(coffeeIdArray)) {
			return new ResponseEntity<R>(R.error(400, "参数不能为空"),HttpStatus.BAD_REQUEST);
		}
		try {
			String[] idsarray = coffeeIdArray.split(",");
			List<Long> idslist = new ArrayList<>();
			for (int i = 0; i <idsarray.length ; i++) {

				idslist.add(StringUtils.isBlank(idsarray[i])?0l:Long.valueOf(idsarray[i]));
			}
			//查询咖啡列表
			List<Coffee> coffeelist = cs.getCoffeeByIds(idslist);
			if(coffeelist==null){
				return new ResponseEntity<R>(R.error(400, "咖啡不存在"),HttpStatus.BAD_REQUEST);
			}
			CoffeeOrder create = cos.create(customer, coffeelist);
			return new ResponseEntity<R>(R.data(create),HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<R>(R.error(500, e.getMessage()),HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
	}

	//更新咖啡状态
	@PostMapping("update")
	public Object update(@RequestParam(value = "orderid") Long orderId,@RequestParam(value = "status") Integer status){
		OrderState orderState = new StatusEnumTypeHandler().valueOf(status);
		return cos.updateStatus(orderState,orderId);
	}
	//查询订单
	@GetMapping("get")
	public Object query(@RequestParam(value = "orderid",required = false) Long orderId,@RequestParam(value = "customs",required = false) String customs){
		List<CoffeeOrder> data=null;
		if(orderId!=null){
			//查询对应ID订单
			 data= cos.getOrderById(orderId);
		}else if(StringUtils.isNotBlank(customs)) {
			//查询顾客订单
			data = cos.getOrderByCustomer(customs);
		}
		return new ResponseEntity<R>(R.data(data),HttpStatus.OK);
	}
	
}
