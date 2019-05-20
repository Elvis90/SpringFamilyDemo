//package com.elvis.demo;
//
//import javax.annotation.Resource;
//import javax.sql.DataSource;
//
//
//import org.springframework.beans.factory.annotation.Qualifier;
//import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
//import org.springframework.boot.context.properties.ConfigurationProperties;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.jdbc.core.JdbcTemplate;
//import org.springframework.jdbc.datasource.DataSourceTransactionManager;
//import org.springframework.transaction.PlatformTransactionManager;
//
//import lombok.extern.slf4j.Slf4j;
//
///*
// * 多数据源配置
// */
//@Slf4j
//@Configuration
//public class DataSourceConfig {
//
//	@Bean
//	@ConfigurationProperties("celltest.datasource")
//	public DataSourceProperties celltestDataSourceProperties() {
//		return new DataSourceProperties();
//	}
//	
//	@Bean(name="celltestDataSource")
//	public DataSource celltestDataSource() {
//		DataSourceProperties dsp = celltestDataSourceProperties();
//		log.info("celltestDataSource:{}",dsp.getUrl());
//		return dsp.initializeDataSourceBuilder().build();
//	}
//	@Bean
//	@Resource
//	public PlatformTransactionManager celltestTxMan(DataSource celltestDataSource) {
//		return new DataSourceTransactionManager(celltestDataSource);
//	}
//	
//	@Bean
//	@ConfigurationProperties("test.datasource")
//	public DataSourceProperties testDataSourceProperties() {
//		return new DataSourceProperties();
//	}
//	
//	@Bean(name="testDataSource")
//	public DataSource testDataSource() {
//		DataSourceProperties dsp = testDataSourceProperties();
//		log.info("testDataSource:{}",dsp.getUrl());
//		return dsp.initializeDataSourceBuilder().build();
//	}
//	@Bean
//	@Resource
//	public PlatformTransactionManager testTxMan(DataSource testDataSource) {
//		return new DataSourceTransactionManager(testDataSource);
//	}
//	
//
//	/**
//     * @param dataSource
//     * @return
//     * @Bean明确地指示了一种方法，什么方法呢——产生一个bean的方法， 并且交给Spring容器管理；从这我们就明白了为啥@Bean是放在方法的注释上了，
//     * 因为它很明确地告诉被注释的方法，你给我产生一个Bean，然后交给Spring容器，
//     * 实现依赖注入
//     */
//    @Bean(name = "celltestJdbcTemplate")
//    public JdbcTemplate primaryTemplate(@Qualifier("celltestDataSource") DataSource dataSource) {
//        return new JdbcTemplate(dataSource);
//    }
// 
//    @Bean(name = "testJdbcTemplate")
//    public JdbcTemplate secondaryTemplate(@Qualifier("testDataSource") DataSource dataSource) {
//        return new JdbcTemplate(dataSource);
//    }
// 
//}
