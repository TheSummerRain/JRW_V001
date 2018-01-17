package com.jryz.web.filter;

import com.google.gson.Gson;
import com.jryz.redis.DbCode;
import com.jryz.redis.dao.RedisDao;
import com.jryz.sign.SignType;
import com.jryz.sign.SignUtil;
import com.jryz.spring.ApplicationContextUtil;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * 并发拦截
 * 高并发下 过滤掉 相同请求的工具
 * @author 杨超
 *
 */
public class ContextLJ {

    private static RedisDao dao = ApplicationContextUtil.getContext().getBean(RedisDao.class);

    /**
     * 上锁 使用redis 为分布式项目 加锁
     * @param sign 摘要
     * @param tiD  线程id 标识当前线程的身份
     * @return
     * @throws Exception
     */
    public static boolean lock(String sign, String tiD) {


        synchronized (dao) { // 加锁
            String uTid = dao.get(DbCode.SIGN, sign);
            if (uTid == null) {
                dao.set(DbCode.SIGN, sign, tiD);
                dao.expire(DbCode.SIGN, sign, 36);
                return true;
            }
            return false;
        }
    }

    /**
     * 锁验证 验证当前锁的拥有者 是不是 tid
     * @param sign
     * @param tiD
     * @return
     */
    public static boolean checklock(String sign, String tiD){
        String uTid = dao.get(DbCode.SIGN, sign);
        return tiD.equals(uTid);
    }

    /**
     * 释放锁
     * @param sign
     * @param tiD
     */
    public static void clent (String sign, String tiD){
        if (checklock(sign, tiD)) {
            dao.del(DbCode.SIGN, sign);
        }
    }

    public static void main(String[] args) {
        System.out.println(new Gson().toJson(new String[]{""}));
    }

    /**
     * 获取摘要
     * @param request
     * @return
     */
	public static String getSign(ServletRequest request){
        Map<String, String> map = SignUtil.getRequstMap((HttpServletRequest) request);
        String sign = null;
        try {
            sign = SignUtil.buildRequest(map, SignType.MD5);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return sign;
    }
}