package org.to2mbn.akir.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

// TODO: to be removed
@Controller
public class MainController {

	@RequestMapping("/register")
	public String register() {
		return "register";
	}

}
