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
		if (user.isEmailVerified()) return;

		EmailVerifyCode code = createVerifyCodeFor(user);
		code = repository.save(code);
		verifyEmailSender.sendEmail(code, user);
	}

	public void verifyEmail(String email, String code) throws EmailVerifyException {
		email = email.toLowerCase();

		EmailVerifyCode verifyCode = repository.findByCode(code)
				.orElseThrow(() -> new WrongVerifyCodeException("Wrong verify code"));

		repository.delete(verifyCode);

		User user = userRepo.findById(verifyCode.getOwnerId())
				.orElseThrow(() -> new WrongVerifyCodeException("User not found"));

		if (user.isEmailVerified())
			throw new AlreadyVerifiedException("Email already verified");

		if (!user.getEmail().equals(email))
			throw new WrongVerifyCodeException("Wrong email");

		if (System.currentTimeMillis() > verifyCode.getAvailableBefore())
			throw new VerifyCodeExpiredException("Verify code has been expired");

		user.setEmailVerified(true);
		userRepo.save(user);

		// delete other verify codes
		repository.deleteByEmail(user.getEmail());

		LOGGER.info("User {} verified email {}", user.getId(), user.getEmail());
	}

	private EmailVerifyCode createVerifyCodeFor(User user) {
		EmailVerifyCode verifyCode = new EmailVerifyCode();
		verifyCode.setCode(codeGenerator.generate(codeLength));
		verifyCode.setEmail(user.getEmail());
		verifyCode.setOwnerId(user.getId());
		verifyCode.setSendTime(System.currentTimeMillis());
		verifyCode.setAvailableBefore(verifyCode.getSendTime() + availableTime);
		return verifyCode;
	}

}
