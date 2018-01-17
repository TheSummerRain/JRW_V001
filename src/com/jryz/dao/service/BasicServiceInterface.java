package com.jryz.dao.service;

import com.jryz.core.free.Pagination;

import java.util.List;

/**
 * maper 封装的 模板方法
 * @param <T>
 */
public interface BasicServiceInterface <T> {

    List<T> list(T t);

    List<T> listByPage(Pagination pag);

    void insert(T bean);

    void update(T bean);

    void delete(String id);

    T get(String id);

    T get(T t);

    T get(T t, boolean toNotice);

    /**
     *
     * @param id
     * @param toNotice 是否加载 对bean中的 关联实体进行填充
     * @return
     */
    T get(String id, boolean toNotice);

    List<T> list(T t, boolean toNotice);

    List<T> listByPage(Pagination pag, boolean toNotice);
}