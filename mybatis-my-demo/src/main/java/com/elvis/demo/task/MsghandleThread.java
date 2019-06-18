package com.elvis.demo.task;

import com.alibaba.fastjson.JSONObject;
import com.elvis.demo.service.RedisTemplateService;
import lombok.extern.slf4j.Slf4j;

/**
 * @author Elvis
 * @create 2019-05-24 16:46
 */


@Slf4j
public class MsghandleThread extends Thread{


    private RedisTemplateService rts;
    public MsghandleThread(RedisTemplateService rts){
        this.rts=rts;
    }
    @Override
    public void run() {
        while (true){
            JSONObject js = (JSONObject)rts.ConsumerMsgByquene(RedisTemplateService.QUENE_KEY);
            if(js==null){
                log.info("没有待消费的消息");
                break;
            }
            log.info("队列里面的消息为"+js.toJSONString());
        }
    }
}
