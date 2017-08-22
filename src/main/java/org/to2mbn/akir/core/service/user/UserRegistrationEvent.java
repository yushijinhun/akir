package org.to2mbn.akir.core.service.user;

import java.util.UUID;
import org.springframework.context.ApplicationEvent;

public class UserRegistrationEvent extends ApplicationEvent {

	private static final long serialVersionUID = 1L;

	private UUID userId;

	public UserRegistrationEvent(Object source, UUID userId) {
		super(source);
		this.userId = userId;
	}

	public UUID getUserId() {
		return userId;
	}

}
