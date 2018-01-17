package com.jryz.web.filter;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * 自已实现的类似Spring的ContextHolder，在没有使用spring或struts的框架中，想要在一般的代码中获取request，可以使用此类
 * @author 何金旭
 *
 */
public class ContextHolder {

	private static Map<Thread, ThreadLocal<HttpServletRequest>> requestThreadLocalMap = new HashMap<Thread, ThreadLocal<HttpServletRequest>>();
	
	public static HttpServletRequest getRequest() {
		ThreadLocal<HttpServletRequest> reqThreadLocal = requestThreadLocalMap.get(Thread.currentThread());
        if (reqThreadLocal == null) {
            return null;
        }
        return (HttpServletRequest) requestThreadLocalMap.get(Thread.currentThread()).get();
	}

	public static Map<Thread, ThreadLocal<HttpServletRequest>> getThreadLocalMap() {
		return requestThreadLocalMap;
	}

	public static void setThreadLocalMap(Map<Thread, ThreadLocal<HttpServletRequest>> threadLocalMap) {
		ContextHolder.requestThreadLocalMap = threadLocalMap;
	}
	
	
}
