package org.to2mbn.akir.core.model;

import java.util.UUID;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import org.hibernate.annotations.Type;

@Entity
public class EmailVerifyCode {

	@Id
	private String code;

	@Column(nullable = false)
	private String email;

	@Column(nullable = false, length = 16)
	@Type(type = "uuid-binary")
	private UUID ownerId;

	private long sendTime;
	private long availableBefore;

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public UUID getOwnerId() {
		return ownerId;
	}

	public void setOwnerId(UUID ownerId) {
		this.ownerId = ownerId;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public long getSendTime() {
		return sendTime;
	}

	public void setSendTime(long sendTime) {
		this.sendTime = sendTime;
	}

	public long getAvailableBefore() {
		return availableBefore;
	}

	public void setAvailableBefore(long availableBefore) {
		this.availableBefore = availableBefore;
	}

}
