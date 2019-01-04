package com.moszis.template.service.core.util;

import com.moszis.template.service.core.security.AccessToken;

public class LocalContext {
	/**
	 * The constant PLAYBOOK_SYSTEM_USER_NAME.
	 */
	public static final String PLAYBOOK_SYSTEM_USER_NAME = "Playbook Services";

	private static final ThreadLocal<AccessToken> authContext = new ThreadLocal<>();
	private static final ThreadLocal<WebParamsQuery> webParamsQueryContext = new ThreadLocal<>();
	private static final ThreadLocal<StandardValidationHelper> validationContext = new ThreadLocal<>();

	/**
	 * Gets auth context.
	 *
	 * @return the auth context
	 */
	public static AccessToken getAuthContext() {
		if (authContext.get() == null) {
			AccessToken token = new AccessToken();
			token.setUsername(PLAYBOOK_SYSTEM_USER_NAME);

			authContext.set(token);
		}

		return authContext.get();
	}

	/**
	 * Sets auth context.
	 *
	 * @param token the token
	 */
	public static void setAuthContext(AccessToken token) {
		authContext.set(token);
	}

	/**
	 * Sets web params query context.
	 *
	 * @param params the params
	 */
	public static void setWebParamsQueryContext(WebParamsQuery params) {

		webParamsQueryContext.set(params);
	}

	/**
	 * Gets web parameters context.
	 *
	 * @return the web parameters context
	 */
	public static WebParamsQuery getWebParametersContext() {
		if (webParamsQueryContext.get() == null) {
			webParamsQueryContext.set(new WebParamsQuery());
		}

		return webParamsQueryContext.get();
	}

	/**
	 * Gets validation context.
	 *
	 * @return the validation context
	 */
	public static StandardValidationHelper getValidationContext() {
		if (validationContext.get() == null) {
			validationContext.set(new StandardValidationHelper());
		}

		return validationContext.get();
	}

	/**
	 * Sets validation context.
	 *
	 * @param helper the helper
	 */
	public static void setValidationContext(StandardValidationHelper helper) {
		validationContext.set(helper);
	}
}
