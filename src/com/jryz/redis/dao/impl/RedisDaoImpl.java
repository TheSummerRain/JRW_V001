package com.jryz.redis.dao.impl;

import com.jryz.SerializeUtil;
import com.jryz.core.Cache;
import com.jryz.redis.DbCode;
import com.jryz.redis.connection.RedisConnection;
import com.jryz.redis.dao.RedisDao;
import com.jryz.redis.dao.RedisPoolDao;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by jryc on 2017/5/31.
 */
@Component
public class RedisDaoImpl extends RedisPoolDao implements RedisDao {

    @Override
    public Long expire(DbCode dbCode, String key, int seconds) {
        RedisConnection connection = getConnectopm(dbCode);
        try {
            return connection.getConnection().expire(createKey(dbCode, key), seconds);
        } finally {
            close(connection);
        }
    }

    @Override
    public String set(DbCode dbCode, String key, String value) {
        RedisConnection connection = getConnectopm(dbCode);
        try {
            return connection.getConnection().set(createKey(dbCode, key), value);
        } finally {
            close(connection);
        }
    }

    @Override
    public <T> String set(DbCode dbCode, String key, T s) {
        RedisConnection connection = getConnectopm(dbCode);
        try {
            byte[] serialize = SerializeUtil.serialize(s);
            return connection.getConnection().set(createKey(dbCode, key).getBytes("utf-8"), serialize);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            close(connection);
        }
        return null;
    }

    @Override
    public <T> Long add(DbCode dbCode, String key, List<T> s) {
        RedisConnection connection = getConnectopm(dbCode);
        try {
            int l = s.size();
            byte[][] bytes = new byte[l][];
            for (int i = 0; i < l; i++) {
                bytes[i] = (SerializeUtil.serialize(s.get(i)));
            }
            return connection.getConnection().rpush(createKey(dbCode, key).getBytes("utf-8"), bytes);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            close(connection);
        }
        return null;
    }

    @Override
    public <T> Long add(DbCode dbCode, String key, T s) {
        RedisConnection connection = getConnectopm(dbCode);
        try {
            byte[] bytes = (SerializeUtil.serialize(s));
            return connection.getConnection().rpush(createKey(dbCode, key).getBytes("utf-8"), bytes);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            close(connection);
        }
        return null;
    }

    @Override
    public <T> T getPojo(DbCode dbCode, String key) {
        RedisConnection connection = getConnectopm(dbCode);
        try {
            byte[] serialize = connection.getConnection().get(createKey(dbCode, key).getBytes("utf-8"));
            if (serialize == null) {
                return null;
            }
            return (T) SerializeUtil.unserialize(serialize);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close(connection);
        }
        return null;
    }

    @Override
    public <T> T getAndDelPojo(DbCode dbCode, String key) {
        try {
            return getPojo(dbCode, key);
        } finally {
            del(dbCode, key);
        }
    }

    @Override
    public <T> List<T> getPojoList(DbCode dbCode, String key, int start, int end) {
        RedisConnection connection = getConnectopm(dbCode);
        try {
            List<byte[]> serialize = connection.getConnection().lrange(createKey(dbCode, key).getBytes("utf-8"), start, end);
            if (serialize == null) {
                return null;
            }
            List<T> data = new ArrayList<>();
            serialize.forEach((v) -> {
                data.add((T) SerializeUtil.unserialize(v));
            });
            return data;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close(connection);
        }
        return null;
    }

    @Override
    public String get(DbCode dbCode, String key) {
        RedisConnection connection = getConnectopm(dbCode);
        try {
            return connection.getConnection().get(createKey(dbCode, key));
        } finally {
            close(connection);
        }
    }

    @Override
    public String getAndDel(DbCode dbCode, String key) {
        try {
            return get(dbCode, key);
        } finally {
            del(dbCode, key);
        }
    }

    @Override
    public Long del(DbCode dbCode, String key) {
        RedisConnection connection = getConnectopm(dbCode);
        try {
            return connection.getConnection().del(createKey(dbCode, key));
        } finally {
            close(connection);
        }
    }

    @Override
    public String ltrim(DbCode dbCode, String key, int start, int end) throws UnsupportedEncodingException {
        RedisConnection connection = getConnectopm(dbCode);
        try {
            return connection.getConnection().ltrim(createKey(dbCode, key).getBytes("utf-8"), start, end);
        } finally {
            close(connection);
        }
    }

    @Override
    public String hmset(DbCode dbCode, String key, Map<String, String> hash) {
        RedisConnection connection = getConnectopm(dbCode);
        try {
            return connection.getConnection().hmset(createKey(dbCode, key), hash);
        } finally {
            close(connection);
        }
    }

    @Override
    public Map<String, String> hgetAll(DbCode dbCode, String key) {
        RedisConnection connection = getConnectopm(dbCode);
        try {
            return connection.getConnection().hgetAll(createKey(dbCode, key));
        } finally {
            close(connection);
        }
    }


    private String createKey(DbCode dbCode, String key){
        return Cache.PROJECT_ID + "_" + dbCode + "_" + key;
    }
}
