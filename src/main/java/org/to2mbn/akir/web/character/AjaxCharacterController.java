package org.to2mbn.akir.web.character;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.to2mbn.akir.core.repository.CharacterRepository;
import org.to2mbn.akir.core.service.character.CharacterConflictException;

@RequestMapping("/character")
@RestController
public class AjaxCharacterController {

	@Autowired
	private CharacterRepository characterRepo;

	@GetMapping("new/validate/name")
	@ResponseStatus(code = HttpStatus.NO_CONTENT)
	public void validateCharacterName(@RequestParam String characterName) throws CharacterConflictException {
		if (characterRepo.existsByName(characterName))
			throw new CharacterConflictException("Character name is already in use");
	}

}
