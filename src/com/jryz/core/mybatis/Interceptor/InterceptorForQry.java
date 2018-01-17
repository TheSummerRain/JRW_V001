package com.jryz.core.mybatis.Interceptor;

import com.jryz.core.free.Pagination;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.plugin.*;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;

import java.util.List;
import java.util.Properties;

@Intercepts({ @Signature(type = Executor.class, method = "query", args = { MappedStatement.class, Object.class,
        RowBounds.class, ResultHandler.class }) })
public class InterceptorForQry implements Interceptor {

    @SuppressWarnings({ "rawtypes", "unchecked" })
    public Object intercept(Invocation invocation) throws Throwable {
        Object result = invocation.proceed(); //执行请求方法，并将所得结果保存到result中
        Object obj = invocation.getArgs()[1];
        if (obj instanceof Pagination) {
            Pagination pag = (Pagination) obj;
            pag.setItems((List) result); // 此时 items 中已经包含 返回结果
        }
        return result;
    }

    public Object plugin(Object target) {
        System.out.println("this is the proceed ===>>" + target);
        return Plugin.wrap(target, this);
    }

    public void setProperties(Properties arg0) {
        System.out.println("this is the properties ===>>" + arg0);
    }
}