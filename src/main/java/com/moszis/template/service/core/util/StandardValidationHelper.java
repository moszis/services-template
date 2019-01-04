package com.moszis.template.service.core.util;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

/**
 * Helper class to perform standard validations and assist with keeping
 * the list of validation errors
 */
public class StandardValidationHelper {

	private static Log log = LogFactory.getLog(StandardValidationHelper.class);

	private String errorResponseCode;

	/**
	 * Map of validation errors. Contains the field/param that was in error as
	 * the key and the reason for the validation error.
	 */
	private HashMap<String, String> warnings = new HashMap<>();

	/**
	 * Map of validation errors. Contains the field/param that was in error as
	 * the key and the reason for the validation error.
	 */
	private HashMap<String, String> validationErrors = new HashMap<>();

	/**
	 * Map of validation errors. Contains the field/param that was in error as
	 * the key and the reason for the validation error.
	 */
	private HashMap<String, String> securityErrors = new HashMap<>();

	private HashMap<String, String> notFoundErrors = new HashMap<>();

	/**
	 * Is string one of boolean.
	 *
	 * @param key       the key
	 * @param fieldName the field name
	 * @param args      the args
	 * @return the boolean
	 */
	public boolean isStringOneOf(String key, String fieldName, String... args) {
		List<String> values = Arrays.asList(args);
		if (values.contains(key)) {
			return true;
		}
		String joined = String.join(",", values);
		validationErrors.put(fieldName, "is not one of [" + joined + "]");
		return false;
	}


	public boolean stringMustBeOneOf(String fieldName, String target, String... values) {

		if(!objectNotNull(fieldName,target)){
			return false;
		}
		for(String value: values){
			if(value.equals(target)){
				return true;
			}
		}
		validationErrors.put(fieldName, "Must be one of these values:" + String.join(",",values));
		return false;

	}


	/**
	 * Adds an appropriate error message to the map of validation errors if
	 * there is an issue.
	 *
	 * @param fieldName       the field name
	 * @param valueToValidate the value to validate
	 * @param charsCount      the chars count
	 * @return false if the string is short enough
	 */
	public boolean exceedsMaximumChars(String fieldName, String valueToValidate, int charsCount) {

		if (valueToValidate != null && valueToValidate.length() > charsCount) {
			validationErrors.put(fieldName, "Exceeds max length.  value='" + valueToValidate + "' max=" + charsCount);
			return true;
		}

		return false;
	}

	/**
	 * Length must equal boolean.
	 *
	 * @param fieldName       the field name
	 * @param valueToValidate the value to validate
	 * @param charsCount      the chars count
	 * @return the boolean
	 */
	public boolean lengthMustEqual(String fieldName, String valueToValidate, int charsCount) {
		boolean error = true;
		for (String s : valueToValidate.split(",")) {

			if (s.length() != charsCount) {
				validationErrors.put(fieldName, "Must Equal.  value='" + valueToValidate + "' length=" + charsCount);
				error = false;
			}

		}
		return error;
	}

	/**
	 * Is alpha boolean.
	 *
	 * @param fieldName        the field name
	 * @param stringToValidate the string to validate
	 * @return the boolean
	 */
	public boolean isAlpha(String fieldName, String stringToValidate) {
		if (!stringToValidate.matches("[a-zA-Z]+")) {
			validationErrors.put(fieldName, "Contains non alphabetic characters [" + stringToValidate + "]");
			return false;
		}
		return true;
	}

	/**
	 * Is alpha numeric boolean.
	 *
	 * @param fieldName        the field name
	 * @param stringToValidate the string to validate
	 * @return the boolean
	 */
	public boolean isAlphaNumeric(String fieldName, String stringToValidate) {
		if (!stringToValidate.matches("[a-zA-Z0-9]+")) {
			validationErrors.put(fieldName, "Contains non alphanumeric characters [" + stringToValidate + "]");
			return false;
		}
		return true;
	}

	/**
	 * Is numeric boolean.
	 *
	 * @param fieldName        the field name
	 * @param stringToValidate the string to validate
	 * @return the boolean
	 */
	public boolean isNumeric(String fieldName, String stringToValidate) {

		String[] values = stringToValidate.split(",");
		for (String value : values) {
			if (!value.matches("[0-9]+")) {
				validationErrors.put(fieldName, "Contains non numberic characters [" + stringToValidate + "]");
				return false;
			}
		}
		return true;
	}

