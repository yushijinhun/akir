package org.to2mbn.akir.web.util;

import java.util.Optional;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;
import org.to2mbn.akir.core.model.GameCharacter;
import org.to2mbn.akir.core.model.TextureModel;
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
				.orElseGet(() -> texturesManager.defaultSkinIdFor(character.getModel()));
	}

	@Nullable
	public String capeTextureId(GameCharacter character) {
		return character.getTextures().get(TextureType.CAPE);
	}

	@Nullable
	public String elytraTextureId(GameCharacter character) {
		return character.getTextures().get(TextureType.ELYTRA);
	}

	public String defaultSkinTextureId(String type) {
		return texturesManager.defaultSkinIdFor(TextureModel.valueOf(type.toUpperCase()));
	}

}
