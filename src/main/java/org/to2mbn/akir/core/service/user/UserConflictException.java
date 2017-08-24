package org.to2mbn.akir.core.service.user;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Thrown when the identity of a user(email, name...) conflicts with another
 * user.
 */
@ResponseStatus(code = HttpStatus.CONFLICT, reason = "error.user.conflict")
public class UserConflictException extends Exception {

	private static final long serialVersionUID = 1L;

	public UserConflictException() {}

	public UserConflictException(String message) {
		super(message);
	}

}
