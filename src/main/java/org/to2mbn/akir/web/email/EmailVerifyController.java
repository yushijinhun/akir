package org.to2mbn.akir.web.email;

import static org.to2mbn.akir.web.login.LoginController.LOGIN_TOOLTIP;
import static org.to2mbn.akir.web.util.WebUtils.getResponseStatus;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.to2mbn.akir.core.model.User;
import org.to2mbn.akir.core.service.user.email.EmailVerifyException;
import org.to2mbn.akir.core.service.user.email.EmailVerifyService;
import org.to2mbn.akir.web.util.OneTimeCookie;

@Controller
@RequestMapping("/email_verify")
public class EmailVerifyController {

	@Autowired
	private EmailVerifyService emailVerifyService;

	@GetMapping("do_verify")
	public String verifyEmail(@RequestParam String email, @RequestParam String code, HttpServletResponse response) {
		String message;
		try {
			emailVerifyService.verifyEmail(email, code);
			message = "email_verify.success";
		} catch (EmailVerifyException e) {
			message = getResponseStatus(e).map(ResponseStatus::reason).orElse("email_verify.error.unknown");
		}
		OneTimeCookie.put(response, LOGIN_TOOLTIP, message);
		return "redirect:/login";
	}

	@GetMapping
	public String resendVerifyEmail(User user) {
		if (user.isEmailVerified())
			return "redirect:/";

		// TODO resend email view
		return "resend_email";
	}

}
