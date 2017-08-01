package org.to2mbn.akir.web.util;

import java.util.Optional;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.to2mbn.akir.core.model.GameCharacter;
import org.to2mbn.akir.core.model.TextureType;
import org.to2mbn.akir.core.service.texture.TexturesManager;

@Component
public class AkirModelExtension {

	@Autowired
	private TexturesManager texturesManager;

	public String md5(String input) {
		return DigestUtils.md5Hex(input);
	}

	public String skinTextureId(GameCharacter character) {
		return Optional.ofNullable(character.getTextures().get(TextureType.SKIN))
				.orElseGet(() -> texturesManager.defaultTextureIdFor(character.getModel()));
	}

}
