//package com.elvis.demo;
//
//import java.sql.Connection;
//import java.sql.SQLException;
//import java.util.List;
//import java.util.Map;
//
//import javax.sql.DataSource;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.CommandLineRunner;
//import org.springframework.boot.SpringApplication;
//import org.springframework.boot.autoconfigure.SpringBootApplication;
//import org.springframework.jdbc.core.JdbcTemplate;
//
//import lombok.extern.slf4j.Slf4j;
//
//
//@SpringBootApplication
//@Slf4j
//public class Demo1Application implements CommandLineRunner{
//
//	@Autowired
//	private DataSource dataSource;
//	@Autowired
//	private JdbcTemplate jdbcTemplate;
//	public static void main(String[] args) {
//		SpringApplication.run(Demo1Application.class, args);
//	}
//
//	@Override
//	public void run(String... args) throws Exception {
//		// TODO Auto-generated method stub
//		showconnction();
//		testJdbchandle();
//	}
//
//	private void showconnction() throws SQLException {
//	  log.info("====dataSource==="+dataSource.toString());
//	  Connection conn = dataSource.getConnection();
//	  log.info("===conn==="+conn.toString());
//	  conn.close();
//	}
//	
//	private void testJdbchandle() {
//		 List<Map<String, Object>> list = jdbcTemplate.queryForList("select * from cellinfo_v2");
//		  log.info("list.size="+list.size());
//		  log.info("list.get(0):"+list.get(0).get("addr").toString());
//	}
//}
//
