package org.to2mbn.akir.core.service.user;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.FORBIDDEN, reason = "error.user.invalid_credentials")
public class InvalidCredentialsException extends Exception {

	private static final long serialVersionUID = 1L;

	public InvalidCredentialsException() {}

	public InvalidCredentialsException(Throwable cause) {
		super(null, cause);
	}

}
