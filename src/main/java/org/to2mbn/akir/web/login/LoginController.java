package org.to2mbn.akir.web.login;

import java.security.Principal;
import javax.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LoginController {

	@GetMapping("/login")
	public String loginPage(ModelMap model, HttpServletRequest request, Principal principal) {
		if (principal != null)
			return "redirect:/";

		ReturnableLoginUrlAuthenticationEntryPoint.consumeReturnUrl(request)
				.ifPresent(returnUrl -> model.addAttribute("login_return_url", returnUrl));

		return "login";
	}

}
