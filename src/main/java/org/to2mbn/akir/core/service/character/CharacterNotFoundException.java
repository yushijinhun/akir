package org.to2mbn.akir.core.service.character;

import java.util.UUID;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND, reason = "error.character.not_found")
public class CharacterNotFoundException extends Exception {

	private static final long serialVersionUID = 1L;

	public CharacterNotFoundException() {}

	public CharacterNotFoundException(String message) {
		super(message);
	}

	public CharacterNotFoundException(UUID uuid) {
		this(uuid.toString());
	}

}
