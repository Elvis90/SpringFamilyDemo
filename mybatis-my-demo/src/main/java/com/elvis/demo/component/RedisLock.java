//package com.elvis.demo.component;
//
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.data.redis.core.StringRedisTemplate;
//import org.springframework.stereotype.Component;
//import org.springframework.util.StringUtils;
//
///**
// * Created by VitoYi on 2018/4/5.
// */
//@Component
//@Slf4j
//public class RedisLock {
//    @Autowired
//    private StringRedisTemplate redisTemplate;
//
//    /**
//     * 加锁
//     *
//     * @param key   商品id
//     * @param value 当前时间+超时时间
//     * @return
//     */
//    public boolean lock(String key, String value) {
//
//
//
//        if (redisTemplate.opsForValue().setIfAbsent(key, value)) { //这个其实就是setnx命令，只不过在java这边稍有变化，返回的是boolea
//            return true;
//        }
//        //避免死锁，且只让一个线程拿到锁
//        String currentValue = redisTemplate.opsForValue().get(key);
//        //如果锁过期了
//        if (!StringUtils.isEmpty(currentValue) && Long.parseLong(currentValue) < System.currentTimeMillis()) {
//            //获取上一个锁的时间
//            String oldValues = redisTemplate.opsForValue().getAndSet(key, value);
//             /*
//             只会让一个线程拿到锁
//             如果旧的value和currentValue相等，只会有一个线程达成条件，因为第二个线程拿到的oldValue已经和currentValue不一样了
//             */
//            if (!StringUtils.isEmpty(oldValues) && oldValues.equals(currentValue)) {
//                return true;
//            }
//        }
//        return false;
//    }
//
//    /**
//     * 解锁
//     *
//     * @param key
//     * @param value
//     */
//    public void unlock(String key, String value) {
//        try {
//            String currentValue = redisTemplate.opsForValue().get(key);
//            if (!StringUtils.isEmpty(currentValue) && currentValue.equals(value)) {
//                redisTemplate.opsForValue().getOperations().delete(key);
//            }
//        } catch (Exception e) {
//            log.error("『redis分布式锁』解锁异常，{}", e);
//        }
//    }
//}