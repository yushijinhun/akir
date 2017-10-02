package org.to2mbn.akir.web.util;

import static org.springframework.util.ReflectionUtils.findField;
import static org.springframework.util.ReflectionUtils.getField;
import static org.springframework.util.ReflectionUtils.makeAccessible;
import java.lang.reflect.Field;
import java.util.Optional;
import javax.servlet.http.HttpServletRequest;
import org.springframework.boot.web.servlet.error.DefaultErrorAttributes;
import org.springframework.core.annotation.AnnotatedElementUtils;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.to2mbn.akir.web.util.exception.RestExceptionHandler;

public final class WebUtils {

	private WebUtils() {}

	public static boolean isAjax(HttpServletRequest request) {
		return Boolean.TRUE.equals(request.getAttribute(RestExceptionHandler.ATTR_REST_REQ))
				|| "XMLHttpRequest".equals(request.getHeader("X-Requested-With"));
	}

	public static String getRelativePath(HttpServletRequest request) {
		String path = request.getRequestURI();
		String query = request.getQueryString();
		return query == null ? path : path + "?" + query;
	}

	public static Optional<ResponseStatus> getResponseStatus(Throwable e) {
		return Optional.ofNullable(AnnotatedElementUtils.findMergedAnnotation(e.getClass(), ResponseStatus.class));
	}

	public static final String ERROR_ATTRIBUTE;
	static {
		Field f_ERROR_ATTRIBUTE = findField(DefaultErrorAttributes.class, "ERROR_ATTRIBUTE");
		makeAccessible(f_ERROR_ATTRIBUTE);
		ERROR_ATTRIBUTE = (String) getField(f_ERROR_ATTRIBUTE, null);
	}

}
