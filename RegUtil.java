package com.ljzforum.platform.util;



import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegUtil {
	public static boolean validatePhoneNumber(String mobile) {
		Pattern p = Pattern
				.compile("^((14[0-9])|(13[0-9])|(15[0-9])|(14[0-9])|(17[0-9])|(18[0-9]))\\d{8}$");
		Matcher m = p.matcher(mobile);
		return m.matches();
	}
	public static boolean isChineseChar(String str){
        boolean temp = false;
        Pattern p=Pattern.compile("[\u4e00-\u9fa5]"); 
        Matcher m=p.matcher(str); 
        if(m.find()){ 
            temp =  true;
        }
        return temp;
    }
	public static void main(String[] args) {
		System.out.println(RegUtil.validatePhoneNumber("18121022010"));
	}
}
