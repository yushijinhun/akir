package org.to2mbn.akir.web.dashboard;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class DashboardController {

	@GetMapping("/")
	public String root() {
		return "redirect:/dashboard";
	}

	@GetMapping("/dashboard")
	public String dashboard() {
		return "dashboard";
	}
}
