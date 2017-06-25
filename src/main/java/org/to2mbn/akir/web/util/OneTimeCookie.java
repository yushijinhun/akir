package org.to2mbn.akir.web.util;

import java.util.Optional;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public final class OneTimeCookie {

	private OneTimeCookie() {}

	public static void put(HttpServletResponse response, String key, String value) {
		Cookie cookie = new Cookie(key, value);
		cookie.setPath("/");
		cookie.setHttpOnly(true);
		response.addCookie(cookie);
	}

	public static Optional<String> consume(HttpServletRequest request, HttpServletResponse response, String key) {
		Cookie cookie = org.springframework.web.util.WebUtils.getCookie(request, key);
		if (cookie == null) return Optional.empty();
		cookie.setMaxAge(0);
		response.addCookie(cookie);
		return Optional.of(cookie.getValue());
	}

}