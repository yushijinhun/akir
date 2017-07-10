package org.to2mbn.akir.web.util.exception;

import static javax.servlet.http.HttpServletResponse.SC_INTERNAL_SERVER_ERROR;
import static org.to2mbn.akir.web.util.WebUtils.ERROR_ATTRIBUTE;
import static org.to2mbn.akir.web.util.exception.ErrorMessage.E_INTERNAL_SERVER_ERROR;
import java.io.IOException;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.GenericFilterBean;
import org.springframework.web.servlet.DispatcherServlet;
import org.springframework.web.util.NestedServletException;

/**
 * Catches exceptions thrown from {@link DispatcherServlet} to prevent
 * information exposure.
 */
@Component
public class ExceptionCatcherFilter extends GenericFilterBean {

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		try {
			chain.doFilter(request, response);
		} catch (Throwable e) {
			if (e instanceof IOException
					|| (e instanceof ServletException && !(e instanceof NestedServletException)))
				throw e;

			if (request instanceof HttpServletRequest && response instanceof HttpServletResponse) {
				processException(e, (HttpServletRequest) request, (HttpServletResponse) response);
				return;
			}
			throw e;
		}
	}

	private void processException(Throwable ex, HttpServletRequest request, HttpServletResponse response) throws IOException {
		request.setAttribute(ERROR_ATTRIBUTE, ex);
		response.sendError(SC_INTERNAL_SERVER_ERROR, E_INTERNAL_SERVER_ERROR);
	}
}
