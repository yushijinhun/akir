package org.to2mbn.akir.core.service.character;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Thrown when the identity of a character(uuid, name...) conflicts with another
 * character.
 */
@ResponseStatus(code = HttpStatus.CONFLICT, reason = "create_character.error.conflict")
public class CharacterConflictException extends Exception {

	private static final long serialVersionUID = 1L;

	public CharacterConflictException() {}

	public CharacterConflictException(String message) {
		super(message);
	}

}
