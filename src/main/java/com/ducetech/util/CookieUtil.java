package com.ducetech.util;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

/**
 * @author wang
 * 
 */
public class CookieUtil {
	public static final int PERSISTENTTIME = 3 * 30 * 24 * 3600;// 目前是三个月

	protected Logger log = LoggerFactory.getLogger(getClass());

	/**
	 * 从cookie里面获取用户登录信息
	 * 
	 * @param request
	 * @return
	 */
	public static String getLoginUserId(final HttpServletRequest request) {
		Cookie[] cookies = request.getCookies();
		if (cookies != null && cookies.length > 0) {
			// 遍历 cookies
			for (Cookie cooky : cookies) {
				if ("DT_LOGIN_USER".equals(cooky.getName())) {
					return cooky.getValue();
				}
			}
		}
		return null;
	}

	public static void deleteUserFromCookie(HttpServletResponse response) {
		CookieUtil.setCookie(response, "DT_LOGIN_USER", null);
	}

	/**
	 * 获取Cookie的值
	 */
	public static String getCookieValue(HttpServletRequest request, String key) {
		Cookie[] cookies = request.getCookies();
		if (cookies != null) {
			for (Cookie cooky : cookies) {
				if (key.equals(cooky.getName())) {
					return cooky.getValue();
				}
			}
		}
		return null;
	}
	
	   /**
     * 生成cookie
     */
	   public static void setCookie(HttpServletResponse response, String key, String value) {
		   if (value == null) {
            value = "";
        }
        try {
            value = URLEncoder.encode(value, "utf-8");
        } catch (UnsupportedEncodingException e) {
        }
		   if (StringUtils.isEmpty(key)) {
			   key = "DT_LOGIN_USER";
		   }
		   Cookie cookie = new Cookie(key, value);
       /* cookie.setSecure(secure); // 表示是否Cookie只能通过加密的连接（即SSL）发送。
		cookie.setPath(path); // 设置Cookie适用的路径
        cookie.setDomain(domain); // 设置Cookie适用的域
        cookie.setMaxAge(time); // 设置Cookie有效时间*/
        response.addCookie(cookie);
    }
	
}
