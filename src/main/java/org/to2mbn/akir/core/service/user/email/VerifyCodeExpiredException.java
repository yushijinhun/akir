package org.to2mbn.akir.core.service.user.email;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.FORBIDDEN, reason = "email_verify_code_expired")
public class VerifyCodeExpiredException extends EmailVerifyException {

	private static final long serialVersionUID = 1L;

	public VerifyCodeExpiredException() {}

	public VerifyCodeExpiredException(String message) {
		super(message);
	}

}
