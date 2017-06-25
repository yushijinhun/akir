package org.to2mbn.akir.core.service.user.email;

public class VerifyCodeExpiredException extends EmailVerifyException {

	private static final long serialVersionUID = 1L;

	public VerifyCodeExpiredException() {}

	public VerifyCodeExpiredException(String message) {
		super(message);
	}

}
