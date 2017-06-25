package org.to2mbn.akir.web.login;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.LoginUrlAuthenticationEntryPoint;
import org.springframework.security.web.util.RedirectUrlBuilder;
import org.springframework.stereotype.Component;
import org.to2mbn.akir.core.service.AkirConfig;
import org.to2mbn.akir.web.util.OneTimeCookie;

@Component
public class ReturnableLoginUrlAuthenticationEntryPoint extends LoginUrlAuthenticationEntryPoint {

	public static final String LOGIN_RETURN_URL = "login_return_url";

	@Autowired
	private AkirConfig config;

	public ReturnableLoginUrlAuthenticationEntryPoint() {
		super("/login");
	}

	@Override
	public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
		OneTimeCookie.put(response, LOGIN_RETURN_URL, buildReturnUrl(request));
		super.commence(request, response, authException);
	}

	private String buildReturnUrl(HttpServletRequest request) {
		RedirectUrlBuilder urlBuilder = config.rootUrl();
		urlBuilder.setServletPath(request.getServletPath());
		urlBuilder.setPathInfo(request.getPathInfo());
		urlBuilder.setQuery(request.getQueryString());
		return urlBuilder.getUrl();
	}

}
