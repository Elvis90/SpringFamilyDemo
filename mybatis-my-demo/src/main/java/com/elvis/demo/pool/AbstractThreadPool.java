/**
 * AbstractThreadPool.java
 * 
 * Create Version: 1.0
 * Author: 樊雪强
 * Create Date: 2016年6月28日
 * 
 * Copyright (c) 2016 CMCCIOT. All Right Reserved.
 */
package com.elvis.demo.pool;

import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * AbstractThreadPool ()
 *
 * @author Elvis
 */
@Component
public abstract class AbstractThreadPool {

	/**
	 * 线程池
	 */
	protected ThreadPoolExecutor threadpool;

	protected void setThreadPool(ThreadPoolExecutor threadpool) {
		this.threadpool = threadpool;
	}

	/**
	 * 执行异步线程
	 * 
	 * @param command
	 */
	public void execute(Runnable command) {
		threadpool.execute(command);
	}



	/**
	 * 同步阻塞执行线程
	 * 
	 * @param works
	 * @throws Exception
	 */
	public void invokeAll(List<Callable<String>> works) throws Exception {
		threadpool.invokeAll(works);
	}

	/**
	 * 获取核心线程数
	 * 
	 * @return
	 */
	public int getCorePoolSize() {
		return threadpool.getCorePoolSize();
	}

	/**
	 * 获取线程池最大线程数
	 * 
	 * @return
	 */
	public int getThreadPoolMaxSize() {
		return threadpool.getMaximumPoolSize();
	}

	/**
	 * 获取活动线程数
	 * 
	 * @return
	 */
	public int getActiveSize() {
		return threadpool.getActiveCount();
	}

	/**
	 * 获取缓冲队列数
	 * 
	 * @return
	 */
	public int getQueueSize() {
		return threadpool.getQueue().size();
	}

	/**
	 * 关闭线程池
	 */
	public void shutDown() {
		threadpool.shutdown();
	}

	/**
	 * 立即关闭线程池
	 * 
	 * @return
	 */
	public List<Runnable> shutDownNow() {
		return threadpool.shutdownNow();
	}

}
