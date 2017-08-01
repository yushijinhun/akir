package org.to2mbn.akir.core.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.to2mbn.akir.core.model.GameCharacter;

public interface CharacterRepository extends PagingAndSortingRepository<GameCharacter, UUID> {

	Optional<GameCharacter> findByName(String name);

	List<GameCharacter> findByOwnerEmail(String ownerEmail);

}
