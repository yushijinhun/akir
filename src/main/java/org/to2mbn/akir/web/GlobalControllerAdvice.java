package org.to2mbn.akir.web;

import static java.util.Collections.unmodifiableList;
import static java.util.stream.Collectors.toList;
import java.util.List;
import java.util.stream.Stream;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.to2mbn.akir.core.model.TextureModel;
import org.to2mbn.akir.core.model.User;
import org.to2mbn.akir.core.service.AkirConfiguration;
import org.to2mbn.akir.web.util.AkirModelExtension;

@ControllerAdvice
public class GlobalControllerAdvice {

	@Autowired
	private AkirConfiguration serverInfo;

	@Autowired
	private AkirModelExtension ext;

	private List<String> availableTextureModels =
			unmodifiableList(Stream.of(TextureModel.values())
					.map(Enum::name)
					.map(String::toLowerCase)
					.collect(toList()));

	@ModelAttribute("akir_server")
	public AkirConfiguration akirServer() {
		return serverInfo;
	}

	@ModelAttribute("login_user")
	public User loginUser(User user) {
		return user;
	}

	@ModelAttribute("ext")
	public AkirModelExtension ext() {
		return ext;
	}

	@ModelAttribute("available_texture_models")
	public List<String> availableTextureModels() {
		return availableTextureModels;
	}

}
