package org.to2mbn.akir.web;

import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.to2mbn.akir.core.model.User;
import org.to2mbn.akir.core.service.AkirConfig;

@ControllerAdvice
public class GlobalControllerAdvice {

	@Autowired
	private AkirConfig serverInfo;

	@ModelAttribute("akir_server")
	public AkirConfig akirServer() {
		return serverInfo;
	}

	@ModelAttribute("home_page_url")
	public String homePageUrl(HttpServletRequest request) {
		return serverInfo.getUrl();
	}

	@ModelAttribute("user")
	public User user(User user) {
		return user;
	}

}
