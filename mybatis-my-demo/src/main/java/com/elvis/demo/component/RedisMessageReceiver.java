
package com.elvis.demo.component;

import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.JdkSerializationRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.stereotype.Component;

/**
 * 接收消息的实体类
 * 订阅发布模式
 *
 */
@Component
@Slf4j
public class RedisMessageReceiver implements MessageListener {
    @Autowired
    private RedisTemplate<Object, Object> redisTemplate;
//    @Override
//    public void onMessage(Message message, byte[] pattern) {
//        RedisSerializer<String> serializer = redisTemplate.getStringSerializer();
//        String msg = serializer.deserialize(message.getBody());
//        log.info("接收到的消息是：" + msg);
//    }

    @Override
    public void onMessage(Message message, byte[] pattern) {
        RedisSerializer serializer = new JdkSerializationRedisSerializer();
        StringRedisSerializer stringRedisSerializer = new StringRedisSerializer();
        JSONObject msg = (JSONObject)serializer.deserialize(message.getBody());
        //订阅的频道
        String channel = stringRedisSerializer.deserialize(message.getChannel());
        //HashMap<String,Object>  map = (HashMap<String,Object>)serializer.deserialize(message.getBody());
        log.info("channel:"+channel+"===content:"+msg.toJSONString());

    }
}
