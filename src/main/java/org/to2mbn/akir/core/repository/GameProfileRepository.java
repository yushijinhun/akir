package org.to2mbn.akir.core.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.to2mbn.akir.core.model.GameProfile;

public interface GameProfileRepository extends PagingAndSortingRepository<GameProfile, UUID> {

	Optional<GameProfile> findByName(String name);

	List<GameProfile> findByOwnerEmail(String ownerEmail);

}
