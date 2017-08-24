package org.to2mbn.akir.core.service.character;

import static org.springframework.http.HttpStatus.FORBIDDEN;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Thrown when a non-admin user is trying to operate on a character which doesn't belong to the user.
 */
@ResponseStatus(code = FORBIDDEN, reason = "character.error.unauthorized")
public class UnauthorizedCharacterOperationException extends Exception {

	private static final long serialVersionUID = 1L;

	public UnauthorizedCharacterOperationException() {}

	public UnauthorizedCharacterOperationException(String message) {
		super(message);
	}
}
