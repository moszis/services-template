package com.moszis.template.service.core.exceptions;

import java.util.HashMap;

import com.moszis.template.service.core.constants.ErrorCodes;

public class ValidationErrorResponse extends ErrorResponse {

	/**
	 * Map of validation errors. Contains the field/param that was in error as
	 * the key and the reason for the validation error.
	 */
	private HashMap<String, String> validationErrors;

	private String code;

	public ValidationErrorResponse(String message, HashMap<String, String> validationErrors) {
		super(message, ErrorCodes.INVALID_DATA.value());
		this.validationErrors = validationErrors;
	}

	public ValidationErrorResponse(String message, String errorCode, HashMap<String, String> validationErrors) {
		super(message, errorCode);
		this.validationErrors = validationErrors;
	}

	public HashMap<String, String> getValidationErrors() {
		return validationErrors;
	}

	public void setValidationErrors(HashMap<String, String> validationErrors) {
		this.validationErrors = validationErrors;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	@Override
	public String toString() {
		return "ValidationErrorResponse [validationErrors=" + validationErrors + ", toString()=" + super.toString() + "]";
	}

}