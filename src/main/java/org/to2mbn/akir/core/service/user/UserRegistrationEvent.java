package org.to2mbn.akir.core.service.user;

import org.springframework.context.ApplicationEvent;

public class UserRegistrationEvent extends ApplicationEvent {

	private static final long serialVersionUID = 1L;

	private String userId;

	public UserRegistrationEvent(Object source, String userId) {
		super(source);
		this.userId = userId;
	}

	public String getUserId() {
		return userId;
	}

}
