package org.to2mbn.akir.core.service.user.email;

abstract public class EmailVerifyException extends Exception {

	private static final long serialVersionUID = 1L;

	public EmailVerifyException() {}

	public EmailVerifyException(String message) {
		super(message);
	}

}
