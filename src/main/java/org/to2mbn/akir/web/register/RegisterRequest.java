package org.to2mbn.akir.web.register;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import org.to2mbn.akir.core.service.user.UserService;

public class RegisterRequest {

	@NotNull
	@Pattern(regexp = UserService.REGEX_EMAIL)
	@Size(max = UserService.MAX_LENGTH_EMAIL)
	private String email;

	@NotNull
	@Pattern(regexp = UserService.REGEX_NAME)
	@Size(max = UserService.MAX_LENGTH_NAME)
	private String name;

	@NotNull
	@Size(min = UserService.MIN_LENGTH_PASSWORD, max = UserService.MAX_LENGTH_PASSWORD)
	private String password;

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

}
