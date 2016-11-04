package com.example.spring.services.utils;

import javax.servlet.http.Cookie;

public class CookieUtils {

	public static String getCookie(Cookie cookies[], String name) {
		for (Cookie cookie : cookies) {
			if (cookie.getName().equals(name)) {
				return cookie.getValue();
			}
		}
		return null;
	}
}
