package com.jryz.redis.dao;

import com.jryz.core.connection.BasicDbConnection;
import com.jryz.core.connection.dao.BasicDao;
import com.jryz.redis.DbCode;
import com.jryz.redis.connection.RedisConnection;
import com.jryz.redis.pool.RedisPoolUtil;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.util.HashMap;
import java.util.Map;

/**
 * redis Dao 获取 redis 数据连接
 * Created by jryc on 2017/5/27.
 */
public class RedisPoolDao implements BasicDao<RedisConnection, DbCode> {

    private static Map<com.jryz.dao.DbCode, JedisPool> pools = new HashMap<>(); // 链接池容器

    @Override
    public RedisConnection getConnectopm() {
        return null;
    }

    @Override
    public RedisConnection getConnectopm(DbCode o) {
        JedisPool pool;
        if ((pool = pools.get(o.getDbCode())) == null) {
            synchronized (pools) {
                if ((pool = pools.get(o.getDbCode())) == null) { // 防止重复创建 连接池
                    pool = RedisPoolUtil.createJedisPool(o.getDbCode());
                    pools.put(o.getDbCode(), pool);
                }
            }
        }
        Jedis jedis;
        synchronized (pool) { // 获取链接要上锁
            jedis = pool.getResource();
        }
        return new RedisConnection(jedis, o, this);
    }

    @Override
    public void close(BasicDbConnection connection) {
        if (connection != null) {
            RedisConnection con = (RedisConnection) connection;
            pools.get(con.getCode()
                    .getDbCode()).returnResource(con.getConnection());
        }
    }
}
