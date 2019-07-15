package com.elvis.demo.designdemo.myinterface.imp;

import com.elvis.demo.designdemo.myinterface.MyVerify;

/**
 * @author Elvis
 * @create 2019-07-12 11:21
 */
public class IsAllLower implements MyVerify {
    @Override
    public boolean execute(String s) {
       return s.matches("[a-z]+");
    }
}
