package com.elvis.demo.redis;

import org.apache.ibatis.cache.Cache;
import org.apache.ibatis.cache.CacheKey;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.data.redis.serializer.JdkSerializationRedisSerializer;

import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class RedisCache implements Cache {
    private static final Logger logger = LoggerFactory.getLogger(RedisCache.class);

    private final ReadWriteLock readWriteLock = new ReentrantReadWriteLock();
    private final String id; // cache instance id
	private StringRedisTemplate redisTemplate;

    private static final long EXPIRE_TIME_IN_MINUTES = 1; // redis过期时间


    public RedisCache(String id) {
        if (id == null) {
            throw new IllegalArgumentException("Cache instances require an ID");
        }
        this.id = id;
    }

    @Override
    public String getId() {
        return id;
    }

    /**
     * Put query result to redis
     *
     * @param key
     * @param value
     */
    @Override
    @SuppressWarnings({ "unchecked", "rawtypes" })
    public void putObject(Object key, Object value) {
        try {
            RedisTemplate redisTemplate = getRedisTemplate();
            ValueOperations opsForValue = redisTemplate.opsForValue();
            CacheKey ck = (CacheKey)key;
            String skey = ck.toString().replaceAll("\\\n", "").replaceAll("\\\t", "").replaceAll(" +", "");
            opsForValue.set(skey, value, EXPIRE_TIME_IN_MINUTES, TimeUnit.MINUTES);
            logger.debug("Put query result to redis");
        }
        catch (Throwable t) {
            logger.error("Redis put failed", t);
        }
    }

    /**
     * Get cached query result from redis
     *
     * @param key
     * @return
     */
    @SuppressWarnings("rawtypes")
	@Override
    public Object getObject(Object key) {
        try {
            RedisTemplate redisTemplate = getRedisTemplate();
            ValueOperations opsForValue = redisTemplate.opsForValue();
            CacheKey ck = (CacheKey)key;
            String skey = ck.toString().replaceAll("\\\n", "").replaceAll("\\\t", "").replaceAll(" +", "");
            logger.debug("Get cached query result from redis");
            return opsForValue.get(skey);
        }
        catch (Throwable t) {
            logger.error("Redis get failed, fail over to db", t);
            return null;
        }
    }

    /**
     * Remove cached query result from redis
     *
     * @param key
     * @return
     */
    @Override
    @SuppressWarnings({ "unchecked", "rawtypes" })
    public Object removeObject(Object key) {
        try {
            RedisTemplate redisTemplate = getRedisTemplate();
            CacheKey ck = (CacheKey)key;
            String skey = ck.toString().replaceAll("\\\n", "").replaceAll("\\\t", "").replaceAll(" +", "");
            redisTemplate.delete(skey);
            logger.debug("Remove cached query result from redis");
        }
        catch (Throwable t) {
            logger.error("Redis remove failed", t);
        }
        return null;
    }

    /**
     * Clears this cache instance
     */
    @SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
    public void clear() {
        RedisTemplate redisTemplate = getRedisTemplate();
        Set set = redisTemplate.keys("*");
        for(Object o :set){
        	String key = o.toString();
        	if(!key.startsWith("wisdom") && !key.startsWith("spring")){
        		redisTemplate.delete(o);
        	}
        }
        logger.debug("Clear all the cached query result from redis");
    }

    /**
     * This method is not used
     *
     * @return
     */
    @Override
    public int getSize() {
        return 0;
    }

    @Override
    public ReadWriteLock getReadWriteLock() {
        return readWriteLock;
    }

	private StringRedisTemplate getRedisTemplate() {
        if (redisTemplate == null) {
            redisTemplate = ApplicationContextHolder.getBean("stringRedisTemplate");
            redisTemplate.setValueSerializer(new JdkSerializationRedisSerializer());
        }
        return redisTemplate;
    }
}
