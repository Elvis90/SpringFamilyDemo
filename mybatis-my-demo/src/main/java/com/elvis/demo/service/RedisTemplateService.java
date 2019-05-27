package com.elvis.demo.service;

import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.JdkSerializationRedisSerializer;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * @author Elvis
 * @create 2019-05-24 14:36
 */
@Service
public class RedisTemplateService {
    @Resource
    private RedisTemplate<String,Object> template;

    public static final String CHANNEL="msg";

    public static final String  QUENE_KEY="quene";

    /**
     * 存储数据或修改数据
     *
     * @param modelMap
     * @param mapName
     */
    public void setKey(String mapName, Map<String, Object> modelMap) {
        HashOperations<String, String, Object> hps = template.opsForHash();
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
}
