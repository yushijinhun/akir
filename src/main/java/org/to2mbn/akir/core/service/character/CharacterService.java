package org.to2mbn.akir.core.service.character;

import java.util.LinkedHashMap;
import java.util.UUID;
import java.util.regex.Pattern;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;
import org.to2mbn.akir.core.model.GameCharacter;
import org.to2mbn.akir.core.model.TextureModel;
import org.to2mbn.akir.core.model.User;
import org.to2mbn.akir.core.repository.CharacterRepository;
import org.to2mbn.akir.core.repository.UserRepository;
import org.to2mbn.akir.core.service.user.UserNotFoundException;
import org.to2mbn.akir.core.service.user.UserService;

@Component
public class CharacterService {

	public static final String REGEX_NAME = "[a-zA-Z0-9]([-_ ]?[a-zA-Z0-9]+)*";
	public static final int MAX_LENGTH_NAME = 254;

	private static final Pattern PATTERN_NAME = Pattern.compile(REGEX_NAME);

	private static final Logger LOGGER = LoggerFactory.getLogger(CharacterService.class);

	@Autowired
	private ApplicationEventPublisher eventPublisher;

	@Autowired
	private UserRepository userRepo;

	@Autowired
	private CharacterRepository characterRepo;

	public static void checkPermission(User characterOwner) throws UnauthorizedCharacterOperationException {
		checkPermission(characterOwner.getId());
	}

	public static void checkPermission(GameCharacter character) throws UnauthorizedCharacterOperationException {
		checkPermission(character.getOwnerId());
	}

	public static void checkPermission(UUID characterOwnerId) throws UnauthorizedCharacterOperationException {
		checkPermission(characterOwnerId, UserService.getCurrentUser().orElseThrow(() -> new UnauthorizedCharacterOperationException("You are not logged in")));
	}

	public static void checkPermission(UUID characterOwnerId, User callerUser) throws UnauthorizedCharacterOperationException {
		if (callerUser.isAdmin() || callerUser.getId().equals(characterOwnerId))
			return;
		throw new UnauthorizedCharacterOperationException("You are not admin, or the character doesn't belong to you");
	}

	private void validateCharacterName(String characterName) {
		if (characterName.length() > MAX_LENGTH_NAME)
			throw new IllegalArgumentException("Name is too long");
		if (!PATTERN_NAME.matcher(characterName).matches())
			throw new IllegalArgumentException("Invalid name");
	}

	public GameCharacter createCharacter(UUID owner, String characterName) throws UserNotFoundException, CharacterConflictException {
		validateCharacterName(characterName);
		// we only use lower-case character name
		characterName = characterName.toLowerCase();

		if (!userRepo.existsById(owner))
			throw new UserNotFoundException(owner.toString());

		if (characterRepo.existsByName(characterName))
			throw new CharacterConflictException(characterName);

		GameCharacter character = new GameCharacter();
		character.setUuid(UUID.randomUUID());
		character.setName(characterName);
		character.setOwnerId(owner);
		character.setModel(TextureModel.STEVE);
		character.setTextures(new LinkedHashMap<>());
		character.setCreateTime(System.currentTimeMillis());
		character = characterRepo.save(character);

		LOGGER.info("Character {} created with name {} and owner {}", character.getUuid(), character.getName(), character.getOwnerId());

		eventPublisher.publishEvent(new CharacterCreationEvent(this, character.getUuid()));

		return characterRepo.findById(character.getUuid()).get();
	}

	public GameCharacter renameCharacter(GameCharacter character, String newName) throws CharacterConflictException {
		validateCharacterName(newName);
		newName = newName.toLowerCase();

		if (character.getName().equals(newName))
			return character;

		if (characterRepo.existsByName(newName))
			throw new CharacterConflictException(newName);

		character.setName(newName);
		character = characterRepo.save(character);

		return character;
	}

	public GameCharacter characterOf(String uuid) throws CharacterNotFoundException {
		UUID characterUuid;
		try {
			characterUuid = UUID.fromString(uuid);
		} catch (IllegalArgumentException e) {
			throw new CharacterNotFoundException(uuid);
		}
		return characterOf(characterUuid);
	}

	public GameCharacter characterOf(UUID uuid) throws CharacterNotFoundException {
		return characterRepo.findById(uuid).orElseThrow(() -> new CharacterNotFoundException(uuid));
	}

	public User ownerOf(GameCharacter character) throws UserNotFoundException {
		return userRepo.findById(character.getOwnerId()).orElseThrow(() -> new UserNotFoundException(character.getOwnerId().toString()));
	}
}
