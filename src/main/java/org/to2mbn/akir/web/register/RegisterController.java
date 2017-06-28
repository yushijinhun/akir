package org.to2mbn.akir.web.register;

import java.security.Principal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.to2mbn.akir.core.service.user.UserService;

@RequestMapping("/register")
@Controller
public class RegisterController {

	@GetMapping
	public String register(Principal principal, ModelMap model) {
		if (principal != null)
			return "redirect:/";

		model.put("email_maxlength", UserService.MAX_LENGTH_EMAIL);
		model.put("email_regex", UserService.REGEX_EMAIL);
		model.put("name_maxlength", UserService.MAX_LENGTH_NAME);
		model.put("name_regex", UserService.REGEX_NAME);
		model.put("password_maxlength", UserService.MAX_LENGTH_PASSWORD);
		model.put("password_minlength", UserService.MIN_LENGTH_PASSWORD);

		return "register";
	}
}
