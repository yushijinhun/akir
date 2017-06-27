package org.to2mbn.akir.web.util.exception;

import javax.annotation.Priority;
import javax.servlet.http.HttpServletRequest;
import org.springframework.core.Ordered;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice(annotations = { RestController.class })
@Priority(Ordered.HIGHEST_PRECEDENCE)
public class RestExceptionHandler {

	public static final String ATTR_REST_REQ = "org.to2mbn.akir.web.restRequest";

	@ExceptionHandler
	public void onException(Throwable ex, HttpServletRequest request) throws Throwable {
		request.setAttribute(ATTR_REST_REQ, true);
		throw ex;
	}

}
