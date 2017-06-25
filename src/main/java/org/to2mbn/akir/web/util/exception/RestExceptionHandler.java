package org.to2mbn.akir.web.util.exception;

import javax.annotation.Priority;
import org.springframework.core.Ordered;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice(annotations = { RestController.class })
@Priority(Ordered.HIGHEST_PRECEDENCE)
public class RestExceptionHandler {

	@ExceptionHandler
	public ResponseEntity<ErrorMessage> onException(Throwable ex) {
		ErrorMessage msg = WebExceptionResolver.onException(ex);
		return new ResponseEntity<>(msg, HttpStatus.valueOf(msg.getCode()));
	}

}
