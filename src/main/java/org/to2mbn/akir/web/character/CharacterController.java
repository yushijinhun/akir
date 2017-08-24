package org.to2mbn.akir.web.character;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.to2mbn.akir.core.model.User;
import org.to2mbn.akir.core.repository.UserRepository;
import org.to2mbn.akir.core.service.character.CharacterService;
import org.to2mbn.akir.core.service.user.UserNotFoundException;

@RequestMapping("/character")
@Controller
public class CharacterController {

	@Autowired
	private UserRepository userRepo;

	@GetMapping("new")
	public String createCharacter(@RequestParam(required = false) String ownerName, User loginUser, ModelMap model) throws UserNotFoundException {
		model.put("name_maxlength", CharacterService.MAX_LENGTH_NAME);
		model.put("name_regex", CharacterService.REGEX_NAME);
		model.put("owner_user", ownerName == null ? loginUser : userRepo.findByName(ownerName).orElseThrow(() -> new UserNotFoundException(ownerName)));
		return "create-character";
	}

	@GetMapping("{uuid}")
	public String viewCharacter(String uuidString) {
		return null;// TODO
	}

}
