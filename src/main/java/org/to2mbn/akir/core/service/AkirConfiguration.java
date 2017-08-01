package org.to2mbn.akir.core.service;

import java.net.URI;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.security.web.util.RedirectUrlBuilder;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties("akir-server")
public class AkirConfiguration {

	private String name;
	private boolean requireEmailVerfied;
	private String url;
	private String verifyEmailSender;
	private String texturesStorage;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public boolean isRequireEmailVerfied() {
		return requireEmailVerfied;
	}

	public void setRequireEmailVerfied(boolean requireEmailVerfied) {
		this.requireEmailVerfied = requireEmailVerfied;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getVerifyEmailSender() {
		return verifyEmailSender;
	}

	public void setVerifyEmailSender(String verifyEmailSender) {
		this.verifyEmailSender = verifyEmailSender;
	}

	public String getTexturesStorage() {
		return texturesStorage;
	}

	public void setTexturesStorage(String texturesStorage) {
		this.texturesStorage = texturesStorage;
	}

	public RedirectUrlBuilder rootUrl() {
		RedirectUrlBuilder urlBuilder = new RedirectUrlBuilder();
		URI uri = URI.create(getUrl());
		urlBuilder.setScheme(uri.getScheme());
		urlBuilder.setServerName(uri.getHost());
		urlBuilder.setPort(uri.getPort());
		return urlBuilder;
	}

}
