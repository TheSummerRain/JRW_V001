package com.jryz.web.filter;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 根据sessionid对 map进行包装， session内的局部变量表的实现。
 *
 * 缺陷： gc无法自动回收
 */
public class SessionHolder {

    private static Map<String, ConcurrentHashMap<String, Object>> sessionMap = new ConcurrentHashMap();

    public <T> void put(String key, T t) {
        String sessionId;
        ConcurrentHashMap<String, Object> sessionData = sessionMap.get(sessionId = ContextHolder.getRequest().getSession().getId());
        if (sessionData == null)
            sessionMap.put(sessionId, sessionData = new ConcurrentHashMap<>());
        sessionData.put(key, t);
    }
}
