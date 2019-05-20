package com.elvis.demo.controller;

import com.elvis.demo.controller.request.CoffeeRequest;
import com.elvis.demo.model.Coffee;
import com.elvis.demo.service.CoffeeService;
import com.elvis.demo.utils.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("coffee")
@Slf4j
public class CoffeeController {

	@Autowired
	CoffeeService cs;
	@GetMapping("get")
	@ResponseBody
	public ResponseEntity<R> getCoffee(@RequestParam("coffeename") String coffeename) {
		//查询咖啡
		List<Coffee> list = cs.getCoffeeByName(coffeename);
		return new ResponseEntity<R>(R.data(list), HttpStatus.OK);
	}

	//获取咖啡菜单
	@GetMapping("menu")
	@ResponseBody
	public ResponseEntity<R> getMenu() {
		List<Coffee> menulist = cs.getMenu();
		return new ResponseEntity<R>(R.data(menulist),HttpStatus.OK);
	}

	@PostMapping("add")
	public ResponseEntity<R> addCoffee(@Valid CoffeeRequest cr,BindingResult restult){
		ResponseEntity<R> res =null;
//		if(restult.hasErrors()) {
//			log.error(restult.toString());
//			res=new ResponseEntity<R>(R.error("参数错误"), HttpStatus.BAD_REQUEST);
//			return res;
//		}
		//新增咖啡
		Coffee cf = cs.creatCoffee(cr.getName(),cr.getPrice());
		res= new ResponseEntity<R>(R.data(cf),HttpStatus.OK);
		return res;
	}
	
}
