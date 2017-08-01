package org.to2mbn.akir.web.login;

import static org.to2mbn.akir.web.login.ReturnableLoginUrlAuthenticationEntryPoint.LOGIN_RETURN_URL;
import java.security.Principal;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.to2mbn.akir.web.util.OneTimeCookie;

@RequestMapping("/login")
@Controller
public class LoginController {

	public static final String LOGIN_TOOLTIP = "login_tooltip";

	@GetMapping
	public String loginPage(ModelMap model, Principal principal, HttpServletRequest request, HttpServletResponse response) {
		OneTimeCookie.consume(request, response, LOGIN_RETURN_URL)
				.ifPresent(returnUrl -> model.addAttribute("login_return_url", returnUrl));

		OneTimeCookie.consume(request, response, LOGIN_TOOLTIP)
				.ifPresent(tooltip -> model.addAttribute("login_tooltip", tooltip));

		if (principal != null)
			return "redirect:/";

		return "login";
	}

}
