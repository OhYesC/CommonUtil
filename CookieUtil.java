package com.ljzforum.platform.util;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class CookieUtil {
	/**
	 * 保存cookie值
	 */
	public static void setStatus(String cookieName, String value,
			HttpServletRequest request, HttpServletResponse response) {
		clearCookie(cookieName, request,response);
		Cookie cookie = new Cookie(cookieName, value);
		cookie.setPath("/");
		cookie.setDomain("ljzforum.com");
		response.addCookie(cookie);
	}

	/**
	 * 获取指定cookie值
	 */
	public static String getStatus(String cookieName, HttpServletRequest request)
			throws UnsupportedEncodingException {
		Cookie[] cookies = (Cookie[]) request.getCookies(); // 获取所有cookie
		if (cookies != null) {
			for (Cookie cookie : cookies) {
				if (cookieName.equals(cookie.getName())) {
					return URLDecoder.decode(cookie.getValue(), "UTF-8"); // 返回cookie值
				}
			}
		}
		return null;

	}

	/**
	 * 清除指定cookie
	 */
	public static void clearCookie(String cookieName, HttpServletRequest request,HttpServletResponse response) {
		Cookie[] cookies = (Cookie[]) request.getCookies(); // 获取所有cookie
		if (cookies != null) {
			for (Cookie cookie : cookies) {
				if (cookieName.equals(cookie.getName())) {
					Cookie newCookie = new Cookie(cookieName, null);
					newCookie.setMaxAge(0);
					newCookie.setPath("/");
					newCookie.setDomain("ljzforum.com");
					response.addCookie(newCookie);
					break;
				}
			}
		}
	}
}
