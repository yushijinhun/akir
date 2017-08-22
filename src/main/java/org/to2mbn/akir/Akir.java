package org.to2mbn.akir;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Locale;
import java.util.Properties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Akir {

	private static final Logger LOGGER = LoggerFactory.getLogger(Akir.class);

	private static final String CONFIG_PATH = "application.yaml";
	private static final String DEFAULT_CONFIG_PATH = "/default-application.yaml";

	public static void main(String[] args) {
		Locale.setDefault(Locale.ENGLISH);
		if (doesConfigExist()) {
			startApplication(args);
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
		try (InputStream in = Akir.class.getResourceAsStream(DEFAULT_CONFIG_PATH)) {
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

	private static void startApplication(String[] args) {
		SpringApplication app = new SpringApplication(Akir.class);
		app.setDefaultProperties(getDefaultProperties());
		app.run(args);
	}

	private static Properties getDefaultProperties() {
		Properties properties = new Properties();
		try {
			loadProperties("/git.properties", properties);
			loadProperties("/META-INF/build-info.properties", properties);
		} catch (IOException e) {
			LOGGER.warn("Unable to load default properties", e);
		}
		return properties;
	}

	private static void loadProperties(String location, Properties properties) throws IOException {
		try (InputStream in = Akir.class.getResourceAsStream(location)) {
			if (in != null) {
				properties.load(in);
			}
		}
	}

}
