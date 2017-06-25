package org.to2mbn.akir.web.util.exception;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;

@Controller
public class AccessDeniedController implements AccessDeniedHandler {

	private static final String ATTR_REDIRECT_ACCESS_DENIED = "org.to2mbn.akir.web.redirectFromAccessDenied";
	private static final String ATTR_ACCESS_DENIED_EXCEPTION = "org.to2mbn.akir.web.accessDeniedException";

	@Autowired
	private MvcExceptionHandler mvcExceptionHandler;

	@Override
	public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {
		request.setAttribute(ATTR_REDIRECT_ACCESS_DENIED, true);
		request.setAttribute(ATTR_ACCESS_DENIED_EXCEPTION, accessDeniedException);
		request.getRequestDispatcher("/errors/access_denied").forward(request, response);
	}

	@RequestMapping("/errors/access_denied")
	@ResponseStatus(HttpStatus.FORBIDDEN)
	public Object onAccessDenied(HttpServletRequest request) {
		AccessDeniedException exception;
		if (Boolean.TRUE.equals(request.getAttribute(ATTR_REDIRECT_ACCESS_DENIED))) {
			exception = (AccessDeniedException) request.getAttribute(ATTR_ACCESS_DENIED_EXCEPTION);
		} else {
			// the method shouldn't be accessed directly
			exception = new AccessDeniedException("Access is denied");
		}

		ErrorMessage msg = WebExceptionResolver.onException(exception);

		if (isAjax(request)) {
			return new ResponseEntity<>(msg, HttpStatus.FORBIDDEN);
		} else {
			return mvcExceptionHandler.onException(msg);
		}
	}

	private boolean isAjax(HttpServletRequest request) {
		return "XMLHttpRequest".equals(request.getHeader("X-Requested-With"));
	}

}
