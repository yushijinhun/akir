package org.to2mbn.akir.core.service.user.email;

public class AlreadyVerifiedException extends EmailVerifyException {

	private static final long serialVersionUID = 1L;

	public AlreadyVerifiedException() {}

	public AlreadyVerifiedException(String message) {
		super(message);
	}

}
