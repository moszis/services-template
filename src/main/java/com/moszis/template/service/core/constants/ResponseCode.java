package com.moszis.template.service.core.constants;

/**
 * The enum Response code.
 */
public enum ResponseCode {

	/**
	 * The Success.
	 */
	SUCCESS(200, "Data returned successfully"),
	/**
	 * The Unauthorized no oauth token.
	 */
	UNAUTHORIZED_NO_OAUTH_TOKEN(401, "Missing Authorization Header"),
	/**
	 * The Unauthorized.
	 */
	UNAUTHORIZED(403, "Caller is not unauthorized to make this service call"),
	/**
	 * The Resource not found.
	 */
	RESOURCE_NOT_FOUND(410, "Resource is gone or id is invalid"),
	/**
	 * The Invalid data.
	 */
	INVALID_DATA(422, "Invalid argument(s) passed"),
	/**
	 * The External system error.
	 */
	EXTERNAL_SYSTEM_ERROR(500, "External system error occurred.  Please contact support if this error persists"),
	/**
	 * The System error.
	 */
	SYSTEM_ERROR(500, "A system error occurred.  Please contact support if this error persists");

	private final Integer statusCode;
	private final String message;

	ResponseCode(Integer statusCode, String message) {
		this.statusCode = statusCode;
		this.message = message;
	}

	/**
	 * Status code integer.
	 *
	 * @return the integer
	 */
	public Integer statusCode() {
		return statusCode;
	}

	/**
	 * Message string.
	 *
	 * @return the string
	 */
	public String message() {
		return message;
	}

}
