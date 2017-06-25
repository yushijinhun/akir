package org.to2mbn.akir.web.util.exception;

import java.text.MessageFormat;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.AnnotatedElementUtils;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.server.ResponseStatusException;

/*

 * controller should throw a checked exception if the exception is expected to be known by client(such as bad input)
   * the exception class should have a `@ResponseStatus` annotation, which defines how it is shown to client
     * the response format:
       ```json
       {
           "error": "${ResponseStatus.reason}", // (machine-readable)
           "code": ${ResponseStatus.code}, // (machine-readable)
           "details": "${exception.message}" // optional, which describes the details about the exception(human-readable)
       }
       ```
     * `error` should only contain lower-case letters, numbers, dot(.), underline(_)
       * if it is empty, `unknown_error` will be used
     * the http status code will be set to `code`
     * log in `info` level
     * if the exception class is missing `@ResponseStatus` annotation, `code` will be set to `500`

 * unchecked exceptions are used for server internal errors
   * details about the exception should be hidden from client
   * log in `warn` level
   * `error` is set to `server_error`
   * `code` is set to `500`
   * `details` is omitted
   * special cases:

     * ResponseStatusException
       * condition: `exception.status.series==CLIENT_ERROR`
       * log in `info` level
       * `error` is set to `client_error`
       * `code` is set to `exception.status.value`
       * `message` is set to `exception.reason`

     * AccessDeniedException
       * log in `info` level
       * `error` is set to `access_denied`
       * `code` is set to `403`

     * Error
       * log in `error` level

*/

public final class WebExceptionResolver {

	private static final Logger LOGGER = LoggerFactory.getLogger(WebExceptionResolver.class);

	private static boolean isChecked(Throwable ex) {
		return ex instanceof Exception && !(ex instanceof RuntimeException);
	}

	public static ErrorMessage onException(Throwable ex) {
		String log = toLogString();
		if (isChecked(ex)) {
			LOGGER.info(log, ex);
			ResponseStatus status = AnnotatedElementUtils.findMergedAnnotation(ex.getClass(), ResponseStatus.class);
			if (status == null) {
				return new ErrorMessage(ErrorMessage.UNKNOWN_ERROR, ErrorMessage.DEFAULT_ERROR_CODE, ex.getMessage());
			} else {
				String error = status.reason();
				if (error.trim().isEmpty())
					error = ErrorMessage.UNKNOWN_ERROR;
				return new ErrorMessage(error, status.code().value(), ex.getMessage());
			}

		} else {

			if (ex instanceof ResponseStatusException) {
				ResponseStatusException rse = (ResponseStatusException) ex;
				if (rse.getStatus().is4xxClientError()) {
					LOGGER.info(log, ex);
					return new ErrorMessage(ErrorMessage.CLIENT_ERROR, rse.getStatus().value(), rse.getReason());
				}
			}

			if (ex instanceof AccessDeniedException) {
				LOGGER.info(log, ex);
				return new ErrorMessage("access_denied", HttpStatus.FORBIDDEN.value(), null);
			}

			if (ex instanceof Error) {
				LOGGER.error(log, ex);
			} else {
				LOGGER.warn(log, ex);
			}

			return new ErrorMessage(ErrorMessage.SERVER_ERROR, ErrorMessage.DEFAULT_ERROR_CODE, null);

		}
	}

	private static String toLogString() {
		return MessageFormat.format("user={0};",
				Optional.ofNullable(SecurityContextHolder.getContext().getAuthentication())
						.map(Authentication::getPrincipal)
						.orElse(null));
	}

	private WebExceptionResolver() {}

}
