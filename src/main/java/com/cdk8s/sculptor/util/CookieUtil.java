package com.cdk8s.sculptor.util;


import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.net.URLDecoder;
import java.net.URLEncoder;

@Slf4j
public final class CookieUtil {


	/**
	 * 设置 Cookie（生成时间为1天）
	 *
	 * @param name  名称
	 * @param value 值
	 */
	public static void setCookie(final HttpServletResponse response, String name, String value) {
		setCookie(response, name, value, 60 * 60 * 24);
	}

	/**
	 * 设置 Cookie（生成时间为1天）
	 *
	 * @param name  名称
	 * @param value 值
	 */
	public static void setCookie(final HttpServletResponse response, String name, String value, boolean httpOnly, boolean secure) {
		setCookie(response, name, value, "/", 60 * 60 * 24, httpOnly, secure);
	}

	public static void setCookie(final HttpServletResponse response, String name, String value, int maxAge, boolean httpOnly, boolean secure) {
		setCookie(response, name, value, "/", maxAge, httpOnly, secure);
	}

	/**
	 * 设置 Cookie
	 *
	 * @param name  名称
	 * @param value 值
	 */
	public static void setCookie(final HttpServletResponse response, String name, String value, String path) {
		setCookie(response, name, value, path, 60 * 60 * 24, false, false);
	}

	/**
	 * 设置 Cookie
	 *
	 * @param name   名称
	 * @param value  值
	 * @param maxAge 生存时间（单位秒）
	 */
	public static void setCookie(final HttpServletResponse response, String name, String value, int maxAge) {
		setCookie(response, name, value, "/", maxAge, false, false);
	}

	/**
	 * 设置 Cookie
	 *
	 * @param response
	 * @param name
	 * @param value
	 * @param path
	 * @param maxAge       生存时间（单位秒）
	 * @param httpOnly，如果为 true 那只用在 http 传输，JS 脚本无法读取，防止XSS攻击
	 * @param secure，如果为   true，只能使用在 https，传统 http 无法使用，防止中间人攻击
	 */
	@SneakyThrows
	public static void setCookie(final HttpServletResponse response, String name, String value, String path, int maxAge, boolean httpOnly, boolean secure) {
		Cookie cookie = new Cookie(name, null);
		cookie.setPath(path);
		cookie.setMaxAge(maxAge);
		cookie.setVersion(1);
		cookie.setHttpOnly(httpOnly);
		cookie.setSecure(secure);
		cookie.setValue(URLEncoder.encode(value, "utf-8"));
		response.addCookie(cookie);
	}


	/**
	 * 删除指定Cookie的值。
	 *
	 * @param name 名称
	 * @return 值
	 */
	public static void deleteCookie(final HttpServletRequest request, HttpServletResponse response, String name) {
		deleteCookie(request, response, name, "/");
	}

	/**
	 * 删除指定Cookie的值。
	 *
	 * @param name 名称
	 * @return 值
	 */
	public static void deleteCookie(final HttpServletRequest request, HttpServletResponse response, String name, String path) {
		Cookie[] cookies = request.getCookies();
		if (cookies != null) {
			for (Cookie cookie : cookies) {
				if (cookie.getName().equals(name)) {
					cookie.setPath(path);
					cookie.setValue("");
					cookie.setMaxAge(0);
					response.addCookie(cookie);
				}
			}
		}
	}

	// ======================================================

	/**
	 * 获得指定Cookie的值
	 *
	 * @param name 名称
	 * @return 值
	 */
	public static String getCookie(final HttpServletRequest request, String name) {
		return getCookie(request, null, name, false);
	}

	/**
	 * 获得指定Cookie的值，并删除。
	 *
	 * @param name 名称
	 * @return 值
	 */
	public static String getCookieWithDelete(final HttpServletRequest request, HttpServletResponse response, String name) {
		return getCookie(request, response, name, true);
	}

	@SneakyThrows
	public static String getCookie(final HttpServletRequest request, final HttpServletResponse response, String name, boolean isRemove) {
		String value = null;
		Cookie[] cookies = request.getCookies();
		if (cookies != null) {
			for (Cookie cookie : cookies) {
				if (StringUtil.equalsIgnoreCase(cookie.getName(), name)) {
					value = URLDecoder.decode(cookie.getValue(), "utf-8");
					if (isRemove) {
						cookie.setMaxAge(0);
						response.addCookie(cookie);
					}
				}
			}
		}
		return value;
	}
}

