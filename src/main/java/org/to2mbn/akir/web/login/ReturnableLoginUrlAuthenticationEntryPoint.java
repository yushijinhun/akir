package org.to2mbn.akir.web.login;

import static java.nio.charset.StandardCharsets.UTF_8;
import static javax.servlet.http.HttpServletResponse.SC_FORBIDDEN;
import static org.to2mbn.akir.web.util.WebUtils.ERROR_ATTRIBUTE;
import static org.to2mbn.akir.web.util.WebUtils.isAjax;
import static org.to2mbn.akir.web.util.exception.ErrorMessage.E_ACCESS_DENIED;
import java.io.IOException;
import java.net.URI;
import java.util.Base64;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.LoginUrlAuthenticationEntryPoint;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.to2mbn.akir.core.service.AkirConfiguration;
import org.to2mbn.akir.web.util.OneTimeCookie;

@Component
public class ReturnableLoginUrlAuthenticationEntryPoint extends LoginUrlAuthenticationEntryPoint {

	public static final String LOGIN_RETURN_URL = "login_return_url";

	public static String decodeReturnUrl(String encoded) {
		return new String(Base64.getDecoder().decode(encoded), UTF_8);
	}

	private URI rootUri;

	public ReturnableLoginUrlAuthenticationEntryPoint() {
		super("/login");
	}

	@Autowired
	private void setRootUri(AkirConfiguration config) {
		rootUri = URI.create(config.getUrl());
	}

	@Override
	public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
		if (isAjax(request)) {
			request.setAttribute(ERROR_ATTRIBUTE, authException);
			response.sendError(SC_FORBIDDEN, E_ACCESS_DENIED);
		} else {
			OneTimeCookie.put(response, LOGIN_RETURN_URL,
					Base64.getUrlEncoder().encodeToString(
							buildReturnUrl(request).getBytes(UTF_8)));
			super.commence(request, response, authException);
		}
	}

	private String buildReturnUrl(HttpServletRequest request) {
		return ServletUriComponentsBuilder.fromRequest(request)
				.scheme(rootUri.getScheme())
				.host(rootUri.getHost())
				.port(rootUri.getPort())
				.toUriString();
	}

}
