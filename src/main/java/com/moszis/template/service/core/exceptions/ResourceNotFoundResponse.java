package com.moszis.template.service.core.exceptions;

import java.util.HashMap;

import com.moszis.template.service.core.constants.ErrorCodes;

public class ResourceNotFoundResponse extends ErrorResponse {
	private HashMap<String, String> errors;

	public ResourceNotFoundResponse(String message, HashMap<String, String> errors) {
		super();
		this.setErrorCode(ErrorCodes.RESOURCE_NOT_FOUND.value());
		this.setMessage(message);
		this.errors = errors;
	}

	public HashMap<String, String> getErrors() {
		return errors;
	}

	public void setErrors(HashMap<String, String> errors) {
		this.errors = errors;
	}

	@Override
	public String toString() {
		return "NotFoundErrorResponse [notFoundErrors=" + errors + ", toString()=" + super.toString() + "]";
	}
}
