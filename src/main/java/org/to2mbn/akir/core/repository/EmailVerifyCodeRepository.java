package org.to2mbn.akir.core.repository;

import org.springframework.data.repository.CrudRepository;
import org.to2mbn.akir.core.model.EmailVerifyCode;

public interface EmailVerifyCodeRepository extends CrudRepository<EmailVerifyCode, String> {

}
