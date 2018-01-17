package com.jryz.core.free.service;


import com.jryz.core.free.Pagination;

import java.beans.IntrospectionException;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

/**
 * Created by jryc on 2017/2/8.
 */
public interface BasicService <T>{

    /**
     * 获取列表 分页
     * @param t
     * @return
     */
    Pagination getBeanPage(T t, Pagination pag);


    /**
     * 获取列表
     * @param t
     * @return
     */
    List<T> getBeanList(T t);

    /**
     * 得到实体
     * @param t
     * @return
     */
    T getBean(T t);


    /**
     *  保存实体 返回主键 仅限自增
     * @param t
     * @return
     */
    int saveAndReturnId(T t) throws IllegalAccessException, IntrospectionException, InvocationTargetException, Exception;

    /**
     *  保存实体
     * @param t
     * @return
     */
    boolean save(T t);

    boolean save(List<T> t);

    /**
     *  保存实体
     * @param t
     * @return
     */
    boolean update(T t);

    /**
     * 删除数据
     * @param t
     * @return
     */
    boolean delBean(T t);
}
