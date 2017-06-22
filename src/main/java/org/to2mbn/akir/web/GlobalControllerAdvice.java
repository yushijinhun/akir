package org.to2mbn.akir.web;

import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.web.util.RedirectUrlBuilder;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.to2mbn.akir.core.service.ServerInfo;

@ControllerAdvice
public class GlobalControllerAdvice {

	@Autowired
	private ServerInfo serverInfo;

	@ModelAttribute("akir_server")
	public ServerInfo akirServer() {
		return serverInfo;
	}

	@ModelAttribute("home_page_url")
	public String homePageUrl(HttpServletRequest request) {
		RedirectUrlBuilder urlBuilder = new RedirectUrlBuilder();
		urlBuilder.setScheme(request.getScheme());
		urlBuilder.setServerName(request.getServerName());
		urlBuilder.setPort(request.getServerPort());
		urlBuilder.setContextPath(request.getContextPath());
		return urlBuilder.getUrl();
	}

}
