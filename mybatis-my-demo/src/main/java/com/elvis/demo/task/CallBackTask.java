package com.elvis.demo.task;

import com.elvis.demo.service.CallBackTest;

import java.util.concurrent.TimeUnit;

/**
 * @author Elvis
 * @create 2019-11-19 9:56
 */
public class CallBackTask{
    

    //同步回调
    public void testCallBack(CallBackTest callBackTest){
        excude();
        if(callBackTest!=null){
            callBackTest.notice();
        }
    }


    private void excude()  {
        System.out.println("任务执行开始");
        try {
            TimeUnit.SECONDS.sleep(10);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("任务执行结束");
    }



    public static void main(String[] args) {
        System.out.println("主线程开始");

        new Thread(new Runnable() {
            @Override
            public void run() {
                CallBackTask ct = new CallBackTask();
                ct.testCallBack(()-> System.out.println("执行异步通知"));
            }
        }).start();
        System.out.println("主线程结束");

    }
}
