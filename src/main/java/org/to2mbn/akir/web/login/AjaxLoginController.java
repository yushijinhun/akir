package org.to2mbn.akir.web.login;

import java.security.Principal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/login")
@RestController
public class AjaxLoginController {

	@Autowired
	private AuthenticationManager authProvider;

	@PostMapping
	@ResponseStatus(code = HttpStatus.NO_CONTENT)
	public void login(@RequestBody LoginRequest req, Principal principal) {
		if (principal == null) {
			Authentication auth = authProvider.authenticate(new UsernamePasswordAuthenticationToken(req.getEmail(), req.getPassword()));
			SecurityContextHolder.getContext().setAuthentication(auth);
		}
	}

}
