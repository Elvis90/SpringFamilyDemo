package com.elvis.demo.customerserver.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author Elvis
 * @create 2019-05-17 14:38
 */
@Component
@Slf4j
public class LoadbalancerService {

    @Autowired
    DiscoveryClient dc;
    //获取注册服务信息
    public void GetInstance(){
        List<String> serviceslist = dc.getServices();
        serviceslist.forEach((se)->log.info("serviceslist==="+se));
    }
}
