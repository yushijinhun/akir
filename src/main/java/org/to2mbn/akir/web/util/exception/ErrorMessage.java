package org.to2mbn.akir.web.util.exception;

import java.text.MessageFormat;
import java.util.Objects;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

public class ErrorMessage {

	public static final String E_INTERNAL_SERVER_ERROR = "error.internal_server_error";
	public static final String E_ACCESS_DENIED = "error.access_denied";

	private String error;
	private int code;

	@JsonInclude(Include.NON_NULL)
	private String details;

	public ErrorMessage(String error, HttpStatus status, String details) {
		this(error, status.value(), details);
	}

	public ErrorMessage(String error, int code, String details) {
		this.error = Objects.requireNonNull(error);
		this.code = code;
		this.details = details;
	}

	public String getError() {
		return error;
	}

	public int getCode() {
		return code;
	}

	public String getDetails() {
		return details;
	}

	public ResponseEntity<ErrorMessage> toResponseEntity() {
		return new ResponseEntity<>(this, getStatus());
	}

	@JsonIgnore
	public HttpStatus getStatus() {
		return HttpStatus.valueOf(code);
	}

	@Override
	public String toString() {
		return MessageFormat.format("[error={0}, code={1}, details={2}]", error, code, details);
	}

}
