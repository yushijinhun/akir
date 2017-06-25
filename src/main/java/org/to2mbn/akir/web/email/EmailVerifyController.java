package org.to2mbn.akir.web.email;

import static org.to2mbn.akir.web.login.LoginController.LOGIN_TOOLTIP;
import javax.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.to2mbn.akir.core.model.User;
import org.to2mbn.akir.core.service.user.email.AlreadyVerifiedException;
import org.to2mbn.akir.core.service.user.email.EmailVerifyException;
import org.to2mbn.akir.core.service.user.email.EmailVerifyService;
import org.to2mbn.akir.core.service.user.email.VerifyCodeExpiredException;
import org.to2mbn.akir.core.service.user.email.WrongVerifyCodeException;
import org.to2mbn.akir.web.util.OneTimeCookie;

@Controller
@RequestMapping("/email_verify")
public class EmailVerifyController {

	private static final Logger LOGGER = LoggerFactory.getLogger(EmailVerifyController.class);

	@Autowired
	private EmailVerifyService emailVerifyService;

	@GetMapping("do_verify")
	public String verifyEmail(@RequestParam String email, @RequestParam String code, HttpServletResponse response) {
		String message;
		try {
			emailVerifyService.verifyEmail(email, code);
			message = "email_verified";
		} catch (AlreadyVerifiedException e) {
			message = "email_already_verified";
		} catch (VerifyCodeExpiredException e) {
			message = "email_verify_code_expired";
		} catch (WrongVerifyCodeException e) {
			message = "email_wrong_verify_code";
		} catch (EmailVerifyException e) {
			// here shouldn't be reached
			LOGGER.warn("Unexpected exception", e);
			// fallback
			message = "email_wrong_verify_code";
		}
		OneTimeCookie.put(response, LOGIN_TOOLTIP, message);
		return "redirect:/login";
	}

	@GetMapping
	public String resendVerifyEmail(User user) {
		if (user.isEmailVerified())
			return "redirect:/";

		// TODO resend email interface
		return "resend_email";
	}

}
