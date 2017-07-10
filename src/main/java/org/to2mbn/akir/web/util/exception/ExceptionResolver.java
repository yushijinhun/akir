package org.to2mbn.akir.web.util.exception;

import static javax.servlet.http.HttpServletResponse.SC_FORBIDDEN;
import static javax.servlet.http.HttpServletResponse.SC_INTERNAL_SERVER_ERROR;
import static org.to2mbn.akir.web.util.exception.ErrorMessage.E_ACCESS_DENIED;
import static org.to2mbn.akir.web.util.exception.ErrorMessage.E_INTERNAL_SERVER_ERROR;
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

	@Override
	public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
		try {
			if (ex instanceof AccessDeniedException) {
				response.sendError(SC_FORBIDDEN, E_ACCESS_DENIED);
			} else {
				response.sendError(SC_INTERNAL_SERVER_ERROR, E_INTERNAL_SERVER_ERROR);
			}
		} catch (Exception e) {
			LOGGER.warn("Handling of [{}] resulted in Exception", ex.getClass().getName(), e);
		}
		return new ModelAndView();
	}

}
