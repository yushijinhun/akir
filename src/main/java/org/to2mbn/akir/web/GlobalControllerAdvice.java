package org.to2mbn.akir.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.to2mbn.akir.core.model.User;
import org.to2mbn.akir.core.service.AkirConfiguration;

@ControllerAdvice
public class GlobalControllerAdvice {

	@Autowired
	private AkirConfiguration serverInfo;

	@ModelAttribute("akir_server")
	public AkirConfiguration akirServer() {
		return serverInfo;
	}

	@ModelAttribute("user")
	public User user(User user) {
		return user;
	}

}
