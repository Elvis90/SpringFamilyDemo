package com.elvis.demo.designdemo;

/**
 * @author Elvis
 * @create 2019-07-10 16:37
 * 枚举类实现单例模式  调用此类方法方式 SingleCaseEnum.INSTANCE.otherMethods()
 */
public enum SingleCaseEnum{
    INSTANCE;
    public void otherMethods(){
        System.out.println("单例模式Something");
    }
}