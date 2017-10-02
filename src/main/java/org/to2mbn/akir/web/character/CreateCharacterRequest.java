package org.to2mbn.akir.web.character;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import org.to2mbn.akir.core.model.TextureModel;
import org.to2mbn.akir.core.service.character.CharacterService;

public class CreateCharacterRequest {

	@NotEmpty
	private String ownerName;

	@NotNull
	@Pattern(regexp = CharacterService.REGEX_NAME)
	@Size(max = CharacterService.MAX_LENGTH_NAME)
	private String characterName;

	@NotNull
	private TextureModel textureModel;

	public String getOwnerName() {
		return ownerName;
	}

	public void setOwnerName(String ownerName) {
		this.ownerName = ownerName;
	}

	public String getCharacterName() {
		return characterName;
	}

	public void setCharacterName(String characterName) {
		this.characterName = characterName;
	}

	public TextureModel getTextureModel() {
		return textureModel;
	}

	public void setTextureModel(TextureModel textureModel) {
		this.textureModel = textureModel;
	}
}
