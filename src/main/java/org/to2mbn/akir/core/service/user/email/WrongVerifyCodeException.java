package org.to2mbn.akir.core.service.user.email;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.FORBIDDEN, reason = "email_verify.error.wrong_code")
public class WrongVerifyCodeException extends EmailVerifyException {

	private static final long serialVersionUID = 1L;

	public WrongVerifyCodeException() {}

	public WrongVerifyCodeException(String message) {
		super(message);
	}

}
