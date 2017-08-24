package org.to2mbn.akir.web.character;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.to2mbn.akir.core.model.GameCharacter;
import org.to2mbn.akir.core.model.User;
import org.to2mbn.akir.core.repository.CharacterRepository;
import org.to2mbn.akir.core.repository.UserRepository;
import org.to2mbn.akir.core.service.character.CharacterConflictException;
import org.to2mbn.akir.core.service.character.CharacterService;
import org.to2mbn.akir.core.service.character.UnauthorizedCharacterOperationException;
import org.to2mbn.akir.core.service.user.UserNotFoundException;

@RequestMapping("/character")
@RestController
public class AjaxCharacterController {

	@Autowired
	private CharacterRepository characterRepo;

	@Autowired
	private UserRepository userRepo;

	@Autowired
	private CharacterService characterService;

	@GetMapping("new/validate/name")
	@ResponseStatus(code = HttpStatus.NO_CONTENT)
	public void validateCharacterName(@RequestParam String characterName) throws CharacterConflictException {
		if (characterRepo.existsByName(characterName))
			throw new CharacterConflictException("Character name is already in use");
	}

	@PostMapping("new")
	public CreateCharacterResponse createCharacter(@RequestBody CreateCharacterRequest request) throws UserNotFoundException, UnauthorizedCharacterOperationException, CharacterConflictException {
		User owner = userRepo.findByName(request.getOwnerName())
				.orElseThrow(() -> new UserNotFoundException(request.getOwnerName()));
		CharacterService.checkPermission(owner);

		GameCharacter character = characterService.createCharacter(owner.getId(), request.getCharacterName());
		character.setModel(request.getTextureModel());
		character = characterRepo.save(character);

		CreateCharacterResponse response = new CreateCharacterResponse();
		response.setUuid(character.getUuid());
		return response;
	}

}