	/**
	 * Is double boolean.
	 *
	 * @param fieldName        the field name
	 * @param stringToValidate the string to validate
	 * @return the boolean
	 */
	public boolean isDouble(String fieldName, String stringToValidate) {
		String[] values = stringToValidate.split(",");
		for (String value : values) {
			if (!value.matches("[+-]?([0-9]*[.])?[0-9]+")) {
				validationErrors.put(fieldName, "Contains non numberic characters [" + stringToValidate + "]");
				return false;
			}
		}
		return true;
	}

	/**
	 * Is date before boolean.
	 *
	 * @param startDateField the start date field
	 * @param endDateField   the end date field
	 * @param startDate      the start date
	 * @param endDate        the end date
	 * @return true id startdate before enddate
	 */
	public boolean isDateBefore(String startDateField, String endDateField, Date startDate, Date endDate) {
		if (endDate.before(startDate)) {
			validationErrors.put(endDateField, "Occurs before " + endDateField);
			return false;
		}
		return true;
	}

	/**
	 * Is date boolean.
	 *
	 * @param fieldName the field name
	 * @param date      the date
	 * @param format    the format
	 * @return the boolean
	 */
	public boolean isDate(String fieldName, String date, String format) {
		SimpleDateFormat formatter = new SimpleDateFormat(format);
		try {
			formatter.parse(date);

			return true;
		} catch (ParseException e) {
			validationErrors.put(fieldName, date + " is not a date with this format [" + format + " ] ");
			log.error(e.getMessage());
			return false;
		}
	}

	/**
	 * Validate and convert date date.
	 *
	 * @param fieldName the field name
	 * @param date      the date
	 * @param format    the format
	 * @return the date
	 */
	public Date validateAndConvertDate(String fieldName, String date, String format) {
		SimpleDateFormat formatter = new SimpleDateFormat(format);
		try {

			return formatter.parse(date);

		} catch (ParseException e) {
			validationErrors.put(fieldName, date + " is not a date with this format [" + format + " ] ");
			log.error(e.getMessage());
			return null;
		}
	}

	/**
	 * Adds an appropriate error message to the map of validation errors if
	 * there is an issue.
	 *
	 * @param fieldName       Field name for error message
	 * @param valueToValidate Value to check
	 * @return true if the value to validate is a valid UUID
	 */
	public boolean isNotEmpty(String fieldName, String valueToValidate) {

		if (valueToValidate == null || StringUtils.isEmpty(valueToValidate.trim())) {
			validationErrors.put(fieldName, "This is a required field.");
			return false;
		} else {
			return true;
		}
	}

	/**
	 * Return true if the data is null. Returns false otherwise
	 *
	 * @param fieldName       Field name for error message
	 * @param valueToValidate Value to check
	 * @return True if the value is null or an empty string and add an error to the validation error list, otherwise         return false
	 */
	public boolean shouldBeNull(String fieldName, Object valueToValidate) {
		if (valueToValidate != null) {
			validationErrors.put(fieldName, "This field should not be passed for this request.");
			return false;
		} else {
			return true;
		}
	}

	/**
	 * Is empty boolean.
	 *
	 * @param fieldName       the field name
	 * @param valueToValidate the value to validate
	 * @return the boolean
	 */
	public boolean isEmpty(String fieldName, String valueToValidate) {
		return !isNotEmpty(fieldName, valueToValidate);
	}

	/**
	 * Is true boolean.
	 *
	 * @param fieldName       the field name
	 * @param valueToValidate the value to validate
	 * @return the boolean
	 */
	public boolean isTrue(String fieldName, boolean valueToValidate) {

		return isTrue(fieldName, valueToValidate, "This value must be true.");

	}

	/**
	 * Is true boolean.
	 *
	 * @param fieldName       the field name
	 * @param valueToValidate the value to validate
	 * @param customMessage   the custom message
	 * @return the boolean
	 */
	public boolean isTrue(String fieldName, boolean valueToValidate, String customMessage) {

		if (!valueToValidate) {
			validationErrors.put(fieldName, customMessage);
		}
		return valueToValidate;
	}

	/**
	 * Adds an appropriate error message to the map of validation errors if
	 * there is an issue.
	 *
	 * @param fieldName        the field name
	 * @param objectToEvaluate the object to evaluate
	 * @return true if the object has value
	 */
	public boolean objectNotNull(String fieldName, Object objectToEvaluate) {

		return objectNotNull(fieldName, objectToEvaluate, null);
	}

