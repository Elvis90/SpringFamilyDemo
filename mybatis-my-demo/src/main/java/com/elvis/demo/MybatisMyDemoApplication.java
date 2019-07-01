package com.elvis.demo;

import com.elvis.demo.integration.Barista;
import com.elvis.demo.mapper.CoffeeMapper;
import com.elvis.demo.model.Coffee;
import com.elvis.demo.model.CoffeeExample;
import com.elvis.demo.service.CoffeeOrderService;
import com.elvis.demo.service.CoffeeService;
import com.elvis.demo.service.RedisTemplateService;
import lombok.extern.slf4j.Slf4j;
import org.joda.money.CurrencyUnit;
import org.joda.money.Money;
import org.mybatis.generator.api.MyBatisGenerator;
import org.mybatis.generator.config.Configuration;
import org.mybatis.generator.config.xml.ConfigurationParser;
import org.mybatis.generator.internal.DefaultShellCallback;
import org.mybatis.spring.annotation.MapperScan;
import org.redisson.Redisson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

//import com.elvis.demo.model.Coffee;
//import com.elvis.demo.model.CoffeeExample;
//import com.elvis.demo.model.CoffeeExample.Criteria;

@SpringBootApplication
@MapperScan("com.elvis.demo.mapper")
@Slf4j
@EnableCaching(proxyTargetClass=true)
@EnableAspectJAutoProxy
@EnableDiscoveryClient
@EnableRedisHttpSession(maxInactiveIntervalInSeconds = 10)
//当前版本spring redis会在session过期时间上加300S
@EnableBinding(Barista.class)
public class MybatisMyDemoApplication implements CommandLineRunner{

	@Autowired
	CoffeeMapper coffeeMapper;
	@Autowired
	CoffeeService cs;
	@Autowired
	CoffeeOrderService cos;
	@Autowired
	RedisTemplateService rts;
	@Autowired
	Redisson redisson;
	public static void main(String[] args) {
		ConfigurableApplicationContext context = SpringApplication.run(MybatisMyDemoApplication.class, args);

//		ConvertThreadPool.getInstance().execute(new MyThread("abc"));
//		log.info("main线程开始循环");
//		for (int i = 0; i < 10; i++) {
//			try {
//				Thread.sleep(10000);
//				log.info("main线程"+i);
//			}catch (Exception e){
//				e.printStackTrace();
//			}
//		}
	}



	@Override
	public void run(String... args) throws Exception {
	//	generateArtifacts();
//		showmybatis();
		/*//测试缓存
		System.out.println(cs.getMenu().size());
		cs.creatCoffee("雪碧", Money.of(CurrencyUnit.of("CNY"), 18.50));
		for (int i = 0; i < 10; i++) {
			System.out.println(cs.getMenu().size());
		}
		//cs.reload();
		Thread.sleep(11*1000);
		System.out.println(cs.getMenu().size()); */
		
		/*
		List<CoffeeOrder> list = cos.getOrderByCustomer("周杰伦");
		list.forEach(c->System.out.println(c));
		cos.updateStatus(OrderState.TAKEN, list.get(0).getId());*/

		//new Thread(new MsghandleThread()).start();

//		RLock mylock = redisson.getLock("mylock");
//		mylock.lock();
//		for (int i = 0; i < 10; i++) {
//			log.info(i+"");
//		}
//		Thread.sleep(40000);
//		mylock.unlock();
	}

	private void generateArtifacts() throws Exception {
		List<String> warnings = new ArrayList<>();
		ConfigurationParser cp = new ConfigurationParser(warnings);
		Configuration config = cp.parseConfiguration(
				this.getClass().getResourceAsStream("/generatorConfig.xml"));
		DefaultShellCallback callback = new DefaultShellCallback(true);
		MyBatisGenerator myBatisGenerator = new MyBatisGenerator(config, callback, warnings);
		myBatisGenerator.generate(null);
	}
	
	private void showmybatis() {
		Coffee jt = new Coffee().withName("黑糖玛奇朵").withPrice(Money.of(CurrencyUnit.of("CNY"),30.5)).withCreateTime(new Date());
		coffeeMapper.insertSelective(jt);
		CoffeeExample coffeeExample = new CoffeeExample();
		coffeeExample.createCriteria().andPriceBetween(Money.of(CurrencyUnit.of("CNY"),20), Money.of(CurrencyUnit.of("CNY"),40));
		List<Coffee> list = coffeeMapper.selectByExample(coffeeExample);
		list.forEach(o->log.info("res:{}",o));
	}




}
