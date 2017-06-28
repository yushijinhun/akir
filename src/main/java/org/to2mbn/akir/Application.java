package org.to2mbn.akir;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Locale;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController // TODO: to be removed
public class Application {

	private static final String CONFIG_PATH = "application.yaml";
	private static final String DEFAULT_CONFIG_PATH = "/default-application.yaml";

	public static void main(String[] args) {
		Locale.setDefault(Locale.ENGLISH);
		if (doesConfigExist()) {
			SpringApplication.run(Application.class, args);
		} else {
			if (copyConfig()) {
				System.err.println("No configuration is detected.");
				System.err.println("A new configuration has been written to '" + CONFIG_PATH + "'.");
				System.err.println("Please edit it and restart akir.");
			}
			System.exit(1);
		}
	}

	private static boolean doesConfigExist() {
		return Files.exists(Paths.get(CONFIG_PATH));
	}

	private static boolean copyConfig() {
		try (InputStream in = Application.class.getResourceAsStream(DEFAULT_CONFIG_PATH)) {
			if (in == null) {
				System.err.println(DEFAULT_CONFIG_PATH + " not exists");
				return false;
			}
			Files.copy(in, Paths.get(CONFIG_PATH));
			return true;
		} catch (IOException e) {
			System.err.println("Couldn't create default configuration");
			e.printStackTrace();
			return false;
		}
	}

	// TODO: to be removed
	@RequestMapping("/")
	public String index() {
		return "hi";
	}

}
