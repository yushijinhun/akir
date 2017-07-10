package org.to2mbn.akir.core.service.user.email;

import java.security.SecureRandom;
import java.util.concurrent.TimeUnit;
import org.apache.commons.text.RandomStringGenerator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.to2mbn.akir.core.model.EmailVerifyCode;
import org.to2mbn.akir.core.model.User;
import org.to2mbn.akir.core.repository.EmailVerifyCodeRepository;
import org.to2mbn.akir.core.repository.UserRepository;
import org.to2mbn.akir.core.service.AkirConfiguration;
import org.to2mbn.akir.core.service.user.UserRegistrationEvent;

@Component
public class EmailVerifyService {

	private static final Logger LOGGER = LoggerFactory.getLogger(EmailVerifyService.class);

	@Autowired
	private EmailVerifyCodeRepository repository;

	@Autowired
	private UserRepository userRepo;

	@Autowired
	private VerifyEmailSender verifyEmailSender;

	@Autowired
	private AkirConfiguration config;

	private RandomStringGenerator codeGenerator;

	private long availableTime = TimeUnit.HOURS.toMillis(8);
	private int codeLength = 64;

	public EmailVerifyService() {
		codeGenerator = new RandomStringGenerator.Builder()
				.usingRandom(new SecureRandom()::nextInt)
				.withinRange('0', 'z')
				.filteredBy(ch -> (ch >= '0' && ch <= '9') || (ch >= 'a' && ch <= 'z'))
				.build();
	}

	@EventListener
	public void onUserRegister(UserRegistrationEvent event) {
		userRepo.findById(event.getUserId()).ifPresent(user -> {
			if (config.isRequireEmailVerfied()) {
				sendVerifyEmail(user);
			} else {
				user.setEmailVerified(true);
				userRepo.save(user);
			}
		});
	}

	public void sendVerifyEmail(User user) {
		// TODO: rate limit
		if (user.isEmailVerified())
			throw new IllegalStateException("Email already verified");

		EmailVerifyCode code = repository.findById(user.getEmail())
				.orElseGet(() -> {
					EmailVerifyCode newCode = new EmailVerifyCode();
					newCode.setEmail(user.getEmail());
					return newCode;
				});
		renewVerifyCode(code);
		repository.save(code);
		verifyEmailSender.sendEmail(code, user);
	}

	public void verifyEmail(String email, String code) throws EmailVerifyException {
		User user = userRepo.findById(email.toLowerCase())
				.orElseThrow(() -> new WrongVerifyCodeException("User not exists"));
		if (user.isEmailVerified())
			throw new AlreadyVerifiedException("Email already verified");
		EmailVerifyCode verifyCode = repository.findById(email)
				.orElseThrow(() -> new WrongVerifyCodeException("Verify code not exists"));
		if (System.currentTimeMillis() > verifyCode.getAvailableBefore())
			throw new VerifyCodeExpiredException("Verify code has been expired");
		if (!verifyCode.getCode().equals(code))
			throw new WrongVerifyCodeException("Wrong verify code");

		repository.delete(verifyCode);
		user.setEmailVerified(true);
		userRepo.save(user);

		LOGGER.info("User {} email verified", user.getEmail());
	}

	private void renewVerifyCode(EmailVerifyCode verifyCode) {
		verifyCode.setCode(codeGenerator.generate(codeLength));
		verifyCode.setSendTime(System.currentTimeMillis());
		verifyCode.setAvailableBefore(verifyCode.getSendTime() + availableTime);
	}

}
