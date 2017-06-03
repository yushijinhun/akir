package org.to2mbn.akir.web;

import javax.servlet.http.HttpServletResponse;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalRestControllerAdvice {

	@ExceptionHandler(Exception.class)
	public ErrorMessage onError(Exception exception, HttpServletResponse response) {
		ErrorMessage msg = new ErrorMessage();
		msg.setError(exception.getClass().getSimpleName());
		msg.setErrorMessage(exception.getMessage());
		response.setStatus(400); // TODO: better error code
		return msg;
	}

}
