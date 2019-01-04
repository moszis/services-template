package com.moszis.template.service.core.exceptions;


import com.fasterxml.jackson.annotation.JsonInclude;
import com.moszis.template.service.core.constants.ErrorCodes;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class ErrorResponse {

	/**
	 * Main error message. Required
	 */
    private String message = null;

	private String errorCode = ErrorCodes.SYSTEM_ERROR.value();

    public ErrorResponse(String message) {
        super();
        this.message = message;
    }

	public ErrorResponse(String message, String errorCode) {
		super();
		this.message = message;
		this.errorCode = errorCode;
	}

    public ErrorResponse() {
        super();
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

	public String getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}

	@Override
	public String toString() {
		return "ErrorResponse [message=" + message + ", errorCode=" + errorCode + "]";
	}



}
