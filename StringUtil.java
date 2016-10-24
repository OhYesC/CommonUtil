package com.ljzforum.platform.util;



import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;

/**
 * 
 * @author chuijian.kong
 * @date 2014年10月28日
 * @time 下午5:27:10
 * @version 1.0
 * @since 1.0
 */
public class StringUtil {
	
	/**
	 * 拼接电话号码
	 * @param phoneCode		//区号
	 * @param phoneNum		//号码
	 * @param phoneExtNo	//分机号
	 * @return
	 */
	public static String jointPhoneNum(String phoneCode, String phoneNum, String phoneExtNo){
		if(StringUtils.isBlank(phoneNum)){
			return phoneNum;
		}
		String phone = phoneNum;
		if(!StringUtils.isBlank(phoneCode)){
			phone = phoneCode + "-" + phone;
		}
		if(!StringUtils.isBlank(phoneExtNo)){
			phone = phone + "-" + phoneExtNo;
		}
		return phone;
	}
	/**
	 * 是否是有效数字
	 * @author chuijian.kong
	 * @date 2014年11月5日 上午9:34:44
	 * @version 1.0
	 */
	public static boolean isAvailableValue(Long value){
		return value != null && value.longValue() > 0;
	}
	/**
	 * 是否是有效数字
	 * @author chuijian.kong
	 * @date 2014年11月5日 上午9:35:08
	 * @version 1.0
	 */
	public static boolean isAvailableValue(Integer value){
		return value != null && value.intValue() >0;
	}
	public static String removeNumberZero(String value){
		 if(value.indexOf(".") > 0){
		     //正则表达
				 value =value.replaceAll("0+?$", "");//去掉后面无用的零
				 value = value.replaceAll("[.]$", "");//如小数点后面全是零则去掉小数点
		  }
		 return value;
	}
	 /**
     * 判断字符串是否是乱码
     *
     * @param strName 字符串
     * @return 是否是乱码
     */
    public static boolean isMessyCode(String strName) {
        Pattern p = Pattern.compile("\\s*|t*|r*|n*");
        Matcher m = p.matcher(strName);
        String after = m.replaceAll("");
        String temp = after.replaceAll("\\p{P}", "");
        char[] ch = temp.trim().toCharArray();
        float chLength = ch.length;
        float count = 0;
        for (int i = 0; i < ch.length; i++) {
            char c = ch[i];
            if (!Character.isLetterOrDigit(c)) {
                if (!isChinese(c)) {
                    count = count + 1;
                }
            }
        }
        float result = count / chLength;
        if (result > 0.4) {
            return true;
        } else {
            return false;
        }
 
    }
    /**
     * 判断字符是否是中文
     *
     * @param c 字符
     * @return 是否是中文
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
    public static String getEncoding(String str) {      
        String encode = "GB2312";      
       try {      
           if (str.equals(new String(str.getBytes(encode), encode))) {      
                String s = encode;      
               return s;      
            }      
        } catch (Exception exception) {      
        }      
        encode = "ISO-8859-1";      
       try {      
           if (str.equals(new String(str.getBytes(encode), encode))) {      
                String s1 = encode;      
               return s1;      
            }      
        } catch (Exception exception1) {      
        }      
        encode = "UTF-8";      
       try {      
           if (str.equals(new String(str.getBytes(encode), encode))) {      
                String s2 = encode;      
               return s2;      
            }      
        } catch (Exception exception2) {      
        }      
        encode = "GBK";      
       try {      
           if (str.equals(new String(str.getBytes(encode), encode))) {      
                String s3 = encode;      
               return s3;      
            }      
        } catch (Exception exception3) {      
        }      
       return "";      
    }      
    /** 
     * 手机号验证 
     *  
     * @param  str 
     * @return 验证通过返回true 
     */  
    public static boolean isMobile(String str) {   
        Pattern p = null;  
        Matcher m = null;  
        boolean b = false;   
        p = Pattern.compile("^[1][3,4,5,8][0-9]{9}$"); // 验证手机号  
        m = p.matcher(str);  
        b = m.matches();   
        return b;  
    }  
    /** 
     * 电话号码验证 
     *  
     * @param  str 
     * @return 验证通过返回true 
     */  
    public static boolean isPhone(String str) {   
        Pattern p1 = null,p2 = null;  
        Matcher m = null;  
        boolean b = false;    
        p1 = Pattern.compile("^[0][1-9]{2,3}-[0-9]{5,10}$");  // 验证带区号的  
        p2 = Pattern.compile("^[1-9]{1}[0-9]{5,8}$");         // 验证没有区号的  
        if(str.length() >9)  
        {   m = p1.matcher(str);  
            b = m.matches();    
        }else{  
            m = p2.matcher(str);  
            b = m.matches();   
        }    
        return b;  
    }  


}
