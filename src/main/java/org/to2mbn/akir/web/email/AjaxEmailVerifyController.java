package org.to2mbn.akir.web.email;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.to2mbn.akir.core.model.User;
import org.to2mbn.akir.core.service.user.email.AlreadyVerifiedException;
import org.to2mbn.akir.core.service.user.email.EmailVerifyService;

@RestController
@RequestMapping("/email_verify")
public class AjaxEmailVerifyController {

	@Autowired
	private EmailVerifyService verifyService;

	@PostMapping("resend")
	@ResponseStatus(code = HttpStatus.NO_CONTENT)
	public void resendVerifyEmail(User user) throws AlreadyVerifiedException {
		if (user.isEmailVerified())
			throw new AlreadyVerifiedException();
		verifyService.sendVerifyEmail(user);
	}

}
