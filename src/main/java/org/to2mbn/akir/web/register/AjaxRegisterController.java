package org.to2mbn.akir.web.register;

import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.to2mbn.akir.core.repository.UserRepository;
import org.to2mbn.akir.core.service.user.UserConflictException;
import org.to2mbn.akir.core.service.user.UserService;

@RequestMapping("/register")
@RestController
public class AjaxRegisterController {

	@Autowired
	private UserRepository userRepo;

	@Autowired
	private UserService userService;

	@GetMapping("validate/email")
	@ResponseStatus(code = HttpStatus.NO_CONTENT)
	public void validateEmail(@RequestParam String email) throws UserConflictException {
		if (userRepo.existsById(email.toLowerCase()))
			throw new UserConflictException("Email is already in use");
	}

	@GetMapping("validate/name")
	@ResponseStatus(code = HttpStatus.NO_CONTENT)
	public void validateName(@RequestParam String name) throws UserConflictException {
		if (userRepo.existsByName(name.toLowerCase()))
			throw new UserConflictException("Name is already in use");
	}

	@PostMapping
	@ResponseStatus(code = HttpStatus.NO_CONTENT)
	public void register(@Valid @RequestBody RegisterRequest request) throws UserConflictException {
		userService.register(request.getEmail(), request.getName(), request.getPassword());
	}

}
