package com.ljzforum.platform.util;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;

import com.ljzforum.platform.consts.PublicConst;


public class HttpUtil {
	
	/**
	 * 禁止浏览器缓存
	 */
	public static void noCache(HttpServletResponse response){
		response.setHeader("Cache-Control", "no-cache");  
		response.setHeader("Pragma", "no-cache");  
		response.setDateHeader("Expires", -1);
	}
	
	/**
	 * 删除客户端cookie
	 */
	public static void removeCookie(HttpServletRequest request, HttpServletResponse response, String cookieName){
		Cookie[] cookies = request.getCookies();
		if (cookies != null) {
			for (Cookie cookie : cookies) {
				if (cookieName.equals(cookie.getName())) {
					cookie.setValue(null);
					cookie.setMaxAge(0);
					cookie.setPath("/");
					response.addCookie(cookie);
				}
			}
		}
	}
	
	/**
	 * 判断request是否是ajax请求
	 */
	public static boolean isAjax(HttpServletRequest request){
		String header = request.getHeader("X-Requested-With");
		return  header != null  && "XMLHttpRequest".equals(header);
	}
	
	/**
	 * 获得跟路径
	 */
	public static String getBasePath(HttpServletRequest request){
		return  request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath();
	}
	
	/**
	 * 获得request cookie
	 */
	public static Map<String, String> getCookie(HttpServletRequest request) {
		Cookie[] cookies = request.getCookies();
		if (cookies == null) {
			return null;
		}

		Map<String, String> cookieMap = new HashMap<String, String>();
		for (Cookie cookie : cookies) {
			cookieMap.put(cookie.getName(), cookie.getValue());
		}
		return cookieMap;
	}
	
	/**
	 * 获得IP
	 */
	public static String getIpAddr(HttpServletRequest request) { 
	    String ip = request.getHeader("x-forwarded-for"); 
	    if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) { 
	        ip = request.getHeader("Proxy-Client-IP"); 
	    } 
	    if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) { 
	        ip = request.getHeader("WL-Proxy-Client-IP"); 
	    } 
	    if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) { 
	        ip = request.getRemoteAddr(); 
	    } 
	    String[] splitIp = ip.split(",");
	    return splitIp[splitIp.length -1 ]; 
	}
	
	public static String getCookiesHead(HttpServletRequest request) {
		Enumeration<String> enumeration = request.getHeaders("cookie");
		StringBuilder builder = new StringBuilder();
		while (enumeration.hasMoreElements()) {
			builder.append(enumeration.nextElement());
		}
		return builder.toString();
	}
	
	public static String getDomain() {
		return PublicConst.PUBLIC_DOMAIN;
	}
	
	public static String getUrlParam(Map<String, Object> map) {
		if (map != null) {
			StringBuffer params = new StringBuffer(); 
			boolean flag = true;
			for (Entry<String, Object> entry : map.entrySet()) {
				if (flag) {
					flag = false;
				}else{
					params.append("&"); 
				}
				params.append(entry.getKey()).append("=").append(entry.getValue()); 
			}
			return params.toString();
		}
		return "";
	}
	
	public static void addCookie(String name, String value, HttpServletRequest request, HttpServletResponse response) {
		final Cookie cookie = new Cookie(name, value);
		cookie.setDomain(HttpUtil.getDomain());
		cookie.setPath("/");
		response.addCookie(cookie);
	}
	
	public static boolean hasCookie(String name, HttpServletRequest request) {
		Cookie[] cookies = request.getCookies();
		if (cookies != null) {
			for (Cookie cookie : cookies) {
				if (name.equals(cookie.getName())) {
					return true;
				}
			}
		}
		return false;
	}
	
	public static String getCookie(String name, HttpServletRequest request) {
		Cookie[] cookies = request.getCookies();
		if (cookies != null) {
			for (Cookie cookie : cookies) {
				if (name.equals(cookie.getName())) {
					return cookie.getValue();
				}
			}
		}
		return null;
	}
	
}
