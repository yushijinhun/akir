package org.to2mbn.akir.web.login;

import java.io.IOException;
import java.util.Optional;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.LoginUrlAuthenticationEntryPoint;
import org.springframework.security.web.util.RedirectUrlBuilder;

public class ReturnableLoginUrlAuthenticationEntryPoint extends LoginUrlAuthenticationEntryPoint {

	public static final String LOGIN_RETURN_URL = "login_return_url";

	public static Optional<String> consumeReturnUrl(HttpServletRequest request) {
		HttpSession session = request.getSession();
		String returnUrl = (String) session.getAttribute(LOGIN_RETURN_URL);
		if (returnUrl != null)
			session.removeAttribute(LOGIN_RETURN_URL);
		return Optional.ofNullable(returnUrl);
	}

	public ReturnableLoginUrlAuthenticationEntryPoint(String loginFormUrl) {
		super(loginFormUrl);
	}

	@Override
	public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
		request.getSession().setAttribute(LOGIN_RETURN_URL, buildReturnUrl(request));
		super.commence(request, response, authException);
	}

	private String buildReturnUrl(HttpServletRequest request) {
		RedirectUrlBuilder urlBuilder = new RedirectUrlBuilder();
		urlBuilder.setScheme(request.getScheme());
		urlBuilder.setServerName(request.getServerName());
		urlBuilder.setPort(request.getServerPort());
		urlBuilder.setContextPath(request.getContextPath());
		urlBuilder.setServletPath(request.getServletPath());
		urlBuilder.setPathInfo(request.getPathInfo());
		urlBuilder.setQuery(request.getQueryString());
		return urlBuilder.getUrl();
	}

}