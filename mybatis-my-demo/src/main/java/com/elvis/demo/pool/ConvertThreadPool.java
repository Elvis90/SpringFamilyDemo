/**
 * BaseThreadPool.java
 * 
 * Create Version: 1.0
 * Author: 樊雪强
 * Create Date: 2016年6月28日
 * 
 * Copyright (c) 2016 CMCCIOT. All Right Reserved.
 */
package com.elvis.demo.pool;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * BaseThreadPool ()
 * 
 * 单例模式
 *
 * @author elvis
 */
public class ConvertThreadPool extends AbstractThreadPool {

	private static ConvertThreadPool instance = new ConvertThreadPool();

	private ConvertThreadPool() {
		/**
		 * ThreadPoolExecutor(int corePoolSize, int maximumPoolSize, long keepAliveTime, TimeUnit unit, BlockingQueue
		 * <Runnable> workQueue, RejectedExecutionHandler handler)
		 * corePoolSize： 线程池维护线程的最少数量
		 * maximumPoolSize：线程池维护线程的最大数量
		 * keepAliveTime： 线程池维护线程所允许的空闲时间
		 * unit： 线程池维护线程所允许的空闲时间的单位
		 * workQueue： 线程池所使用的缓冲队列
		 * handler： 线程池对拒绝任务的处理策略(抛弃旧的任务)
		 */
		ThreadPoolExecutor threadpool = new ThreadPoolExecutor(10, 100, 0, TimeUnit.MILLISECONDS,
				new LinkedBlockingQueue<Runnable>(1000), new ThreadPoolExecutor.DiscardOldestPolicy());
		super.setThreadPool(threadpool);
	}

	public static ConvertThreadPool getInstance() {
		return instance;
	}

}
