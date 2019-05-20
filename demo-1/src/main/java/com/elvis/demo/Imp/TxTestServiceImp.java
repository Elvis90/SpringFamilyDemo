package com.elvis.demo.Imp;


import com.elvis.demo.service.TxTestService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@Slf4j
public class TxTestServiceImp implements TxTestService {

	@Autowired
	 private JdbcTemplate jdbcTemplate;
	@Override
	@Transactional
	public void insertdata() {
		// TODO Auto-generated method stub
		jdbcTemplate.execute("INSERT INTO table1(id,text) VALUES (2,\"bb\")");
	}

	@Override
	@Transactional(rollbackFor=Exception.class)
	public void insertdataRollBack() throws Exception {
		// TODO Auto-generated method stub
		jdbcTemplate.execute("INSERT INTO table1(id,text) VALUES (3,\"cc\")");
		log.info("insertdataRollBack getCount:{}",jdbcTemplate.queryForObject("SELECT count(id) \"CNT\" FROM table1", Long.class));
		throw new Exception();
	}

	@Override
	@Transactional(rollbackFor=Exception.class)
	public void insertdataRollBack2() throws Exception {
		// TODO Auto-generated method stub
		insertdataRollBack();
	}

}
