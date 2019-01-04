package com.moszis.template.service.core.constants;

/**
 * The enum Error codes.
 */
public enum ErrorCodes {

	/**
	 * Unauthorized no oauth token error codes.
	 */
	UNAUTHORIZED_NO_OAUTH_TOKEN("UNAUTHORIZED_NO_OAUTH_TOKEN"), // 401
	/**
	 * Unauthorized error codes.
	 */
	UNAUTHORIZED("UNAUTHORIZED"), // 403
	/**
	 * Resource not found error codes.
	 */
	RESOURCE_NOT_FOUND("RESOURCE_NOT_FOUND"), // 410
	/**
	 * Invalid data error codes.
	 */
	INVALID_DATA("INVALID_DATA"), // 422
	/**
	 * Unnecessary data error codes.
	 */
	UNNECESSARY_DATA("UNNECESSARY_DATA"), // 422
	/**
	 * External data not found error codes.
	 */
	EXTERNAL_DATA_NOT_FOUND("EXTERNAL_DATA_NOT_FOUND"), // 422
	/**
	 * External system error error codes.
	 */
	EXTERNAL_SYSTEM_ERROR("EXTERNAL_SYSTEM_ERROR"), // 500
	/**
	 * System error error codes.
	 */
	SYSTEM_ERROR("EXTERNAL_SYSTEM_ERROR"); // 500

	private final String value;

	ErrorCodes(String value) {
		this.value = value;
	}

	/**
	 * Value string.
	 *
	 * @return the string
	 */
	public String value() {
		return value;
	}

}
