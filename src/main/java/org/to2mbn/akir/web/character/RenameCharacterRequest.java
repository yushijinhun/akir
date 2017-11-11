package org.to2mbn.akir.web.character;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import org.to2mbn.akir.core.service.character.CharacterService;

public class RenameCharacterRequest {

	@NotNull
	@Pattern(regexp = CharacterService.REGEX_NAME)
	@Size(max = CharacterService.MAX_LENGTH_NAME)
	private String newCharacterName;

	public String getNewCharacterName() {
		return newCharacterName;
	}

	public void setNewCharacterName(String newCharacterName) {
		this.newCharacterName = newCharacterName;
	}
}
