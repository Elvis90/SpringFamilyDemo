package com.elvis.demo;

import com.elvis.demo.service.TxTestService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.transaction.annotation.EnableTransactionManagement;


/*
 * 
 * 事务测试
 */
@SpringBootApplication
@Slf4j
@EnableTransactionManagement
@ComponentScan(basePackages={"com.elvis.demo"})
public class Demo3Application implements CommandLineRunner{
	 @Autowired
	 protected JdbcTemplate jdbcTemplate;
//	 @Autowired
//	 private TransactionTemplate transactionTemplate;
	 @Autowired
	 private TxTestService txService;
	public static void main(String[] args) {
		SpringApplication.run(Demo3Application.class, args);
	}
	@Override
	public void run(String... args) throws Exception {
		//编程式事务
		/*
		transactionTemplate.execute(new TransactionCallbackWithoutResult() {
			
			@Override
			protected void doInTransactionWithoutResult(TransactionStatus status) {
				log.info("===Transaction begin===="+getcount());
				jdbcTemplate.execute("INSERT INTO table1(id,text) VALUES (2,\"bb\")");
				status.setRollbackOnly();
			log.info("===Transaction time===="+getcount());
			}
		});
		
		log.info("===Transaction end===="+getcount()); 
		*/
		
		//声明式事务
		log.info("==begin==="+getcount());
		txService.insertdata();
		log.info("==insertdata==="+getcount());
		
		try {
			txService.insertdataRollBack();
		} catch (Exception e) {
			// TODO: handle exception
			log.error("exception");
		}
		
		try {
			txService.insertdataRollBack();
		} catch (Exception e) {
			// TODO: handle exception
			log.error("exception");
		}
		log.info("==insertdataRollBack==="+getcount());
		try {
			txService.insertdataRollBack2();
		} catch (Exception e) {
			// TODO: handle exception
			log.error("exception");
		}
		log.info("==insertdataRollBack2==="+getcount());
		
	}
	
	public long getcount() {
		return (Long)jdbcTemplate.queryForList("SELECT count(id) \"CNT\" FROM `table1`").get(0).get("CNT");
	}
}
