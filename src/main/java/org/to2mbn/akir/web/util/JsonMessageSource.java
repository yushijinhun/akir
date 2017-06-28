package org.to2mbn.akir.web.util;

import static org.springframework.util.ReflectionUtils.findField;
import static org.springframework.util.ReflectionUtils.getField;
import static org.springframework.util.ReflectionUtils.makeAccessible;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.lang.reflect.Type;
import java.util.Map;
import java.util.Optional;
import java.util.Properties;
import java.util.concurrent.ConcurrentMap;
import javax.annotation.PostConstruct;
import org.apache.commons.lang3.reflect.TypeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;
import com.fasterxml.jackson.databind.ObjectMapper;

@Component(AbstractApplicationContext.MESSAGE_SOURCE_BEAN_NAME)
public class JsonMessageSource extends ReloadableResourceBundleMessageSource {

	@PostConstruct
	private void init() {
		setBasename("/static/js/lang/locale");
		setUseCodeAsDefaultMessage(true);
		setAlwaysUseMessageFormat(true);
	}

	private void addMetadata(String filename, Map<String, String> properties) {
		extractLocale(filename).ifPresent(locale -> properties.put("metadata.locale", locale));
	}

	private Optional<String> extractLocale(String filename) {
		// delete baseName+"_"
		for (String baseName : getBasenameSet()) {
			String prefix = baseName + "_";
			if (filename.startsWith(prefix)) {
				return Optional.of(filename.substring(prefix.length()));
			}
		}
		return Optional.empty();
	}

	@Autowired
	private ObjectMapper objectMapper;

	private ResourceLoader resourceLoader = new DefaultResourceLoader();

	@Override
	protected PropertiesHolder refreshProperties(String filename, @Nullable PropertiesHolder propHolder) {
		long refreshTimestamp = (getCacheMillis() < 0 ? -1 : System.currentTimeMillis());
		Resource resource = resourceLoader.getResource(filename + ".json");
		if (resource.exists()) {
			long fileTimestamp = -1;
			if (getCacheMillis() >= 0) {
				try {
					fileTimestamp = resource.lastModified();
					if (propHolder != null && propHolder.getFileTimestamp() == fileTimestamp) {
						logger.debug("Re-caching properties for filename [" + filename + "] - file hasn't been modified");
						propHolder.setRefreshTimestamp(refreshTimestamp);
						return propHolder;
					}
				} catch (IOException ex) {
					logger.debug(resource + " could not be resolved in the file system - assuming that it hasn't changed", ex);
					fileTimestamp = -1;
				}
			}
			try {
				Properties props = loadProperties(resource, filename);
				propHolder = new PropertiesHolder(props, fileTimestamp);
			} catch (IOException ex) {
				logger.warn("Could not parse properties file [" + resource.getFilename() + "]", ex);
				propHolder = new PropertiesHolder();
			}
		} else {
			logger.debug("No properties file found for [" + filename + "]");
			propHolder = new PropertiesHolder();
		}

		propHolder.setRefreshTimestamp(refreshTimestamp);
		getCachedProperties().put(filename, propHolder);
		return propHolder;
	}

	private static final Type TYPE_STRING_MAP = TypeUtils.parameterize(Map.class, String.class, String.class);

	@Override
	protected Properties loadProperties(Resource resource, String filename) throws IOException {
		Map<String, String> result;
		try (InputStream in = resource.getInputStream()) {
			result = objectMapper.readValue(in, objectMapper.constructType(TYPE_STRING_MAP));
		}
		addMetadata(filename, result);
		Properties properties = new Properties();
		properties.putAll(result);
		return properties;
	}

	private static final Field f_cachedProperties = findField(ReloadableResourceBundleMessageSource.class, "cachedProperties");
	static {
		makeAccessible(f_cachedProperties);
	}

	@SuppressWarnings("unchecked")
	private ConcurrentMap<String, PropertiesHolder> getCachedProperties() {
		return (ConcurrentMap<String, PropertiesHolder>) getField(f_cachedProperties, this);
	}
}
