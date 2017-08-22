package org.to2mbn.akir.web.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.to2mbn.akir.core.model.User;
import org.to2mbn.akir.core.repository.CharacterRepository;
import org.to2mbn.akir.core.repository.UserRepository;
import org.to2mbn.akir.core.service.user.UserNotFoundException;

@Controller
@RequestMapping("/user/{username}")
public class UserController {

	@Autowired
	private UserRepository userRepo;

	@Autowired
	private CharacterRepository profileRepo;

	@GetMapping
	public String viewProfile(@PathVariable String username, ModelMap model) throws UserNotFoundException {
		User user = userRepo.findByName(username).orElseThrow(() -> new UserNotFoundException(username));
		model.put("showing_user", user);
		model.put("user_characters", profileRepo.findByOwnerId(user.getId()));
		return "user-profile";
	}
}
