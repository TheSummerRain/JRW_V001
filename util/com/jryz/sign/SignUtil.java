package com.jryz.sign;


import javax.servlet.http.HttpServletRequest;
import java.util.*;

/**
 * 
 * http 请求 签名工具
 * @author jryc
 *
 */
public class SignUtil {
	
	/**
	 * 生成签名结果
	 * @param sPara 要签名的数组
	 * @return 签名结果字符串
	 * @throws Exception 
	 */
	public static String buildRequest(Map<String, String> sPara, String key, SignType type) throws Exception {
		String prestr = createLinkString(sPara); // 把数组所有元素，按照“参数=参数值”的模式用“&”字符拼接成字符串
		prestr += (key == null ? "" : key);
		return sign(prestr, type);
	}
	
	/**
	 * 生成签名结果
	 * 把容器所有元素，按照“参数=参数值”的模式用“&”字符拼接成字符串 然后进行加密
	 * @param sPara 要签名的数组
	 * @throws Exception 
	 */
	public static String buildRequest(Map<String, String> sPara, SignType type) throws Exception {
		String prestr = createLinkString(sPara); // 把数组所有元素，按照“参数=参数值”的模式用“&”字符拼接成字符串
		return sign(prestr, type);
	}
	
	public static Map<String, String> getRequstMap(HttpServletRequest req){
		Map<String,String> params = new HashMap<String,String>();
		Map<String, String[]> requestParams = req.getParameterMap();
		for (Iterator<String> iter = requestParams.keySet().iterator(); iter.hasNext();) {
			String name = iter.next();
			String[] values = requestParams.get(name);
			String valueStr = "";
			for (int i = 0; i < values.length; i++) {
				valueStr = (i == values.length - 1) ? valueStr + values[i]
						: valueStr + values[i] + ",";
			}
			params.put(name, valueStr);
		}
		return params;
	}

    public static String createLinkString(HttpServletRequest req){
        Map<String, String[]> requestParams = req.getParameterMap();
        String prestr = "";
        for (Iterator<String> iter = requestParams.keySet().iterator(); iter.hasNext();) {
            String name = iter.next();
            String[] values = requestParams.get(name);
            String valueStr = "";
            for (int i = 0; i < values.length; i++) {
                valueStr = (i == values.length - 1) ? valueStr + values[i]
                        : valueStr + values[i] + ",";
            }
            prestr = prestr + name + "=" + valueStr + "&";
        }
        return prestr;
    }

	public static String sign(String text, SignType type) throws Exception{
		switch (type) {
		case MD5:
			return MD5Util.getMD5(text);
		case SHA1:
			return SHA1Utils.encrypt(text);
		}
		return null;
	}
    
	/** 
     * 把数组所有元素排序，并按照“参数=参数值”的模式用“&”字符拼接成字符串
     * @param params 需要排序并参与字符拼接的参数组
     * @return 拼接后字符串
     */
    public static String createLinkString(Map<String, String> params) {

        List<String> keys = new ArrayList<String>(params.keySet());
        Collections.sort(keys);

        String prestr = "";

        for (int i = 0; i < keys.size(); i++) {
            String key = keys.get(i);
            String value = params.get(key);

            if (i == keys.size() - 1) {//拼接时，不包括最后一个&字符
                prestr = prestr + key + "=" + value;
            } else {
                prestr = prestr + key + "=" + value + "&";
            }
        }

        return prestr;
    }
    
    public static void main(String[] args) throws Exception {
		//System.out.println(SignUtil.sign("73da7bb9d2a475bbc2ab79da7d4e94940cb9f9d5", SignType.SHA1));
		Map<String, String> params = new HashMap<>();
		params.put("noncestr", "Wm3WZYTPz0wzccnW");
		params.put("jsapi_ticket", "sM4AOVdWfPE4DxkXGEs8VMCPGGVi4C3VM0P37wVUCFvkVAy_90u5h9nbSlYy3-Sl-HhTdfl2fzFy1AOcHKP7qg");
		params.put("timestamp", "1414587457");
		params.put("url", "http://mp.weixin.qq.com?params=value");
		System.out.println(SignUtil.buildRequest(params, SignType.SHA1));
	}
}
