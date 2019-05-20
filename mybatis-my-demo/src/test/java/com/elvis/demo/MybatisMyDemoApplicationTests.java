//package com.elvis.demo;
//
//import java.util.ArrayList;
//import java.util.List;
//
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.mybatis.generator.api.MyBatisGenerator;
//import org.mybatis.generator.config.Configuration;
//import org.mybatis.generator.config.xml.ConfigurationParser;
//import org.mybatis.generator.internal.DefaultShellCallback;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.test.context.junit4.SpringRunner;
//
//@RunWith(SpringRunner.class)
//@SpringBootTest
//public class MybatisMyDemoApplicationTests {
//
//	@Test
//	public void contextLoads() {
//		try {
//			generateArtifacts();
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//	}
//
//	private void generateArtifacts() throws Exception {
//		List<String> warnings = new ArrayList<>();
//		ConfigurationParser cp = new ConfigurationParser(warnings);
//		Configuration config = cp.parseConfiguration(
//				this.getClass().getResourceAsStream("/generatorConfig.xml"));
//		DefaultShellCallback callback = new DefaultShellCallback(true);
//		MyBatisGenerator myBatisGenerator = new MyBatisGenerator(config, callback, warnings);
//		myBatisGenerator.generate(null);
//	}
//}
