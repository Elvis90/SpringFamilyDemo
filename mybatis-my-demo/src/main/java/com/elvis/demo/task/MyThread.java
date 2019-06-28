package com.elvis.demo.task;

import com.elvis.demo.pool.ConvertThreadPool;
import lombok.extern.slf4j.Slf4j;

import java.util.Random;
import java.util.concurrent.CountDownLatch;

/**
 * @author Elvis
 * @create 2019-06-24 16:52
 */
@Slf4j
public class MyThread implements Runnable{
    private String name;
    public MyThread(String name){
        this.name=name;
    }

    @Override
    public void run() {
        log.info("线程名为:{}",name);
        for (int i = 20; i < 30; i++) {
            try {
                Thread.sleep(1000);
                log.info("线程类在执行"+i);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }
        //新开十个子线程 同时完成任务之后再执行当前线程
        int x =10;
        CountDownLatch cdl = new CountDownLatch(x);
        for (int i = 0; i < x; i++) {
            ConvertThreadPool.getInstance().execute(new ChildThread(cdl));
        }
        try {
            cdl.await();
            log.info("子线程执行完毕  执行后续操作");
        }catch (Exception e){
            e.printStackTrace();
        }



    }
    public class ChildThread implements Runnable{
        private CountDownLatch cdl;
        public ChildThread(CountDownLatch cdl) {
            this.cdl = cdl;
        }

        @Override
        public void run() {
            try {
                Thread.sleep(new Random().nextInt(100) *100);
                log.info("子线程执行完成:{}",Thread.currentThread().getId());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }finally {
                cdl.countDown();
            }
        }
    }
}


