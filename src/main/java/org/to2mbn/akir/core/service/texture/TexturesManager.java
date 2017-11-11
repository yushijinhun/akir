package org.to2mbn.akir.core.service.texture;

import static java.util.Collections.unmodifiableMap;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Paths;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import javax.annotation.PostConstruct;
import javax.imageio.ImageIO;
import org.apache.commons.codec.binary.Hex;
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
					String textureId = computeTextureId(in);
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

	public static String computeTextureId(InputStream in) throws IOException {
		BufferedImage img = ImageIO.read(in);
		MessageDigest digest;
		try {
			digest = MessageDigest.getInstance("SHA-256");
		} catch (NoSuchAlgorithmException e) {
			throw new IllegalStateException(e);
		}

		int w = img.getWidth();
		int h = img.getHeight();
		int buf_size = 4096;
		byte[] buf = new byte[buf_size];
		put_int(buf, 0, w);
		put_int(buf, 4, h);
		int i = 8;
		for (int x = 0; x < w; x++) {
			for (int y = 0; y < h; y++) {
				put_int(buf, i, img.getRGB(x, y));
				i += 4;
				if (i == buf_size) {
					i = 0;
					digest.update(buf, 0, buf_size);
				}
			}
		}
		if (i > 0) {
			digest.update(buf, 0, i);
		}

		return Hex.encodeHexString(digest.digest());
	}

	private static void put_int(byte[] bu, int i, int x) {
		bu[i + 0] = (byte) (x >> 0 & 0xff);
		bu[i + 1] = (byte) (x >> 8 & 0xff);
		bu[i + 2] = (byte) (x >> 16 & 0xff);
		bu[i + 3] = (byte) (x >> 24 & 0xff);
	}
}