	/**
	 * Adds an appropriate error message to the map of validation errors if
	 * there is an issue.
	 *
	 * @param fieldName        the field name
	 * @param objectToEvaluate the object to evaluate
	 * @param customText       the custom text
	 * @return true if the object has value
	 */
	public boolean objectNotNull(String fieldName, Object objectToEvaluate, String customText) {

		if (objectToEvaluate == null) {
			if (customText == null) {
				validationErrors.put(fieldName, "This field cannot be null.");
			} else {
				validationErrors.put(fieldName, customText);
			}
			return false;
		} else {
			return true;
		}
	}

	/**
	 * Adds an appropriate error message to the map of validation errors if
	 * there is an issue.
	 *
	 * @param fieldName       the field name
	 * @param valueToValidate the value to validate
	 * @return true if the value to validate is a valid UUID will test if there is more than 1
	 */
	public boolean isValidUUID(String fieldName, String valueToValidate) {

		boolean error = true;
		if (valueToValidate.contains(",")) {
			String[] values = valueToValidate.split(",");
			for (String value : values) {
				try {
					StringUtil.stringToUUID(value);
				} catch (Exception e) {
					validationErrors.put(fieldName, "Not a valid UUID.  value='" + valueToValidate + "'");
					error = false;
				}
			}
			return error;
		} else {
			try {
				StringUtil.stringToUUID(valueToValidate);
			} catch (Exception e) {
				validationErrors.put(fieldName, "Not a valid UUID.  value='" + valueToValidate + "'");
				return false;
			}
		}
		StringUtil.stringToUUID(valueToValidate);
		return true;

	}

	/**
	 * Adds an appropriate error message to the map of validation errors if
	 * there is an issue.
	 *
	 * @param fieldName             the field name
	 * @param valueToValidate       the value to validate
	 * @param departmentsToFilterOn the departments to filter on
	 * @return true if the value to validate is a valid UUID will test if there is more than 1
	 */
	public boolean isValidDeptUUID(String fieldName, String valueToValidate, Set<UUID> departmentsToFilterOn) {

		boolean error = true;
		if (valueToValidate.contains(",")) {
			String[] values = valueToValidate.split(",");
			for (String value : values) {
				try {
					UUID val = StringUtil.stringToUUID(value);
					if (!departmentsToFilterOn.contains(val)) {
						addSecurityError("dept", "No access to dept [" + value + "]");
					}
				} catch (Exception e) {
					validationErrors.put(fieldName, "Not a valid UUID.  value='" + valueToValidate + "'");
					error = false;
				}
			}
			return error;
		} else {
			try {
				UUID val = StringUtil.stringToUUID(valueToValidate);
				if (!departmentsToFilterOn.contains(val)) {
					addSecurityError("dept", "No access to dept [" + valueToValidate + "]");
				}
			} catch (Exception e) {
				validationErrors.put(fieldName, "Not a valid UUID.  value='" + valueToValidate + "'");
				return false;
			}
		}
		StringUtil.stringToUUID(valueToValidate);
		return true;

	}

	/**
	 * Is one of boolean.
	 *
	 * @param fieldName the field name
	 * @param value     the value
	 * @param values    to check
	 * @return true if value is boolean
	 */
	public boolean isOneOf(String fieldName, String value, String... values) {

		for (String valueToCheck : values) {
			if (value.equals(valueToCheck))
				return true;
		}

		validationErrors.put(fieldName, "Not one of.  values=['" + String.join(",", values) + "]");
		return false;
	}

	/**
	 * All are one of boolean.
	 *
	 * @param fieldName the field name
	 * @param value     the value
	 * @param values    to check
	 * @return true if value is boolean
	 */
	public boolean allAreOneOf(String fieldName, String value, String... values) {

		String[] valuesToCheck = value.split(",");
		boolean good = true;
		HashSet<String> goodValues = new HashSet<>(Arrays.asList(values));

		for (String valueToCheck : valuesToCheck) {

			if (!goodValues.contains(valueToCheck)) {
				validationErrors.put(fieldName, valueToCheck + " not one of.  values=['" + String.join(",", values) + "]");
				good = false;
			}
		}

		return good;
	}

