package com.moszis.template.service.core.util;


import javax.ws.rs.core.Response;

import com.moszis.template.service.core.constants.ErrorCodes;
import com.moszis.template.service.core.constants.InterfaceConstants;
import com.moszis.template.service.core.exceptions.ErrorResponse;
import com.moszis.template.service.core.exceptions.ResourceNotFoundResponse;
import com.moszis.template.service.core.exceptions.SystemErrorResponse;
import com.moszis.template.service.core.exceptions.ValidationErrorResponse;

import java.util.HashMap;

/**
 * The type Standard response builder.
 *
 * @author chicosb Used to build standard JSON responses including errors and successful         JSON responses
 */
public class StandardResponseBuilder {

	/**
	 * Unauthorized response.
	 *
	 * @return the response
	 */
	public static Response unauthorized() {
		return buildErrorResponse(403, ErrorCodes.UNAUTHORIZED.value(), InterfaceConstants.RETURN_CODE_403_MESSAGE);
	}

	/**
	 * Errors response.
	 *
	 * @param validationHelper the validation helper
	 * @return the response
	 */
	public static Response errors(StandardValidationHelper validationHelper) {
		// Security errors take precedence with respect to the HTPP response, process them first.
		if (validationHelper.hasSecurityErrors()) {
			return unauthorized(validationHelper.getSecurityErrors());
		}

		// Not found errors are processed next.
		if (validationHelper.hasNotFoundError()) {
			return resourceNotFoundError(validationHelper.getNotFoundErrors());
		}

		// Special Handling for the new error response codes
		if (validationHelper.getErrorResponseCode() != null) {
			switch (validationHelper.getErrorResponseCode()) {
			case "EXTERNAL_SYSTEM_ERROR":
				return StandardResponseBuilder.externalSystemError();
			case "EXTERNAL_DATA_NOT_FOUND":
			case "UNNECESSARY_DATA":
			case "INVALID_DATA":
				Response.ResponseBuilder responseBuilder = Response.status(422);
				responseBuilder.entity(new ValidationErrorResponse(InterfaceConstants.RETURN_CODE_422_MESSAGE,
						validationHelper.getErrorResponseCode(), validationHelper.getErrors()));
				return responseBuilder.build();
			default:
				break;
			}
		}

		// Finally process any validation errors
		if (validationHelper.hasValidationErrors()) {
			return validationError(validationHelper.getValidationErrors());
		}

		// This should never happen, but include as a sanity check
		return success(null);
	}

	/**
	 * Returns a authorization error message with a map of fields / errors
	 *
	 * @param authorizationErrors the authorization errors
	 * @return response response
	 */
	public static Response authorizationErrors(HashMap<String, String> authorizationErrors) {
		Response.ResponseBuilder responseBuilder = Response.status(422);
		responseBuilder.entity(new ValidationErrorResponse(InterfaceConstants.RETURN_CODE_403_MESSAGE,
				ErrorCodes.UNAUTHORIZED.value(), authorizationErrors));
		return responseBuilder.build();
	}

	/**
	 * Returns a authorization error message with a map of fields / errors
	 *
	 * @param authorizationErrors the authorization errors
	 * @return response response
	 */
	public static Response unauthorized(HashMap<String, String> authorizationErrors) {
		Response.ResponseBuilder responseBuilder = Response.status(403);
		responseBuilder.entity(new ValidationErrorResponse(InterfaceConstants.RETURN_CODE_403_MESSAGE,
				ErrorCodes.UNAUTHORIZED.value(), authorizationErrors));
		return responseBuilder.build();
	}

	/**
	 * Unauthorized response.
	 *
	 * @param customErrorMessage the custom error message
	 * @return the response
	 */
	public static Response unauthorized(String customErrorMessage) {
		return buildErrorResponse(403, ErrorCodes.UNAUTHORIZED.value(),customErrorMessage);
	}

	/**
	 * Invalid auth token response.
	 *
	 * @return the response
	 */
	public static Response invalidAuthToken() {
		return buildErrorResponse(401, ErrorCodes.UNAUTHORIZED_NO_OAUTH_TOKEN.value(), InterfaceConstants.RETURN_CODE_401_MESSAGE);
	}

	/**
	 * Resource not found response.
	 *
	 * @return the response
	 */
	public static Response resourceNotFound() {
		return buildErrorResponse(410, ErrorCodes.RESOURCE_NOT_FOUND.value(), InterfaceConstants.RETURN_CODE_410_MESSAGE);
	}

	/**
	 * Resource not found response.
	 *
	 * @param customErrorMessage the custom error message
	 * @return the response
	 */
	public static Response resourceNotFound(String customErrorMessage) {
		return buildErrorResponse(410, ErrorCodes.RESOURCE_NOT_FOUND.value(), customErrorMessage);
	}

	/**
	 * Data not found in external system response.
	 *
	 * @param customErrorMessage the custom error message
	 * @return the response
	 */
	public static Response dataNotFoundInExternalSystem(String customErrorMessage) {
		return buildErrorResponse(422, ErrorCodes.EXTERNAL_DATA_NOT_FOUND.value(), customErrorMessage);
	}

