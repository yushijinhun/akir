package org.to2mbn.akir.web.login;

import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.util.RedirectUrlBuilder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AjaxLoginController {

	@Autowired
	private AuthenticationManager authProvider;

	@PostMapping("/login")
	public LoginResponse login(@RequestBody LoginRequest req, HttpServletRequest request) {
		Authentication auth = authProvider.authenticate(new UsernamePasswordAuthenticationToken(req.getEmail(), req.getPassword()));
		SecurityContextHolder.getContext().setAuthentication(auth);

		LoginResponse response = new LoginResponse();
		response.setReturnUrl(
				ReturnableLoginUrlAuthenticationEntryPoint.getReturnUrl(request)
						.orElseGet(() -> getHomeUrl(request)));
		return response;
	}

	private String getHomeUrl(HttpServletRequest request) {
		RedirectUrlBuilder homeUrl = new RedirectUrlBuilder();
		homeUrl.setScheme(request.getScheme());
		homeUrl.setServerName(request.getServerName());
		homeUrl.setPort(request.getServerPort());
		homeUrl.setContextPath(request.getContextPath());
		return homeUrl.getUrl();
	}

}
