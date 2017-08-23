package org.to2mbn.akir.core.service.texture;

import static java.util.Collections.unmodifiableMap;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Paths;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import javax.annotation.PostConstruct;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;
import org.to2mbn.akir.core.model.TextureModel;
import org.to2mbn.akir.core.service.AkirConfiguration;

@Component
public class TexturesManager {

	@Autowired
	private AkirConfiguration config;

	private Map<String, Resource> predefinedTextures = new HashMap<>();
	private Map<TextureModel, String> defaultSkins = new EnumMap<>(TextureModel.class);

	@PostConstruct
	private void detectPredefinedTextures() throws IOException {
		for (TextureModel model : TextureModel.values()) {
			Resource resource = new ClassPathResource("/textures/default/skin/" + model.name().toLowerCase() + ".png");
			if (resource.exists()) {
				try (InputStream in = resource.getInputStream()) {
					String textureId = DigestUtils.sha256Hex(in);
					predefinedTextures.put(textureId, resource);
					defaultSkins.put(model, textureId);
				}
			} else {
				throw new IllegalStateException("Default texture for " + model + " not found");
			}
		}
		predefinedTextures = unmodifiableMap(predefinedTextures);
		defaultSkins = unmodifiableMap(defaultSkins);
	}

	public String defaultSkinIdFor(TextureModel model) {
		return defaultSkins.get(model);
	}

	public Resource textureResource(String textureId) {
		return Optional.ofNullable(predefinedTextures.get(textureId))
				.orElseGet(() -> new FileSystemResource(Paths.get(config.getTexturesStorage(), textureId + ".png").toString()));
	}

}
