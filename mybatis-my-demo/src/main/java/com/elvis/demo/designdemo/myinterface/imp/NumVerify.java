package com.elvis.demo.designdemo.myinterface.imp;

import com.elvis.demo.designdemo.myinterface.MyVerify;

/**
 * @author Elvis
 * @create 2019-07-12 11:24
 */
public class NumVerify implements MyVerify {
    @Override
    public boolean execute(String s) {
        return s.matches("\\d+");
    }
}
