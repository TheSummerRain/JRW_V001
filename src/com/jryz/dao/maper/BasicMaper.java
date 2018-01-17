package com.jryz.dao.maper;

/**
 * maper 包装类
 */

import com.jryz.core.free.Pagination;

import java.util.List;

/**
 * maper 封装的 模板方法
 * @param <T>
 */
public interface BasicMaper <T> {

    List<T> list(T t);

    List<T> listByPage(Pagination pag);

    void insert(T user);

    void update(T user);

    void delete(String Id);

    T get(String id);

    T getOne(T t);
}
