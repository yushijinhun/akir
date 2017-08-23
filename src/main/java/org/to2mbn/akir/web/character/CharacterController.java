package org.to2mbn.akir.web.character;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.to2mbn.akir.core.service.character.CharacterService;

@RequestMapping("/character")
@Controller
public class CharacterController {

	@GetMapping("new")
	public String createCharacter(ModelMap model) {
		model.put("name_maxlength", CharacterService.MAX_LENGTH_NAME);
		model.put("name_regex", CharacterService.REGEX_NAME);
		return "create-character";
	}

	@GetMapping("{uuid}")
	public String viewCharacter(String uuidString) {
		return null;// TODO
	}

}
