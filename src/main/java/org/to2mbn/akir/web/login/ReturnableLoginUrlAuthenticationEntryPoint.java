package org.to2mbn.akir.web.login;

import static javax.servlet.http.HttpServletResponse.SC_FORBIDDEN;
import static org.to2mbn.akir.web.util.WebUtils.ERROR_ATTRIBUTE;
import static org.to2mbn.akir.web.util.WebUtils.isAjax;
import static org.to2mbn.akir.web.util.exception.ErrorMessage.E_ACCESS_DENIED;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.LoginUrlAuthenticationEntryPoint;
import org.springframework.security.web.util.RedirectUrlBuilder;
import org.springframework.stereotype.Component;
import org.to2mbn.akir.core.service.AkirConfiguration;
import org.to2mbn.akir.web.util.OneTimeCookie;

@Component
public class ReturnableLoginUrlAuthenticationEntryPoint extends LoginUrlAuthenticationEntryPoint {

	public static final String LOGIN_RETURN_URL = "login_return_url";

	@Autowired
	private AkirConfiguration config;

	public ReturnableLoginUrlAuthenticationEntryPoint() {
		super("/login");
	}

	@Override
	public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
		if (isAjax(request)) {
			request.setAttribute(ERROR_ATTRIBUTE, authException);
			response.sendError(SC_FORBIDDEN, E_ACCESS_DENIED);
		} else {
			OneTimeCookie.put(response, LOGIN_RETURN_URL, buildReturnUrl(request));
			super.commence(request, response, authException);
		}
	}

	private String buildReturnUrl(HttpServletRequest request) {
		RedirectUrlBuilder urlBuilder = config.rootUrl();
		urlBuilder.setServletPath(request.getServletPath());
		urlBuilder.setPathInfo(request.getPathInfo());
		urlBuilder.setQuery(request.getQueryString());
		return urlBuilder.getUrl();
	}

}
