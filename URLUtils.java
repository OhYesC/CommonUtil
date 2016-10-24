package com.ljzforum.platform.util;



import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLEncoder;

import javax.servlet.http.HttpServletRequest;

import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;


/**
 * 取得class path跟路徑.
 * @author jinhua.li
 * @2014年10月21日  上午9:24:26
 */
public class URLUtils {
	private static ResourceLoader resourceLoader = new DefaultResourceLoader();
	
	/**
	 * 利用ResourceLoader取得excel路徑。
	 * @param fileName
	 * @return
	 */
	public static String getExcelFilePath(String fileName){
		Resource path = resourceLoader.getResource("classpath:export/" + fileName);//格式見getResource注释
		//Resource path = resourceLoader.getResource("/WEB-INF/classes/report/" + fileName);//也可
		try {
			return path.getURI().getPath();
		} catch (IOException e) {
			e.printStackTrace();
			return "";
		}
	}

	/**
	 * 取得class path跟路徑
	 * @return
	 */
	public static String getCLassPath(){
		String classpath = null;
		try {
			URL url = new Object(){}.getClass().getResource("/properties/log4j.properties");
			classpath = (new File(url.getFile())).getParentFile().getAbsolutePath();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return classpath;
	}
	
	
	/**
	 * 其中 Base64 类来自 org.apache.commons.codec 组件 一个40多k的jar 要比javamail里的那个简洁很多
	 * public static String encodeString(HttpServletRequest request, String cnStr)
            throws UnsupportedEncodingException {
    	  String agent = request.getHeader("USER-AGENT");
            if (null != agent && -1 != agent.indexOf("MSIE")) {
                return URLEncoder.encode(cnStr, "UTF8");
            } else if (null != agent && -1 != agent.indexOf("Mozilla")) {
                return "=?UTF-8?B?" + (new String(Base64.encodeBase64(cnStr.getBytes("UTF-8")))) + "?=";
            } else {
                return cnStr;
            }
         }
	 * @param request
	 * @param cnStr
	 * @return
	 * @throws UnsupportedEncodingException
	 */
    public static String encodeString(HttpServletRequest request, String cnStr)
            throws UnsupportedEncodingException {
        return URLEncoder.encode(cnStr, "UTF8");
    }
}
