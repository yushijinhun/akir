package org.to2mbn.akir.web.register;

import java.security.Principal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/register")
@Controller
public class RegisterController {

	@GetMapping
	public String register(Principal principal) {
		if (principal != null)
			return "redirect:/";

		return "register";
	}
}
