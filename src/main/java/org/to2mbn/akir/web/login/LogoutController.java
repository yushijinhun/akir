package org.to2mbn.akir.web.login;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/logout")
@Controller
public class LogoutController {

	@GetMapping
	public String logoutPage() {
		return "logout";
	}

}
