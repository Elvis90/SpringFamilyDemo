package com.elvis.demo.redis;

import com.elvis.demo.service.RedisUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.cache.Cache;
import org.apache.ibatis.cache.CacheKey;

import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;
/*
mybatis缓存配置类
 */
@Slf4j
public class RedisCache implements Cache {

    private final ReadWriteLock readWriteLock = new ReentrantReadWriteLock();
    private final String id; // cache instance id
    private final String MYBATIS_REDIS_KEY="mybatis_cache_";
    private static final long EXPIRE_TIME_IN_SECONDS = 120; // redis过期时间
    public RedisCache(String id){
        if (id == null) {
            throw new IllegalArgumentException("id can not be null");
        }
        this.id=id;
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
             RedisUtil redisUtil=getRedisUtil();
             CacheKey ck = (CacheKey)key;
            String skey = ck.toString().replaceAll("\\\n", "").replaceAll("\\\t", "").replaceAll(" +", "");
            redisUtil.hset(getKey(),skey,value,EXPIRE_TIME_IN_SECONDS);
            log.debug("Put query result to redis");
        }
        catch (Throwable t) {
            log.error("Redis put failed", t);
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
        RedisUtil redisUtil=getRedisUtil();

        try {
            CacheKey ck = (CacheKey)key;
            String skey = ck.toString().replaceAll("\\\n", "").replaceAll("\\\t", "").replaceAll(" +", "");
            return redisUtil.hget(getKey(),skey);
        }
        catch (Throwable t) {
            log.error("Redis get failed, fail over to db", t);
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
        RedisUtil redisUtil=getRedisUtil();
        Object obj =null;
        try {
            CacheKey ck = (CacheKey)key;
            String skey = ck.toString().replaceAll("\\\n", "").replaceAll("\\\t", "").replaceAll(" +", "");
            obj = redisUtil.hget(getKey(), skey);
            redisUtil.hdel(getKey(),skey);
            log.debug("MybatisCache remove > key: {}, val: {}", key, obj);
        }
        catch (Throwable t) {
            log.error("Redis remove failed", t);
        }
        return obj;
    }

    /**
     * Clears this cache instance
     */
    @SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
    public void clear() {
        RedisUtil redisUtil=getRedisUtil();
        redisUtil.del(getKey());
        log.debug("MybatisCache clear > hashKey : {}", getKey());
    }

    /**
     * This method is not used
     *
     * @return
     */
    @Override
    public int getSize() {
        RedisUtil redisUtil=getRedisUtil();
        Long size = redisUtil.getHashSizeObj(getKey());
        if(size==null){
            return 0;
        }
        return  Integer.valueOf(size+"");
    }

    @Override
    public ReadWriteLock getReadWriteLock() {
        return readWriteLock;
    }

    public RedisUtil getRedisUtil(){

           return  ApplicationContextHolder.getBean("redisUtil");
    }

    protected String getKey() {
        return MYBATIS_REDIS_KEY + id;
    }
}
