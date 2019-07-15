package com.elvis.demo.controller;

import com.elvis.demo.annotation.MyLog;
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

import javax.servlet.http.HttpServletRequest;
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
	@GetMapping("getmenu")
	@ResponseBody
	public ResponseEntity<R> getMenu() {
		List<Coffee> menulist = cs.getMenu();
		return new ResponseEntity<R>(R.data(menulist),HttpStatus.OK);
	}

	@PostMapping("add")
	@MyLog(title = "添加咖啡操作")
	//@RequestHeader(value = "username") String username,
	public ResponseEntity<R> addCoffee(@Valid CoffeeRequest cr, HttpServletRequest request,BindingResult restult) throws Exception{
		ResponseEntity<R> res =null;
		if(restult.hasErrors()) {
			log.error(restult.toString());
			throw new Exception("参数异常");
		}
		//新增咖啡
	//	try {

			Coffee cf = cs.creatCoffee(cr.getName(),cr.getPrice());
			log.info("价格是:{}",cf.getPrice());
			res= new ResponseEntity<R>(R.data(cf),HttpStatus.OK);
		//}catch (Exception e){
		//	log.error("新增咖啡失败"+e.getMessage());
		//	res= new ResponseEntity<R>(R.error(),HttpStatus.INTERNAL_SERVER_ERROR);
	//	}

		return res;
	}
	
}
