package com.jryz.redis.pool;

import com.jryz.dao.DbCode;
import com.jryz.file.PropertiesUtil;
import org.apache.log4j.Logger;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import java.util.Properties;

public class RedisPoolUtil {

    private static Properties properties = new PropertiesUtil("db.properties").getProperties();
    private static final Logger logger = Logger.getLogger(RedisPoolUtil.class);

    //可用连接实例的最大数目，默认值为8；
    private static int MAX_ACTIVE = 102400;
    
    //控制一个pool最多有多少个状态为idle(空闲的)的jedis实例，默认值也是8。
    private static int MAX_IDLE = 20;
    
    //等待可用连接的最大时间，单位毫秒，默认值为-1，表示永不超时。如果超过等待时间，则直接抛出JedisConnectionException；
    private static int MAX_WAIT = 10000;
    

    //在borrow一个jedis实例时，是否提前进行validate操作；如果为true，则得到的jedis实例均是可用的；
    private static boolean TEST_ON_BORROW = true;
    
    private static JedisPool jedisPool = null;

    public synchronized static JedisPool createJedisPool(DbCode dbCode) {
        try {
            JedisPoolConfig config = new JedisPoolConfig();
            config.setMaxActive(MAX_ACTIVE);
            config.setMaxIdle(MAX_IDLE);
            config.setMaxWait(MAX_WAIT);
            config.setTestOnBorrow(TEST_ON_BORROW);
            return new JedisPool(
                    config,
                    properties.getProperty(dbCode.getCode() + "." + "HOST"),
                    Integer.parseInt(properties.getProperty(dbCode.getCode() + "." + "PORT")),
                    Integer.parseInt(properties.getProperty(dbCode.getCode() + "." + "TIMEOUT")),
                    properties.getProperty(dbCode.getCode() + "." + "AUTH"),
                    dbCode.getIndex()
            );
        } catch (Exception e) {
            logger.error(e);
        }
        return null;
    }
}