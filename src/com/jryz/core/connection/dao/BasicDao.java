package com.jryz.core.connection.dao;


import com.jryz.core.connection.BasicDbConnection;

/**
 * 数据连接获取类
 * Created by jryc on 2017/5/27.
 */
public interface BasicDao<T, K> {

    /**
     * 获取数据连接
     * @return
     */
    T getConnectopm();

    /**
     * 根据key 获取数据连接
     * @param k
     * @param <K>
     * @return
     */
    T getConnectopm(K k);

    /**
     * 数据连接回收方法
     * @param connection
     */
    void close(BasicDbConnection connection);
}
