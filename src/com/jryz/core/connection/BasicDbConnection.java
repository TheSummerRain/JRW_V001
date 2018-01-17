package com.jryz.core.connection;


import com.jryz.core.connection.dao.BasicDao;

/**
 * 数据连接基础包装类
 * Created by jryc on 2017/5/27.
 */
public abstract class BasicDbConnection<T> {

    private T           connection; // 原始 数据 连接
    private BasicDao dao;        // 链接创建者

    protected BasicDbConnection(T connection, BasicDao dao) {
        this.connection = connection;
        this.dao = dao;
    }

    public BasicDao getDao() {
        return dao;
    }

    /**
     * 关闭数据连接
     */
    public void close() {
    };

    public T getConnection() {
        return connection;
    }
}
