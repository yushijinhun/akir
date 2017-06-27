package org.to2mbn.akir.web.util.exception;

import static org.to2mbn.akir.web.util.WebUtils.isAjax;
import static org.to2mbn.akir.web.util.WebUtils.getRelativePath;
import java.text.MessageFormat;
import java.util.Map;
import java.util.Optional;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.web.servlet.error.ErrorAttributes;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.ModelAndView;
import org.to2mbn.akir.core.service.user.UserService;

@Controller
public class ErrorController implements org.springframework.boot.autoconfigure.web.servlet.error.ErrorController {

	private static final Logger LOGGER = LoggerFactory.getLogger("org.to2mbn.akir.web.ErrorHandling");

	@Autowired
	private ErrorAttributes errorAttributes;

	@RequestMapping("/error")
	public Object handleError(HttpServletRequest request) {
		WebRequest req = new ServletWebRequest(request);
		Map<String, Object> attributes = errorAttributes.getErrorAttributes(req, false);
		int code = (int) attributes.get("status");
		if (code == 999)
			throw new AccessDeniedException("Access is denied");
		ErrorMessage msg = new ErrorMessage((String) attributes.get("error"), code, (String) attributes.get("message"));

		String logMsg = MessageFormat.format("ip={1}; session={2}; user={3}; url={4}; {0}",
				msg,
				request.getRemoteAddr(),
				Optional.ofNullable(request.getSession(false)).map(HttpSession::getId).orElse(null),
				UserService.getCurrentAuthentication().map(Authentication::getPrincipal).orElse(null),
				getRelativePath(request));
		Throwable ex = errorAttributes.getError(req);
		if (code >= 500 && code <= 599) {
			// it's a server internal error
			LOGGER.warn(logMsg, ex);
		} else {
			LOGGER.debug(logMsg, ex);
		}

		if (isAjax(request)) {
			return msg.toResponseEntity();
		} else {
			ModelAndView view = new ModelAndView("error", msg.getStatus());
			view.addObject("error", msg);
			return view;
		}
	}

	@Override
	public String getErrorPath() {
		return "/error";
	}

}
