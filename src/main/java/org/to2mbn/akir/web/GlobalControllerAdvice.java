package org.to2mbn.akir.web;

import static java.util.Collections.unmodifiableList;
import static java.util.stream.Collectors.toList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.to2mbn.akir.core.model.TextureModel;
import org.to2mbn.akir.core.model.User;
import org.to2mbn.akir.core.service.AkirConfiguration;
import org.to2mbn.akir.core.service.character.CharacterService;
import org.to2mbn.akir.core.service.user.UserService;
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

	@ModelAttribute("character_restriction")
	public Map<String, Object> characterRestriction() {
		Map<String, Object> restrictions = new HashMap<>();
		restrictions.put("name_regex", CharacterService.REGEX_NAME);
		restrictions.put("name_maxlength", CharacterService.MAX_LENGTH_NAME);
		return restrictions;
	}

	@ModelAttribute("user_restriction")
	public Map<String, Object> userRestriction() {
		Map<String, Object> restrictions = new HashMap<>();
		restrictions.put("email_maxlength", UserService.MAX_LENGTH_EMAIL);
		restrictions.put("email_regex", UserService.REGEX_EMAIL);
		restrictions.put("name_maxlength", UserService.MAX_LENGTH_NAME);
		restrictions.put("name_regex", UserService.REGEX_NAME);
		restrictions.put("password_maxlength", UserService.MAX_LENGTH_PASSWORD);
		restrictions.put("password_minlength", UserService.MIN_LENGTH_PASSWORD);
		return restrictions;
	}
}
