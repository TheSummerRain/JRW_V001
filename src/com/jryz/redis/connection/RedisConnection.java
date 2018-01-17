package com.jryz.redis.connection;

import com.jryz.core.connection.BasicDbConnection;
import com.jryz.redis.DbCode;
import com.jryz.redis.dao.RedisPoolDao;
import redis.clients.jedis.Jedis;

/**
 * redis 数据连接
 * Created by jryc on 2017/5/27.
 */
public class RedisConnection extends BasicDbConnection<Jedis> {

    // 当前数据连接标记
    private DbCode code;

    public RedisConnection(Jedis connection, DbCode code, RedisPoolDao dao) {
        super(connection, dao);
        this.code = code;
    }

    public DbCode getCode() {
        return code;
    }

    @Override
    public void close() {
        super.close();
        super.getDao().close(this);
    }
}
