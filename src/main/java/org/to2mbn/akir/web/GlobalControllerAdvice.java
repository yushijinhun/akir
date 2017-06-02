package org.to2mbn.akir.web;

import org.springframework.beans.factory.annotation.Autowired;
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

}
