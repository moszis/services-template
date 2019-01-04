package com.moszis.template.service.core.exceptions;

import com.moszis.template.service.core.constants.ErrorCodes;

public class SystemErrorResponse extends ErrorResponse {

	/**
	 * For unexpected errors a reference to the errors which can be used by
	 * 
	 * support to track down the error (in GUID format) (
	 */
	private String unexpectedErrorReferenceCode = null;
	private String additionalInformation = null;

	public SystemErrorResponse(String message, String unexpectedErrorReferenceCode, String additionalInformation) {
		super(message);
		setErrorCode(ErrorCodes.SYSTEM_ERROR.value());
		this.additionalInformation = additionalInformation;
		this.unexpectedErrorReferenceCode = unexpectedErrorReferenceCode;
	}

	public SystemErrorResponse(String message, String unexpectedErrorReferenceCode) {
		super(message);

		this.unexpectedErrorReferenceCode = unexpectedErrorReferenceCode;
	}

	public String getAdditionalInformation() {
		return this.additionalInformation;
	}

	public void setAdditionalInformation(String additionalInformation) {
		this.additionalInformation = additionalInformation;
	}

	public String getUnexpectedErrorReferenceCode() {
		return this.unexpectedErrorReferenceCode;
	}


	public void setUnexpectedErrorReferenceCode(String unexpectedErrorReferenceCode) {
		this.unexpectedErrorReferenceCode = unexpectedErrorReferenceCode;
	}

	@Override
	public String toString() {
		return "SystemErrorResponse [unexpectedErrorReferenceCode=" + this.unexpectedErrorReferenceCode + ", toString()=" + super.toString() + "]";
	}

}
