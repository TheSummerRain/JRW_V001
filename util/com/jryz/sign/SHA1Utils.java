package com.jryz.sign;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * sha1 加密方法
 * @author jryc
 *
 */
public class SHA1Utils {
	 
	/**  
     * 将字节数组转换成16进制字符串  
     * @param b  
     * @return  
     */    
	public static String byte2hex(byte[] b) {
        StringBuilder sbDes = new StringBuilder();    
        String tmp = null;    
        for (int i = 0; i < b.length; i++) {  
            tmp = (Integer.toHexString(b[i] & 0xFF));    
            if (tmp.length() == 1) {    
                sbDes.append("0");    
            }    
            sbDes.append(tmp);    
        }    
        return sbDes.toString();    
    }
        
    public static String encrypt(String strSrc) throws NoSuchAlgorithmException, UnsupportedEncodingException {
    	MessageDigest digest = MessageDigest.getInstance("SHA-1");  
        String strDes = null;    
        byte[] bt = strSrc.getBytes("utf-8");
        digest.update(bt);    
        strDes = byte2hex(digest.digest());    
        return strDes;    
    }    
}
