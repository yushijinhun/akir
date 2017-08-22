package org.to2mbn.akir.web.login;

import java.security.Principal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.to2mbn.akir.core.service.user.InvalidCredentialsException;
import org.to2mbn.akir.core.service.user.UserService;

@RequestMapping("/login")
@RestController
public class AjaxLoginController {

	@Autowired
	private UserService userService;

	@PostMapping
	@ResponseStatus(code = HttpStatus.NO_CONTENT)
	public void login(@RequestBody LoginRequest req, Principal principal) throws InvalidCredentialsException {
		if (principal == null)
			SecurityContextHolder.getContext().setAuthentication(
					userService.authenticate(req.getEmail(), req.getPassword()));
	}

}
