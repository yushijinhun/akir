package org.to2mbn.akir.core.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class EmailVerifyCode {

	@Id
	private String email;

	@Column(nullable = false)
	private String code;

	private long sendTime;
	private long availableBefore;

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
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
