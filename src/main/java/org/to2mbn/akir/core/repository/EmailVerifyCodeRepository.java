package org.to2mbn.akir.core.repository;

import java.util.Optional;
import org.springframework.data.repository.CrudRepository;
import org.to2mbn.akir.core.model.EmailVerifyCode;

// TODO: migrate this to NoSQL(such as Redis?)
public interface EmailVerifyCodeRepository extends CrudRepository<EmailVerifyCode, String> {

	void deleteByEmail(String email);

	Optional<EmailVerifyCode> findByCode(String code);

}
