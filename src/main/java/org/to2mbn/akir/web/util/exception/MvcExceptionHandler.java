package org.to2mbn.akir.web.util.exception;

import javax.annotation.Priority;
import org.springframework.core.Ordered;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
@Priority(Ordered.LOWEST_PRECEDENCE)
public class MvcExceptionHandler {

	@ExceptionHandler
	public Object onException(Throwable ex) {
		return onException(WebExceptionResolver.onException(ex));
	}

	public Object onException(ErrorMessage msg) {
		// FIXME: user-friendly error message
		return new ResponseEntity<>(msg, HttpStatus.valueOf(msg.getCode()));
	}

}
