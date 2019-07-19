package com.elvis.demo.service;

import com.elvis.demo.mapper.CoffeeMapper;
import com.elvis.demo.model.Coffee;
import com.elvis.demo.model.CoffeeExample;
import lombok.extern.slf4j.Slf4j;
import org.joda.money.Money;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
@CacheConfig(cacheNames="coffee")
@Slf4j
public class CoffeeService {

	@Autowired
	private CoffeeMapper coffeeMapper;
	
	//创建一个咖啡
	public Coffee creatCoffee(String name,Money price) {
		Coffee cof = new Coffee().withName(name).withPrice(price).withCreateTime(new Date());
	    coffeeMapper.insertSelective(cof);
		return cof;
	}
	
	//获取菜单
	@Cacheable(cacheNames="coffee")
	public List<Coffee> getMenu(){
		CoffeeExample ce = new CoffeeExample();
		return coffeeMapper.selectByExample(ce);
	}
	
	//根据名称获取咖啡
	public List<Coffee> getCoffeeByName(String name) {
		CoffeeExample ce = new CoffeeExample();
		ce.createCriteria().andNameEqualTo(name);
		return  coffeeMapper.selectByExample(ce);
	}

	//根据id数组获取咖啡
	public List<Coffee> getCoffeeByIds(List<Long> ids){
		CoffeeExample ce = new CoffeeExample();
		ce.createCriteria().andIdIn(ids);
		return coffeeMapper.selectByExample(ce);
	}
	//重载菜单(清除缓存)
	@CacheEvict(cacheNames="coffee")
	public void reload() {
		
	}
	
}
