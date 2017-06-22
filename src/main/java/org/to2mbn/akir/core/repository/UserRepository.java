package org.to2mbn.akir.core.repository;

import java.util.Optional;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.to2mbn.akir.core.model.User;

public interface UserRepository extends PagingAndSortingRepository<User, String> {

	Optional<User> findByName(String name);

	boolean existsByName(String name);

}
