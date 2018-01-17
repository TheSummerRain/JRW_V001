package com.jryz.web;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;

/**
 * @author 杨超
 */
public class WebProjectUtil {
	
	private static Logger log = Logger.getLogger(WebProjectUtil.class);
	
	private static String CLASSES_FLODER_PATH = WebProjectUtil.class.getProtectionDomain().getCodeSource().getLocation().getFile();
    private static String webrootPath = null;
    private static String WEB_NAME = null;
	private static String PROJECT_URL = null;

    /**
     * 获取 项目名称
     * @return
     */
    public static String getWebName(){
        if (WEB_NAME != null) {
            return WEB_NAME;
        }
        String classPath = CLASSES_FLODER_PATH.split("WEB-INF")[0];
        String f = classPath.substring(classPath.length() - 1, classPath.length());
		String[] s = classPath.split(f);
		int i = s.length;
        WEB_NAME = ("WebRoot".equalsIgnoreCase(s[i - 1]) ? s[i - 2] : s[i - 1]).toUpperCase();
        return WEB_NAME;
    }

	/**
	 * 获取项目网址根目录如
	 * http://127.0.0.1:8080/qyjson
	 * @param req
	 * @return
	 */
	public static String getProjectUrl(HttpServletRequest req){
		String path = req.getContextPath();
		PROJECT_URL = req.getScheme() + "://" + req.getServerName() + ":"+ req.getServerPort() + path;
		return PROJECT_URL;
	}
	
	//获取项目下 WebRoot 物理路径
	public static String getWebrootPath(){
        if (webrootPath != null) {
            return webrootPath;
        }

        webrootPath = CLASSES_FLODER_PATH.split("WEB-INF")[0];
		String osName = System.getProperty("os.name").toLowerCase();
		if (osName.contains("windows")) {
			webrootPath = webrootPath.substring(1);
		}
		
		log.debug(" webrootPath： " + webrootPath);
		return webrootPath;
	}
	
	/**
	 * 获取访问者IP
	 * 
	 * 在一般情况下使用Request.getRemoteAddr()即可，但是经过nginx等反向代理软件后，这个方法会失效。
	 * 
	 * 本方法先从Header中获取X-Real-IP，如果不存在再从X-Forwarded-For获得第一个IP(用,分割)，
	 * 如果还不存在则调用Request .getRemoteAddr()。
	 * 
	 * @param request
	 * @return
	 */
	public static String getIpAddr(HttpServletRequest request) throws Exception{
		String ip = request.getHeader("X-Real-IP");
		if (!StringUtils.isBlank(ip) && !"unknown".equalsIgnoreCase(ip)) {
			return ip;
		}
		ip = request.getHeader("X-Forwarded-For");
		if (!StringUtils.isBlank(ip) && !"unknown".equalsIgnoreCase(ip)) {
			// 多次反向代理后会有多个IP值，第一个为真实IP。
			int index = ip.indexOf(',');
			if (index != -1) {
				return ip.substring(0, index);
			} else {
				return ip;
			}
		} else {
			return request.getRemoteAddr();
		}
	}

    public static void main(String[] args) {
        System.out.println(getWebName());
    }
}
