package org.shan.spring.base;

import com.mlnx.common.utils.MyLog;

import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.ServletContext;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class BaseWebController {
	
	protected MyLog logger = MyLog.getLog(getClass());

	public ServletContext getServletContext(){
		return getRequest().getServletContext();
	}

	public HttpServletRequest getRequest() {

		return ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
	}

	public HttpSession getSession() {
		return getRequest().getSession();
	}

	public HttpServletResponse getResponse() {
		return  ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getResponse();
	}

	protected String getCookie(String cookieName) {
		Cookie[] cookies = getRequest().getCookies();
		for (Cookie cookie : cookies) {
			if (cookieName.equals(cookie.getName())) {
				return cookie.getValue();
			}
		}

		return null;
	}

	/**
	 * 设置 cookie
	 *
	 * @param cookieName
	 * @param value
	 * @param age
	 */
	protected void setCookie(String cookieName, String value, Integer age) {
		Cookie cookie = new Cookie(cookieName, value);
		if (age != null) {
			cookie.setMaxAge(age);
		}
		// cookie.setHttpOnly(true);
		getResponse().addCookie(cookie);
	}

	/**
	 * 设置 cookie
	 *
	 * @param cookieName
	 * @param value
	 */
	protected void setCookie(String cookieName, String value) {
		setCookie(cookieName, value, null);
	}



	/**
	 * 重定向至地址 url
	 * @param url 请求地址
	 * @return
	 */
	protected String redirectTo(String url) {
		StringBuilder rto = new StringBuilder("redirect:");
		rto.append(url);
		return rto.toString();
	}
}
