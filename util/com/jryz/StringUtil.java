package com.jryz;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.Validate;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

public class StringUtil {

    public static void main(String[] args) {
        System.out.println(getBeanNameByTableName("tab_name"));
        System.out.println(getBeanNameByTableName("core_pr_coupon_user"));
    }

    /**
     * 通过字段名 获取 bean 的名字
     * @param fiedlName
     * @return
     */
    public static String getBeanNameByFiedlName(String fiedlName) {
        char[] chars = fiedlName
                .toCharArray();
        for (int i = 0; i < chars.length; i++) {
            char c = chars[i];
            if (c == '_' && i < chars.length - 1) {
                chars[i + 1] = Character.toUpperCase(chars[i + 1]);
                i++;
            }
        }
        fiedlName = new String(chars);
        return fiedlName.replace("_", "");
    }

    /**
     * 通过表名 获取 bean 的名字
     * @param tableName
     * @return
     */
    public static String getBeanNameByTableName(String tableName) {
        char[] chars = tableName
                .replace("pr_", "")
                .replace("tab_", "")
                .replace("go_", "")
                .replace("core_", "")
                .toCharArray();
        for (int i = 0; i < chars.length; i++) {
            char c = chars[i];
            if (c == '_' && i < chars.length - 1) {
                chars[i + 1] = Character.toUpperCase(chars[i + 1]);
                i++;
            }
        }
        tableName = new String(chars);
        return tableName.replace("_", "");
    }

    public static String oneToLowercase(String str) {
        char[] chars = new char[1];
        chars[0]= str.charAt(0);
        String temp = new String(chars);
        if (chars[0] >= 'A' && chars[0] <= 'Z') {
            return str.replaceFirst(temp, temp.toLowerCase());
        }
        return str;
    }

    /**
	 * 把中文转成Unicode码
	 * 
	 * @param str
	 * @return
	 */
	public static String chinaToUnicode(String str) {
		String result = "";
		for (int i = 0; i < str.length(); i++) {
			int chr1 = str.charAt(i);
			if (chr1 >= 19968 && chr1 <= 171941) {// 汉字范围 \u4e00-\u9fa5 (中文)
				result += "\\u" + Integer.toHexString(chr1);
			} else {
				result += str.charAt(i);
			}
		}
		return result;
	}

	/**
	 * 将unicode 字符串
	 * 
	 * @param str
	 *            待转字符串
	 * @return 普通字符串
	 */
	public static String unicodeToChina(String str) {
		str = (str == null ? "" : str);
		if (str.indexOf("\\u") == -1)// 如果不是unicode码则原样返回
        {
            return str;
        }

		StringBuffer sb = new StringBuffer(1000);

		for (int i = 0; i < str.length() - 6;) {
			String strTemp = str.substring(i, i + 6);
			String value = strTemp.substring(2);
			int c = 0;
			for (int j = 0; j < value.length(); j++) {
				char tempChar = value.charAt(j);
				int t = 0;
				switch (tempChar) {
				case 'a':
					t = 10;
					break;
				case 'b':
					t = 11;
					break;
				case 'c':
					t = 12;
					break;
				case 'd':
					t = 13;
					break;
				case 'e':
					t = 14;
					break;
				case 'f':
					t = 15;
					break;
				default:
					t = tempChar - 48;
					break;
				}

				c += t * ((int) Math.pow(16, (value.length() - j - 1)));
			}
			sb.append((char) c);
			i = i + 6;
		}
		return sb.toString();
	}

	/**
	 * 判断是否为中文字符
	 * 
	 * @param c
	 * @return
	 */
	public static boolean isChinese(char c) {
		Character.UnicodeBlock ub = Character.UnicodeBlock.of(c);
		if (ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS
				|| ub == Character.UnicodeBlock.CJK_COMPATIBILITY_IDEOGRAPHS
				|| ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_A
				|| ub == Character.UnicodeBlock.GENERAL_PUNCTUATION
				|| ub == Character.UnicodeBlock.CJK_SYMBOLS_AND_PUNCTUATION
				|| ub == Character.UnicodeBlock.HALFWIDTH_AND_FULLWIDTH_FORMS) {
			return true;
		}
		return false;
	}
	
	public static String fixNull(String s) {
		if(StringUtils.isBlank(s)) {
			return "";
		}
		return s;
	}
	
	public static String fixNull(String str, String str1) {
        if (str == null) {
            return str1;
        }
        return str;
	}
	
	//去除数组中重复的记录
	public static String[] removeRepeat(String[] a) {
	    List<String> list = new LinkedList<String>();
	    for(int i = 0; i < a.length; i++) {
	        if(!list.contains(a[i])) {
	            list.add(a[i]);
	        }
	    }
	    return list.toArray(new String[list.size()]);
	}
	
	public static String arrayToString(String[] a, String separator) {
		String result = "";
		for (String s : a) {
			result += separator + s;
		}
		result = result.replaceFirst(separator, "");
		return result;
	}
	
	public static String removeFirst(String s) {
		if (s != null && s.length() > 0) {
			return s.substring(1, s.length());
		}
		return s;
	}
	
	public static String removeFirst(String s, String startWith) {
		if (s != null && s.startsWith(startWith)) {
			return s.substring(startWith.length(), s.length());
		}
		return s;
	}
	
	public static String removeEnd(String s) {
		if (s != null && s.length() > 0) {
			return s.substring(0, s.length() - 1);
		}
		return s;
	}
	
	public static String removeEnd(String s, String endWith) {
		if (s != null && s.endsWith(endWith)) {
			return s.substring(0, s.length() - endWith.length());
		}
		return s;
	}
	
	/**
	 * 
	 * @param args
	 * @return 返回错误提示信息
	 */
	public static String notNullOrBlank(String msg, String... args) {
		String r = "";
		try {
			for (String s : args) {
				Validate.notNull(s, msg);
				Validate.notEmpty(s, msg);
			}
		} catch (Exception e) {
			r = e.getMessage();
		}
		return r;
	}
	
	@SuppressWarnings("rawtypes")
	public static String notNullOrBlank(Collection coll, String msg) {
		String r = "";
		try {
			Validate.notNull(coll, msg);
			Validate.notEmpty(coll, msg);
		} catch (Exception e) {
			r = e.getMessage();
		}
		return r;
	}
	
	public static boolean isBlank(String str) {
		if (str == null || "".equals(str.trim()) || str.length() == 0) {
			return true;
		}
		return false;
	}
	
	public static boolean isNotBlank(String str){
		return !isBlank(str);
	}
	
	public static String appendIfNotNull(String oldStr, String appendStr) {
		if (isNotBlank(appendStr)) {
			return oldStr + appendStr;
		}
		return oldStr;
	}
	
	public static boolean appendIfNotNull(StringBuffer str, String oldStr, String appendStr) {
		if (isNotBlank(oldStr)) {
			str.append(appendStr);
			return true;
		}
		return false;
	}
	
	public static boolean startIn(String str, String[] starts) {
		for (String st : starts) {
            if (str.startsWith(st)) {
                return true;
            }
        }
		return false;
	}
	
}
