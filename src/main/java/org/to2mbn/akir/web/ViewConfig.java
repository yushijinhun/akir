package org.to2mbn.akir.web;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties("view-config")
public class ViewConfig {

	private String assetsCdn;

	public String getAssetsCdn() {
		return assetsCdn;
	}

	public void setAssetsCdn(String assetsCdn) {
		this.assetsCdn = assetsCdn;
	}

}
