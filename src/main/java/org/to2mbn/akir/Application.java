package org.to2mbn.akir;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController // TODO: to be removed
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

	// TODO: to be removed
	@RequestMapping("/")
	public String index() {
		return "hi";
	}

}
