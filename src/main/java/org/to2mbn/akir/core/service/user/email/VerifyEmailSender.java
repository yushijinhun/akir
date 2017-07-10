package org.to2mbn.akir.core.service.user.email;

import java.io.UncheckedIOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.MessageFormat;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import javax.mail.Message.RecipientType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.mail.MailProperties;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.security.web.util.RedirectUrlBuilder;
import org.springframework.stereotype.Component;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;
import org.to2mbn.akir.core.model.EmailVerifyCode;
import org.to2mbn.akir.core.model.User;
import org.to2mbn.akir.core.service.AkirConfiguration;
import freemarker.template.Configuration;

@Component
public class VerifyEmailSender {

	private static final Logger LOGGER = LoggerFactory.getLogger(VerifyEmailSender.class);

	@Autowired
	private JavaMailSender mailSender;

	@Autowired
	private Configuration fmConfig;

	@Autowired
	private AkirConfiguration config;

	@Autowired
	private MailProperties mailProperties;

	@Async("emailExecutor")
	public void sendEmail(EmailVerifyCode code, User user) {
		LOGGER.info("Sending verify email to {}", user.getEmail());
		try {
			mailSender.send(msg -> {
				msg.setFrom(emailSender());
				msg.setRecipients(RecipientType.TO, code.getEmail());
				msg.setSubject(subject());
				msg.setContent(FreeMarkerTemplateUtils.processTemplateIntoString(fmConfig.getTemplate("email/verify-code.ftl"), model(code, user)), "text/html");
			});
		} catch (Throwable e) {
			LOGGER.warn("Couldn't send verify email to {}", user.getEmail(), e);
			return;
		}
		LOGGER.info("Sent verify email to {}", user.getEmail());
	}

	private String emailSender() {
		return Optional.ofNullable(config.getVerifyEmailSender())
				.orElseGet(mailProperties::getUsername);
	}

	private String subject() {
		return MessageFormat.format("Verify your email - {0}", config.getName());
	}

	private Object model(EmailVerifyCode code, User user) {
		Map<String, Object> model = new HashMap<>();
		model.put("email", code.getEmail());
		model.put("username", user.getName());
		model.put("server_name", config.getName());
		model.put("verify_url", verifyUrl(code));
		model.put("title", subject());
		return model;
	}

	private String verifyUrl(EmailVerifyCode code) {
		RedirectUrlBuilder url = config.rootUrl();
		url.setServletPath("/email_verify/do_verify");
		url.setQuery(MessageFormat.format("email={0}&code={1}", urlEncode(code.getEmail()), urlEncode(code.getCode())));
		return url.getUrl();
	}

	private String urlEncode(String str) {
		try {
			return URLEncoder.encode(str, "utf-8");
		} catch (UnsupportedEncodingException e) {
			throw new UncheckedIOException(e);
		}
	}

}
