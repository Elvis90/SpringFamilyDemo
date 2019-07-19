package com.elvis.demo.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.data.redis.serializer.JdkSerializationRedisSerializer;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @author Elvis
 * @create 2019-05-24 14:36
 */
@Service
@Slf4j
public class RedisTemplateService {
    @Resource
    private RedisTemplate<String,Object> template;

    public static final String CHANNEL="msg";

    public static final String  QUENE_KEY="quene";

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    /**
     * 存储数据或修改数据
     *
     * @param modelMap
     * @param mapName
     */
    public void setKey(String mapName, Map<String, Object> modelMap,long timout,TimeUnit tu) {
        HashOperations<String, String, Object> hps = template.opsForHash();
        template.expire(mapName,timout,tu);
        hps.putAll(mapName, modelMap);
    }

    /**
     * 获取数据Map
     *
     * @param mapName
     * @return
     */
    public Map<String, Object> getMapValue(String mapName) {
        HashOperations<String, String, Object> hps = this.template.opsForHash();
        return hps.entries(mapName);

    }

    /**
     * 获取数据value
     *
     * @param mapName
     * @param hashKey
     * @return
     */
    public Object getValue(String mapName, String hashKey) {
        HashOperations<String, String, Object> hps = this.template.opsForHash();
        return hps.get(mapName, hashKey);

    }

    /**
     * 批量删除缓存数据
     *
     * @param keys
     */
    public void deleteData(List<String> keys) {
        // 执行批量删除操作时先序列化template
        template.setKeySerializer(new JdkSerializationRedisSerializer());
        template.delete(keys);
    }

    /*
        订阅发布模式 消息发布方法
     */
    public void sendmsg(Object msg){
        template.convertAndSend(CHANNEL,msg);
    }

    /*
        消息队列模式 消息发送
     */
    public void sendmsgByquene(String key,Object msg){
        template.opsForList().leftPush(key, msg);
    }

    /*
        消息队列模式 消费消息  监听指定队列
     */
    public Object ConsumerMsgByquene(String key){
        return template.opsForList().rightPop(key);
    }
    /*
  使用有序集合进行访问频率限制 两分钟五次请求
  */
    public boolean isbusy(String userName){
        String key="user:request:"+userName;
        Long source=System.currentTimeMillis();
        //二分钟以前的时间戳
        Long expore = new Date(source -120000).getTime();
        ZSetOperations<String, String> zset = stringRedisTemplate.opsForZSet();
        zset.add(key,source+"",source);
        stringRedisTemplate.expire(key,2, TimeUnit.MINUTES);
        //删除集合里二分钟之前的元素
        Long aLong = zset.removeRangeByScore(key, 0, expore);
        //判断是否到达阈值
        Long num = zset.count(key,0,source);
        if(num>=5){
            return true;
        }else{
            return false;
        }

    }
}
