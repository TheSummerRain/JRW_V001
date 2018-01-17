package com.jryz.redis.dao;


import com.jryz.redis.DbCode;

import java.io.UnsupportedEncodingException;
import java.util.List;

/**
 * Created by jryc on 2017/5/31.
 */
public interface RedisDao {

    /**
     * 为某个key 设置有效期
     * @param key
     * @param seconds
     * @return
     */
    Long expire(DbCode dbCode, String key, final int seconds);

    String set(DbCode dbCode, final String key, String value);

    /**
     * 通过序列化的方式 保存bean的方法
     * @param dbCode
     * @param key
     * @param s
     * @param <T>
     * @return
     */
    <T> String set(DbCode dbCode, final String key, T s);
    <T> Long add(DbCode dbCode, final String key, List<T> s);
    <T> Long add(DbCode dbCode, final String key, T s);
    <T> T getPojo(DbCode dbCode, final String key);
    <T> T getAndDelPojo(DbCode dbCode, final String key);
    <T> List<T> getPojoList(DbCode dbCode, final String key, int start, int end);

    String get(DbCode dbCode, final String key);
    String getAndDel(DbCode dbCode, final String key);

    Long del(DbCode dbCode, final String key);

    /**
     * 删除list集合
     * @param dbCode
     * @param key
     * @return
     */
    String ltrim(DbCode dbCode, String key, int start, int end) throws UnsupportedEncodingException;

    String hmset(DbCode dbCode, String key, java.util.Map<String, String> hash);

    java.util.Map<String,String> hgetAll(DbCode dbCode, String key);
}
