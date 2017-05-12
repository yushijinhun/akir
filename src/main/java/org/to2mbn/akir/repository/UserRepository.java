package org.to2mbn.akir.repository;

import java.util.Optional;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.to2mbn.akir.model.User;

public interface UserRepository extends PagingAndSortingRepository<User, String> {

	Optional<User> findByName(String name);

}
