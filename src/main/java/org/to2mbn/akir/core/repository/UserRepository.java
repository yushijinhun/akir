package org.to2mbn.akir.core.repository;

import java.util.Optional;
import java.util.UUID;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.to2mbn.akir.core.model.User;

public interface UserRepository extends PagingAndSortingRepository<User, UUID> {

	Optional<User> findByName(String name);

	boolean existsByName(String name);

	Optional<User> findByEmail(String email);

	boolean existsByEmail(String email);

}
