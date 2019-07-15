package com.elvis.demo.designdemo;

import com.elvis.demo.designdemo.myinterface.MyVerify;

/**
 * @author Elvis
 * @create 2019-07-12 11:25
 */
public class VerifyStrategy {
    private MyVerify mv;
    public VerifyStrategy(MyVerify mv) {
        this.mv=mv;
    }

    public boolean verify(String s){
        return mv.execute(s);
    }
}
