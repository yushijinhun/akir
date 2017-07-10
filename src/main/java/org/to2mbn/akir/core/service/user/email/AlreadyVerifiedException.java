package org.to2mbn.akir.core.service.user.email;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.FORBIDDEN, reason = "email_verify.error.already_verified")
public class AlreadyVerifiedException extends EmailVerifyException {

	private static final long serialVersionUID = 1L;

	public AlreadyVerifiedException() {}

	public AlreadyVerifiedException(String message) {
		super(message);
	}

}