	public static Response dataNotFoundInExternalSystem(HashMap<String, String> validationErrors) {
		Response.ResponseBuilder responseBuilder = Response.status(422);
		responseBuilder.entity(new ValidationErrorResponse(InterfaceConstants.RETURN_CODE_422_MESSAGE,
				ErrorCodes.EXTERNAL_DATA_NOT_FOUND.value(), validationErrors));
		return responseBuilder.build();
	}

	/**
	 * External system error response.
	 *
	 * @return the response
	 */
	public static Response externalSystemError() {
		return buildErrorResponse(500, ErrorCodes.EXTERNAL_SYSTEM_ERROR.value(),
				InterfaceConstants.RETURN_CODE_500_EXTERNAL_SYSTEM_ERROR);
	}

	/**
	 * System error response.
	 *
	 * @param unexpectedErrorReferenceCode the unexpected error reference code
	 * @return the response
	 */
	public static Response systemError(String unexpectedErrorReferenceCode) {

		Response.ResponseBuilder responseBuilder = Response.status(500);

		responseBuilder.entity(new SystemErrorResponse(InterfaceConstants.RETURN_CODE_500_MESSAGE, unexpectedErrorReferenceCode));
		return responseBuilder.build();
	}

	/**
	 * System error response.
	 *
	 * @param unexpectedErrorReferenceCode the unexpected error reference code
	 * @param message                      the message
	 * @return the response
	 */
	public static Response systemError(String unexpectedErrorReferenceCode, String message) {

		Response.ResponseBuilder responseBuilder = Response.status(500);

		responseBuilder.entity(new SystemErrorResponse(InterfaceConstants.RETURN_CODE_500_MESSAGE, unexpectedErrorReferenceCode, message));
		return responseBuilder.build();
	}

	/**
	 * Returns a validation error message with one message
	 *
	 * @param fieldName    the field name
	 * @param errorMessage the error message
	 * @return response response
	 */
	public static Response validationError(String fieldName, String errorMessage) {
		Response.ResponseBuilder responseBuilder = Response.status(422);
		HashMap<String, String> validationErrors = new HashMap<>();
		validationErrors.put(fieldName, errorMessage);
		responseBuilder.entity(new ValidationErrorResponse(InterfaceConstants.RETURN_CODE_422_MESSAGE,
				ErrorCodes.INVALID_DATA.value(), validationErrors));
		return responseBuilder.build();
	}

	/**
	 * Returns a validation error message with one message
	 *
	 * @param fieldName    the field name
	 * @param customCode   the custom code
	 * @param errorMessage the error message
	 * @return response response
	 */
	public static Response validationError(String fieldName, String customCode, String errorMessage) {
		Response.ResponseBuilder responseBuilder = Response.status(422);
		HashMap<String, String> validationErrors = new HashMap<>();
		validationErrors.put(fieldName, errorMessage);
		ValidationErrorResponse error = new ValidationErrorResponse(InterfaceConstants.RETURN_CODE_422_MESSAGE,
				ErrorCodes.INVALID_DATA.value(), validationErrors);
		error.setCode(customCode);
		responseBuilder.entity(error);
		return responseBuilder.build();
	}

	/**
	 * Returns a validation error message with a map of fields / errors
	 *
	 * @param validationErrors the validation errors
	 * @return response response
	 */
	public static Response validationError(HashMap<String, String> validationErrors) {
		Response.ResponseBuilder responseBuilder = Response.status(422);
		responseBuilder.entity(new ValidationErrorResponse(InterfaceConstants.RETURN_CODE_422_MESSAGE,
				ErrorCodes.INVALID_DATA.value(), validationErrors));
		return responseBuilder.build();
	}

	/**
	 * Resource not found error response.
	 *
	 * @param notFoundErrors the not found errors
	 * @return the response
	 */
	public static Response resourceNotFoundError(HashMap<String, String> notFoundErrors) {
		Response.ResponseBuilder responseBuilder = Response.status(410);
		responseBuilder.entity(new ResourceNotFoundResponse(InterfaceConstants.RETURN_CODE_410_MESSAGE,
				notFoundErrors));
		return responseBuilder.build();
	}

	/**
	 * Success response.
	 *
	 * @param entity the entity
	 * @return the response
	 */
	public static Response success(Object entity) {
		Response.ResponseBuilder responseBuilder = Response.status(200);
		responseBuilder.entity(entity);
		return responseBuilder.build();
	}

	/**
	 * Success response.
	 *
	 * @return the response
	 */
	public static Response success() {
		Response.ResponseBuilder responseBuilder = Response.status(200);

		return responseBuilder.build();
	}

	/**
	 * Post success response.
	 *
	 * @param entity the entity
	 * @return the response
	 */
	public static Response postSuccess(Object entity) {
		return success(entity);
	}

	private static Response buildErrorResponse(int errorCode, String errorMessage) {
		Response.ResponseBuilder responseBuilder = Response.status(errorCode);
		responseBuilder.entity(new ErrorResponse(errorMessage));
		return responseBuilder.build();
	}

	private static Response buildErrorResponse(int errorCode, String errorResponseCode, String errorMessage) {
		Response.ResponseBuilder responseBuilder = Response.status(errorCode);
		responseBuilder.entity(new ErrorResponse(errorMessage, errorResponseCode));
		return responseBuilder.build();
	}
}