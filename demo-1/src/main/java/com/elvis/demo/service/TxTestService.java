package com.elvis.demo.service;

public interface TxTestService {

	public void insertdata();
	
	public void insertdataRollBack() throws Exception;
	
	public void insertdataRollBack2() throws Exception;
}
