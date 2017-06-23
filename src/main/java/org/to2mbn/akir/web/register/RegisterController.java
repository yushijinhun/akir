package org.to2mbn.akir.web.register;

import java.security.Principal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.to2mbn.akir.core.service.email.EmailVerifyService;

@RequestMapping("/register")
@Controller
public class RegisterController {

	@Autowired
	private EmailVerifyService emailVerifyService;

	@GetMapping
	public String register(Principal principal) {
		if (principal != null)
			return "redirect:/";

		return "register";
	}

	@GetMapping("verify_email")
	public String verifyEmail(@RequestParam String email, @RequestParam String code) {
		emailVerifyService.verifyEmail(email, code);
		// TODO exception handling
		return null;
	}

}
