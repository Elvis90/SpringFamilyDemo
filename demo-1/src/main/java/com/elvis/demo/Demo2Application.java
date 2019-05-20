//package com.elvis.demo;
//
//
//
//
//import java.util.List;
//import java.util.Map;
//
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Qualifier;
//import org.springframework.boot.CommandLineRunner;
//import org.springframework.boot.SpringApplication;
//import org.springframework.boot.autoconfigure.SpringBootApplication;
//import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
//import org.springframework.boot.autoconfigure.jdbc.DataSourceTransactionManagerAutoConfiguration;
//import org.springframework.jdbc.core.JdbcTemplate;
//import lombok.extern.slf4j.Slf4j;
//
///*
// * 多数据源测试
// */
//@SpringBootApplication(exclude= {DataSourceAutoConfiguration.class,DataSourceTransactionManagerAutoConfiguration.class})
//@Slf4j
//public class Demo2Application implements CommandLineRunner{
//	
//	 @Autowired
//	 @Qualifier("celltestJdbcTemplate")
//	 protected JdbcTemplate jt1;
//	 @Autowired
//	  @Qualifier("testJdbcTemplate")
//	   protected JdbcTemplate jt2;
//	  
//	public static void main(String[] args) {
//		SpringApplication.run(Demo2Application.class, args);
//	}
//
//	@Override
//	public void run(String... args) throws Exception {
//		// TODO Auto-generated method stub
//		log.info("jt1===="+jt1);
//		 List<Map<String, Object>> list = jt1.queryForList("select * from cellinfo_v2");
//		  log.info("list.size="+list.size());
//		  log.info("list.get(0):"+list.get(0).get("addr").toString());
//		  
//		  List<Map<String, Object>> list2 = jt2.queryForList("select * from role_iot");
//		  log.info("list.size="+list2.size());
//		  log.info("list.get(0):"+list2.get(0).get("role_name").toString());
//	}
//
//	
// 
//}
//
