package org.to2mbn.akir.core.service.user.email;

public class WrongVerifyCodeException extends EmailVerifyException {

	private static final long serialVersionUID = 1L;

	public WrongVerifyCodeException() {}

	public WrongVerifyCodeException(String message) {
		super(message);
	}

}
