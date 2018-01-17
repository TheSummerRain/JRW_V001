package com.jryz.web.filter;

import com.google.gson.Gson;
import com.jryz.model.ApiResult;
import com.jryz.model.ApiRtnCode;
import com.jryz.random.RandomUtil;
import com.jryz.spring.ApplicationContextUtil;
import com.jryz.web.MyHttpServletResponseWrapper;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.MultipartResolver;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

public class ContextFilter implements Filter{
	
	Logger log = Logger.getLogger(this.getClass());
	
	// 用于创建MultipartHttpServletRequest
	private MultipartResolver multipartResolver = null;

    @Override
	public void init(FilterConfig arg0) throws ServletException {
        multipartResolver = (ApplicationContextUtil.getContext().getBean("multipartResolver", MultipartResolver.class));
	}

	@Override
	public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws ServletException, IOException {
		HttpServletResponse resp2 = (HttpServletResponse) resp;
		ServletRequest request = getRequest(req);
        try {
            // 暂时不使用
            // MyHttpServletRequestWrapper myreq = new MyHttpServletRequestWrapper((HttpServletRequest) req);
            MyHttpServletResponseWrapper myResp = new MyHttpServletResponseWrapper(resp2);
            //log.info("Request->" + request.getBody());
            // jsonp跨域支持
            String _pleaseReturnDataType = request.getParameter("_prdt");
            if ("jsonp".equalsIgnoreCase(_pleaseReturnDataType)) {
                String funName = request.getParameter("callback");
                funName = funName == null ? "callback" : funName;
                myResp.getOutputStream().write((funName + "(").getBytes("utf-8"));
            }
            String sign = "sign_" + ContextLJ.getSign(request); // 索签名
            String tiD = RandomUtil.getRandomString(3) + "_" + Thread.currentThread().getId();
            try {
                if (!(request instanceof MultipartHttpServletRequest)) {
                    if (!ContextLJ.lock(sign, tiD)) {
                        log.warn("放弃相同 并发请求" + sign);
                        frequentlyError(myResp);
                        return;
                    }
                    if (!ContextLJ.checklock(sign, tiD)) {
                        log.warn("加锁验证失败  " + sign + " " + tiD);
                        frequentlyError(myResp);
                        return;
                    }
                } else { // 文件上传不控制并发
                    log.warn("文件上传");
                }
                saveRequest((HttpServletRequest) request);
                try {
                    //user = loginService.checkLoginKey((HttpServletRequest) request);
                } catch (Exception e) {
                    e.printStackTrace();
                    loginError(myResp);
                    return;
                }
                chain.doFilter(request, myResp);
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                ContextLJ.clent(sign, tiD);
            }
            if ("jsonp".equalsIgnoreCase(_pleaseReturnDataType)) {
                myResp.getOutputStream().write(new String(")").getBytes("utf-8"));
            }
            try {
                //logSerfvice.cacheLog((HttpServletRequest) request, myResp, user, (Calendar.getInstance().getTimeInMillis() - btime));
            } catch (Exception e) {
                log.error("重要bug 日志保存失败", e);
            }
		    log.debug("URI->" + ((HttpServletRequest) request).getRequestURI());
        } finally {
            removeRequest();	// 清除缓存
        }
	}
	
	@Override
	public void destroy() {
		
	}
	
	
	/**
	 * 获取 request
	 * @param req
	 * @return
	 */
	private ServletRequest getRequest(ServletRequest req){
		String enctype = req.getContentType();
		if (StringUtils.isNotBlank(enctype) && enctype.contains("multipart/form-data")) {
			// 返回 MultipartHttpServletRequest 用于获取 multipart/form-data 方式提交的请求中 上传的参数
			return multipartResolver.resolveMultipart((HttpServletRequest) req);
		}
		return req;
	}

	private void saveRequest(HttpServletRequest req) {
		ThreadLocal<HttpServletRequest> requestThreadLocal = new ThreadLocal<HttpServletRequest>();
		requestThreadLocal.set(req);
		Map<Thread, ThreadLocal<HttpServletRequest>> threadLocalMap =  ContextHolder.getThreadLocalMap();
		threadLocalMap.put(Thread.currentThread(), requestThreadLocal);
	}
	
	private void removeRequest() {
		Map<Thread, ThreadLocal<HttpServletRequest>> threadLocalMap = ContextHolder.getThreadLocalMap();
		threadLocalMap.remove(Thread.currentThread());
	}

    /**
     * loginKey 为空
     * @param myResp
     */
    private void loginError(ServletResponse myResp) throws IOException {
        ApiResult<Object> re = new ApiResult<>();
        ((HttpServletResponse) myResp).setHeader("Content-type", "text/html;charset=UTF-8");
        re.setMsg("未登录");
        re.setCode(ApiRtnCode.DATA_VALIDATION_FAILURE);
        myResp.getOutputStream().write(new Gson().toJson(re).getBytes("utf-8"));
    }

    /**
     * 频繁请求
     * @param / myResp
     */
    private void frequentlyError(ServletResponse myResp) throws IOException {
        ApiResult<Object> re = new ApiResult<>();
        ((HttpServletResponse) myResp).setHeader("Content-type", "text/html;charset=UTF-8");
        re.setMsg("稍安勿躁，不要频繁请求");
        re.setCode(ApiRtnCode.API_VERIFY_FAIL);
        myResp.getOutputStream().write(new Gson().toJson(re).getBytes("utf-8"));
    }
}