	/**
	 * Is valid boolean boolean.
	 *
	 * @param fieldName       the field name
	 * @param valueToValidate the value to validate
	 * @return true if value is boolean
	 */
	public boolean isValidBoolean(String fieldName, String valueToValidate) {

		if (valueToValidate.equals("true") || valueToValidate.equals("false")) {
			return true;
		}
		validationErrors.put(fieldName, "Not a valid Boolean.  value='" + valueToValidate + "'");
		return false;
	}

	/**
	 * Adds an appropriate error message to the map of validation errors if
	 * there is an issue.
	 *
	 * @param fieldName       the field name
	 * @param valueToValidate the value to validate
	 * @return UUID object if the value is a valid UUID. Null otherwise
	 */
	public UUID validateAndConvertUUID(String fieldName, String valueToValidate) {
		try {
			return StringUtil.stringToUUID(valueToValidate);
		} catch (Exception e) {
			validationErrors.put(fieldName, "Not a valid UUID.  value='" + valueToValidate + "'");
			return null;
		}
	}

	/**
	 * Validate and convert integer integer.
	 *
	 * @param fieldName       the field name
	 * @param valueToValidate the value to validate
	 * @return the integer
	 */
	public Integer validateAndConvertInteger(String fieldName, String valueToValidate) {
		try {
			return Integer.parseInt(valueToValidate);
		} catch (Exception e) {
			validationErrors.put(fieldName, "Not a valid Integer.  value='" + valueToValidate + "'");
			return null;
		}
	}

	/**
	 * Validate and convert long long.
	 *
	 * @param fieldName       the field name
	 * @param valueToValidate the value to validate
	 * @return the long
	 */
	public Long validateAndConvertLong(String fieldName, String valueToValidate) {
		try {
			return Long.parseLong(valueToValidate);
		} catch (Exception e) {
			validationErrors.put(fieldName, "Not a valid Long.  value='" + valueToValidate + "'");
			return null;
		}
	}

	/**
	 * Is integer boolean.
	 *
	 * @param fieldName       the field name
	 * @param valueToValidate the value to validate
	 * @return the boolean
	 */
	public boolean isInteger(String fieldName, String valueToValidate) {
		try {
			Integer.parseInt(valueToValidate);
		} catch (Exception e) {
			validationErrors.put(fieldName, "Not a valid Integer.  value='" + valueToValidate + "'");
			return false;
		}
		return true;
	}

	/**
	 * Is long boolean.
	 *
	 * @param fieldName       the field name
	 * @param valueToValidate the value to validate
	 * @return the boolean
	 */
	public boolean isLong(String fieldName, String valueToValidate) {
		try {
			Long.parseLong(valueToValidate);
		} catch (Exception e) {
			validationErrors.put(fieldName, "Not a valid Long.  value='" + valueToValidate + "'");
			return false;
		}
		return true;
	}

	/**
	 * Adds appropriate error message to the map of validation errors if there
	 * is an issue.
	 *
	 * @param fieldName       the field name
	 * @param valueToValidate the value to validate
	 * @return UUID object if the value is a valid UUID. Null otherwise
	 */
	public Set<UUID> validateAndConvertUUIDList(String fieldName, String valueToValidate) {
		HashSet<UUID> uuidSet = new HashSet<>();
		List<String> uuidStringList = Arrays.asList(valueToValidate.split(","));

		for (String uuidString : uuidStringList) {
			try {
				UUID uuid = StringUtil.stringToUUID(uuidString);
				uuidSet.add(uuid);
			} catch (Exception e) {
				validationErrors.put(fieldName, "Not a valid UUID.  value='" + valueToValidate + "'");
				return null;
			}
		}
		return uuidSet;
	}

	/**
	 * Validate and convert enum e.
	 *
	 * @param <E>       the type parameter
	 * @param filedName the filed name
	 * @param e         the e
	 * @param id        the id
	 * @return the e
	 * @throws IllegalArgumentException the illegal argument exception
	 */
	public <E extends Enum<E>> E validateAndConvertEnum(String filedName, Class<E> e, String id) throws IllegalArgumentException {
		E result = null;
		try {
			result = Enum.valueOf(e, id);
		} catch (IllegalArgumentException exception) {
			log.error("Unable to convert " + filedName + " to enum " + e.getSimpleName() + " with with a vlaue of " + id);
			validationErrors.put(filedName, "Unable to convert " + filedName + " to enum " + e.getSimpleName() + " with with a vlaue of " + id);
		}

		return result;
	}

