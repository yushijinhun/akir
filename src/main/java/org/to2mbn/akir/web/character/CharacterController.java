package org.to2mbn.akir.web.character;

import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.to2mbn.akir.core.model.GameCharacter;
import org.to2mbn.akir.core.model.User;
import org.to2mbn.akir.core.repository.CharacterRepository;
import org.to2mbn.akir.core.repository.UserRepository;
import org.to2mbn.akir.core.service.character.CharacterNotFoundException;
import org.to2mbn.akir.core.service.character.CharacterService;
import org.to2mbn.akir.core.service.user.UserNotFoundException;

@RequestMapping("/character")
@Controller
public class CharacterController {

	@Autowired
	private UserRepository userRepo;

	@Autowired
	private CharacterRepository characterRepo;

	@GetMapping("new")
	public String createCharacter(@RequestParam(required = false) String ownerName, User loginUser, ModelMap model) throws UserNotFoundException {
		model.put("name_maxlength", CharacterService.MAX_LENGTH_NAME);
		model.put("name_regex", CharacterService.REGEX_NAME);
		model.put("owner_user", ownerName == null ? loginUser : userRepo.findByName(ownerName).orElseThrow(() -> new UserNotFoundException(ownerName)));
		return "create-character";
	}

	private GameCharacter resolveCharacter(String uuid) throws CharacterNotFoundException {
		UUID characterUuid;
		try {
			characterUuid = UUID.fromString(uuid);
		} catch (IllegalArgumentException e) {
			throw new CharacterNotFoundException(uuid);
		}
		return characterRepo.findById(characterUuid).orElseThrow(() -> new CharacterNotFoundException(uuid));
	}

	private User resolveOwner(GameCharacter character) throws UserNotFoundException {
		return userRepo.findById(character.getOwnerId()).orElseThrow(() -> new UserNotFoundException(character.getOwnerId().toString()));
	}

	@GetMapping("{uuid}")
	public String viewCharacter(@PathVariable String uuid, ModelMap model) throws CharacterNotFoundException, UserNotFoundException {
		GameCharacter character = resolveCharacter(uuid);
		User owner = resolveOwner(character);
		model.put("showing_character", character);
		model.put("showing_owner", owner);
		return "character-profile";
	}

}
