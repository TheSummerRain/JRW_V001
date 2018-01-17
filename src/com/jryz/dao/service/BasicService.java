package com.jryz.dao.service;

import com.jryz.core.free.Pagination;
import com.jryz.dao.maper.BasicMaper;
import com.jryz.spring.ApplicationContextUtil;

import java.util.List;


public abstract class BasicService <T> {

    private BasicMaper basicMaper;

    /**
     * 继承需传入 maper
     * @param basicMaperClass
     */
    public BasicService(Class<?> basicMaperClass) {
        basicMaper = (BasicMaper) ApplicationContextUtil.getContext().getBean(basicMaperClass);
    }

    private BasicMaper getBasicMaper(){
        return basicMaper;
    }

    private boolean isCyclicLoading(Class t){
        return false;
    }

    public List<T> list(T t) {
        List<T> list = basicMaper.list(t);
        postpositionNotification(list);
        return list;
    }

    public List<T> listByPage(Pagination pag) {
        List<T> list = basicMaper.listByPage(pag);
        postpositionNotification(list);
        return list;
    }

    public void insert(T t) {
        basicMaper.insert(t);
    }

    public void update(T t) {
        basicMaper.update(t);
    }

    public void delete(String id) {
        basicMaper.delete(id);
    }

    public T get(T t) {
        T tt = (T) basicMaper.getOne(t);
        postpositionNotification(tt);
        return tt;
    }

    public T get(T t, boolean toNotice) {
        T tt = (T) basicMaper.getOne(t);
        if (toNotice)
            postpositionNotification(tt);
        return tt;
    }

    public T get(String id) {
        T bean = (T) basicMaper.get(id);
        postpositionNotification(bean);
        return bean;
    }

    public T get(String id, boolean toNotice) {
        T bean = (T) basicMaper.get(id);
        if (toNotice)
            postpositionNotification(bean);
        return bean;
    }

    public List<T> list(T t, boolean toNotice) {
        List<T> list = basicMaper.list(t);
        if (toNotice)
            postpositionNotification(list);
        return list;
    }

    public List<T> listByPage(Pagination pag, boolean toNotice) {
        List<T> list = basicMaper.listByPage(pag);
        if (toNotice)
            postpositionNotification(list);
        return list;
    }

    /**
     * 获取到 数据集并返回结果前的 通知
     * @param obj type = list<T> || T
     */
    protected void postpositionNotification(Object obj) {

    };

    /**
     * 删除 数据并返回结果前的 通知
     * @param obj T
     */
    protected void postDelPositionNotification(Object obj) {

    };
}
