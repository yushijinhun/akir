package org.to2mbn.akir.yggdrasil;

import java.util.concurrent.TimeUnit;
import java.util.regex.Pattern;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.web.ResourceProperties;
import org.springframework.core.io.Resource;
import org.springframework.http.CacheControl;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.to2mbn.akir.core.service.texture.TexturesManager;

@RequestMapping("/yggdrasil/textures/texture/{textureId}")
@Controller
public class TexturesController {

	private static final Pattern REGEX_TEXTURE_ID = Pattern.compile("[a-z0-9]+");

	@Autowired
	private TexturesManager texturesManager;

	@Autowired
	private ResourceProperties resourceProperties;

	@GetMapping
	public ResponseEntity<Resource> texture(@PathVariable String textureId) {
		if (!REGEX_TEXTURE_ID.matcher(textureId).matches()) {
			return ResponseEntity.notFound().build();
		}
		Resource resource = texturesManager.textureResource(textureId);
		if (!resource.exists()) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok()
				.contentType(MediaType.IMAGE_PNG)
				.cacheControl(CacheControl.maxAge(resourceProperties.getCachePeriod(), TimeUnit.SECONDS))
				.eTag(textureId)
				.body(resource);
	}
}
