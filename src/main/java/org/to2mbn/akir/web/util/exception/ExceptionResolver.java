package org.to2mbn.akir.web.util.exception;

import javax.annotation.Priority;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.Ordered;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

@Component
@Priority(Ordered.LOWEST_PRECEDENCE)
public class ExceptionResolver implements HandlerExceptionResolver {

	private static final Logger LOGGER = LoggerFactory.getLogger(ExceptionResolver.class);

	private static boolean isChecked(Throwable ex) {
		return ex instanceof Exception && !(ex instanceof RuntimeException);
	}

	@Override
	public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
		try {
			if (isChecked(ex)) {
				response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, ex.getMessage());
			} else {
				if (ex instanceof AccessDeniedException) {
					response.sendError(HttpServletResponse.SC_FORBIDDEN, "access_denied");
				} else {
					response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "internal_server_error");
				}
			}
		} catch (Exception e) {
			LOGGER.warn("Handling of [" + ex.getClass().getName() + "] resulted in Exception", e);
		}
		return new ModelAndView();
	}

}
