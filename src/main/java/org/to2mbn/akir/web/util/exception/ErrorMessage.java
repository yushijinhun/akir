package org.to2mbn.akir.web.util.exception;

import org.springframework.http.HttpStatus;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

public class ErrorMessage {

	public static final String CLIENT_ERROR = "client_error";
	public static final String SERVER_ERROR = "server_error";
	public static final String UNKNOWN_ERROR = "unknown_error";
	public static final int DEFAULT_ERROR_CODE = HttpStatus.INTERNAL_SERVER_ERROR.value();

	private String error;
	private int code;

	@JsonInclude(Include.NON_NULL)
	private String details;

	public ErrorMessage(String error, int code, String details) {
		this.error = error;
		this.code = code;
		this.details = details != null && details.trim().isEmpty() ? null : details;
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

}