	/**
	 * Add validation error.
	 *
	 * @param key   the key
	 * @param value the value
	 */
	public void addValidationError(String key, String value) {
		synchronized (this) {
			validationErrors.put(key, value);
		}
	}

	/**
	 * Add not found error.
	 *
	 * @param id      the id
	 * @param message the message
	 */
	public void addNotFoundError(String id, String message) {
		notFoundErrors.put(id, message);
	}

	/**
	 * Add validation errors.
	 *
	 * @param validationErrors the validation errors
	 */
	public void addValidationErrors(HashMap<String, String> validationErrors) {
		this.validationErrors.putAll(validationErrors);
	}

	/**
	 * Add security error.
	 *
	 * @param key   the key
	 * @param value the value
	 */
	public void addSecurityError(String key, String value) {
		securityErrors.put(key, value);
	}

	/**
	 * Add security errors.
	 *
	 * @param validationErrors the validation errors
	 */
	public void addSecurityErrors(HashMap<String, String> validationErrors) {
		securityErrors.putAll(validationErrors);
	}

	/**
	 * Add warning.
	 *
	 * @param key   the key
	 * @param value the value
	 */
	public void addWarning(String key, String value) {
		warnings.put(key, value);
	}

	/**
	 * Add warnings.
	 *
	 * @param validationErrors the validation errors
	 */
	public void addWarnings(HashMap<String, String> validationErrors) {
		warnings.putAll(validationErrors);
	}

	/**
	 * Gets validation errors.
	 *
	 * @return the validation errors
	 */
	public HashMap<String, String> getValidationErrors() {
		return validationErrors;
	}

	/**
	 * Sets validation errors.
	 *
	 * @param validationErrors the validation errors
	 */
	public void setValidationErrors(HashMap<String, String> validationErrors) {
		this.validationErrors = validationErrors;
	}

	/**
	 * Gets not found errors.
	 *
	 * @return the not found errors
	 */
	public HashMap<String, String> getNotFoundErrors() {
		return notFoundErrors;
	}

	/**
	 * Gets warnings.
	 *
	 * @return the warnings
	 */
	public HashMap<String, String> getWarnings() {
		return warnings;
	}

	/**
	 * Gets errors.
	 *
	 * @return the errors
	 */
	public HashMap<String, String> getErrors() {
		HashMap<String, String> allErrors = new HashMap<>();
		allErrors.putAll(validationErrors);
		allErrors.putAll(securityErrors);
		allErrors.putAll(notFoundErrors);

		return allErrors;
	}

	/**
	 * Gets error response code.
	 *
	 * @return the error response code
	 */
	public String getErrorResponseCode() {
		return errorResponseCode;
	}

	/**
	 * Sets error response code.
	 *
	 * @param errorResponseCode the error response code
	 */
	public void setErrorResponseCode(String errorResponseCode) {
		this.errorResponseCode = errorResponseCode;
	}

	/**
	 * Sets security erros.
	 *
	 * @return the security erros
	 */
	public HashMap<String, String> setSecurityErros() {
		return securityErrors;
	}

	/**
	 * Has warnings boolean.
	 *
	 * @return the boolean
	 */
	public boolean hasWarnings() {
		return this.warnings.size() > 0;
	}

	/**
	 * Has validation errors boolean.
	 *
	 * @return the boolean
	 */
	public boolean hasValidationErrors() {
		return this.validationErrors.size() > 0;
	}

	/**
	 * Has security errors boolean.
	 *
	 * @return the boolean
	 */
	public boolean hasSecurityErrors() {
		return this.securityErrors.size() > 0;
	}

	/**
	 * Has errors boolean.
	 *
	 * @return the boolean
	 */
	public boolean hasErrors() {
		return (errorResponseCode != null || securityErrors.size() > 0 || validationErrors.size() > 0 || notFoundErrors.size() > 0);
	}

	/**
	 * Gets security errors.
	 *
	 * @return the security errors
	 */
	public HashMap<String, String> getSecurityErrors() {
		return securityErrors;
	}

	/**
	 * Sets security errors.
	 *
	 * @param securityErrors the security errors
	 */
	public void setSecurityErrors(HashMap<String, String> securityErrors) {
		this.securityErrors = securityErrors;
	}

	/**
	 * Has not found error boolean.
	 *
	 * @return the boolean
	 */
	public boolean hasNotFoundError() {
		return notFoundErrors.size() > 0;
	}

}
