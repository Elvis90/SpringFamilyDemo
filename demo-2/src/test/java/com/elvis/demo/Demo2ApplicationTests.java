package com.elvis.demo;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.Map;

@RunWith(SpringRunner.class)
@SpringBootTest
public class Demo2ApplicationTests {

	@Autowired
	public JdbcTemplate jdbcTemplate;
	@Test
	public void contextLoads() {
		List<Map<String, Object>> map = jdbcTemplate.queryForList("select * from table1");
		System.out.println(map.size());
	}

}
