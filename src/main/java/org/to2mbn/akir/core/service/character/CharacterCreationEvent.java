package org.to2mbn.akir.core.service.character;

import java.util.UUID;
import org.springframework.context.ApplicationEvent;

public class CharacterCreationEvent extends ApplicationEvent {

	private static final long serialVersionUID = 1L;

	private UUID characterUuid;

	public CharacterCreationEvent(Object source, UUID characterUuid) {
		super(source);
		this.characterUuid = characterUuid;
	}

	public UUID getCharacterUuid() {
		return characterUuid;
	}

}
