package com.moszis.template.service.core.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import com.moszis.template.service.core.constants.AppConstants;

/**
 * Object to hold parsed URI variables
 */
public class WebParamsQuery {

	private List<String> validParams = new ArrayList<>();
	private List<String> validSelectors = new ArrayList<>();
	private String sortField;
	private boolean sortAsc = true;

	private HashMap<String, Parameter> parameters = new HashMap<>();
	private HashMap<String, Parameter> selectors = new HashMap<>();

	private boolean isEmpty = true;

	private HashMap<String, String> errors = new HashMap<>();

	/**
	 * Constructor that will process valid types and the list to parse
	 *
	 * @param filterQueries The parameters we are searching on
	 * @param params        the array of valid parameters
	 */
	public WebParamsQuery(List<String> filterQueries, String... params) {
		Collections.addAll(validParams, params);
		if (filterQueries == null || filterQueries.isEmpty()) {
			this.isEmpty = true;
		} else {
			parse(filterQueries);
			isEmpty = false;
		}

	}

	/**
	 * Constructor with no valid parameters
	 *
	 * @param filterQueries list of fq's to parse
	 */
	public WebParamsQuery(List<String> filterQueries) {
		if (filterQueries == null || filterQueries.isEmpty()) {
			this.isEmpty = true;
		} else {
			parse(filterQueries);
			isEmpty = false;
		}

	}

	/**
	 * Default constructor
	 */
	public WebParamsQuery() {
		super();
	}

	/**
	 * This method will set valid selector fields
	 *
	 * @param selectors the selectors
	 */
	public void setValidSelectors(String... selectors) {
		validSelectors.clear();
		validSelectors = Arrays.asList(selectors);
	}

	/**
	 * This method will take a comma delimited list and set the selector fields we want
	 * It will also parse out any selector criteria it finds and set them
	 * in the selector criteria map
	 *
	 * @param fieldsToParse the fields to parse
	 */
	public void setSelectorFields(String fieldsToParse) {
		if (fieldsToParse == null) {
			return;
		}
		selectors.clear();
		List<String> fields = Arrays.asList(fieldsToParse.split(","));
		parse(fields, selectors, validSelectors, true);

	}

	/**
	 * This method will take a comma delimited list and set the selector fields we want
	 * It will also parse out any selector criteria it finds and set them
	 * in the sort map
	 *
	 * @param fieldsToParse the fields to parse
	 */
	public void parseSortParameter(String fieldsToParse) {
		String[] sortCriteria = fieldsToParse.split(";");
		int position = 0;
		for (String sort : sortCriteria) {
			if (position == 0) {
				sortField = sort;
				position += 1;
				continue;
			}
			String[] sortParse = sort.split(":");
			if (sortParse[0].equals("direction")) {
				if (sortParse[1].equals("asc")) {
					sortAsc = true;
				} else {
					sortAsc = false;
				}
			}
			position += 1;

		}

	}

	/**
	 * Returns a boolean if the key passed is a valid selector
	 *
	 * @param key the name of teh selector
	 * @return boolean boolean
	 */
	public boolean selectorsContains(String key) {
		return selectors.containsKey(key);
	}

	/**
	 * Gets selector fields.
	 *
	 * @return the selector fields
	 */
	public List<String> getSelectorFields() {
		List<String> array = new ArrayList<>();
		array.addAll(selectors.keySet());
		return array;
	}

	/**
	 * Sets selector fields.
	 *
	 * @param selectorFields the selector fields
	 */
	public void setSelectorFields(List<String> selectorFields) {
		selectors.clear();
		parse(selectorFields, selectors, validSelectors, true);
	}

	/**
	 * Boolean that sets if we have any valid fq's to search
	 *
	 * @return boolean boolean
	 */
	public boolean isEmpty() {
		return isEmpty;
	}

	/**
	 * Sets is empty.
	 *
	 * @param isEmpty the is empty
	 */
	public void setIsEmpty(boolean isEmpty) {
		this.isEmpty = isEmpty;
	}

	/**
	 * The primary parse method
	 *
	 * @param filterQueries the list of parameters
	 */
	public void parse(List<String> filterQueries) {
		parse(filterQueries, parameters, validParams, false);
	}

	/**
	 * Add a single valued parameter.
	 *
	 * @param key   Parameter name
	 * @param value Parameter single value
	 */
	public void addParameter(String key, String value) {
		Parameter parameter = new Parameter();
		parameter.setName(key);
		List<String> values = new ArrayList<>();
		values.add(value);
		parameter.setValues(values);
		parameters.put(parameter.getName(), parameter);
	}

	/**
	 * The primary parse method
	 *
	 * @param filterQueries the list of parameters
	 * @param target        the target
	 * @param validFields   the valid fields
	 * @param allowNulls    the allow nulls
	 */
	public void parse(List<String> filterQueries, Map<String, Parameter> target, List<String> validFields, boolean allowNulls) {
		//begin to parse parameters
		for (String s : filterQueries) {
			try {
				Parameter parameter = new Parameter();
				//We have criteria
				if (s.contains(";")) {
					//First get primary value
					int parmEnd = s.indexOf(';');
					String parm = s.substring(0, parmEnd);

					if (parm.contains(":")) {
						int sep = parm.indexOf(':');
						String parmName = parm.substring(0, sep);

						String parmValues = parm.substring(sep + 1, parm.length());
						parameter.setValues(parseValues(parmValues));

						parameter.setName(parmName);
						if (validFields.contains(parmName)) {
							parameter.setName(parmName);
							parameter.setValues(parseValues(parmValues));
						} else {
							errors.put(parmName, "was unexpected.");
						}
					} else {
						if (!allowNulls) {
							errors.put(s, "Must not have a null value and follow this cvonvetion [NAME]:[VALUE];[CRITERIA]");
						}
						parameter.setName(parm);
					}

					//Now get all criteria
					String[] crits = s.substring(parmEnd + 1).split(";");
					for (String crit : crits) {
						FilterCriteria fc = new FilterCriteria();
						if (crit.contains(":")) {
							int index = crit.indexOf(':');
							String name = crit.substring(0, index);
							String values = crit.substring(index + 1, crit.length());

							if (validParams.size() == 0 || validFields.contains(name)) {
								fc.setName(name);
								fc.setValues(parseValues(values));
								parameter.addCriteria(fc);
							} else {
								errors.put(s, "criteria was unexpected.");
							}
						} else {
							String name = crit;
							if (validParams.size() == 0 || validFields.contains(name)) {
								fc.setName(name);
								fc.setValues(new ArrayList<>());
								parameter.addCriteria(fc);
							} else {
								errors.put(s, "criteria was unexpected.");
							}
						}
					}

				} else {
					//we have no criteria
					if (s.contains(":")) {
						int sep = s.indexOf(':');
						String parmName = s.substring(0, sep);
						String parmValues = s.substring(sep + 1, s.length());

						if (validFields.contains(parmName)) {
							parameter.setName(parmName);
							parameter.setValues(parseValues(parmValues));
						} else {
							errors.put(parmName, "was unexpected.");
						}

					} else {
						if (validFields.contains(s)) {
							parameter.setName(s);
						} else {
							errors.put(s, "was unexpected.");
						}
					}

				}
				if (validParams.size() == 0 || validFields.contains(parameter.getName())) {
					target.put(parameter.getName(), parameter);
				} else {
					errors.put(s, "was unexpected.");
				}
			} catch (Exception e) {

				errors.put(s, e.getMessage());
			}

		}

	}

	/**
	 * Gets error text.
	 *
	 * @return the error text
	 */
	public String getErrorText() {
		StringBuilder sb = new StringBuilder();
		sb.append("These parameters could not be parsed: \r\n");
		Iterator it = errors.entrySet().iterator();
		while (it.hasNext()) {
			Map.Entry pair = (Map.Entry) it.next();
			sb.append(pair.getKey() + " [" + pair.getValue() + "]");

		}
		return sb.toString();
	}

	/**
	 * Gets sort field.
	 *
	 * @return the sort field
	 */
	public String getSortField() {
		return sortField;
	}

	/**
	 * Sets sort field.
	 *
	 * @param sortField the sort field
	 */
	public void setSortField(String sortField) {
		this.sortField = sortField;
	}

	/**
	 * Is sort asc boolean.
	 *
	 * @return the boolean
	 */
	public boolean isSortAsc() {
		return sortAsc;
	}

	/**
	 * Sets sort asc.
	 *
	 * @param sortAsc the sort asc
	 */
	public void setSortAsc(boolean sortAsc) {
		this.sortAsc = sortAsc;
	}

	/**
	 * Parm has multiple values boolean.
	 *
	 * @param key the key
	 * @return the boolean
	 */
	public boolean parmHasMultipleValues(String key) {
		Parameter parameter = parameters.get(key);
		return parameter.hasMultipleValues();
	}

	/**
	 * Parm contains criteria boolean.
	 *
	 * @param key         the key
	 * @param criteriaKey the criteria key
	 * @return the boolean
	 */
	public boolean parmContainsCriteria(String key, String criteriaKey) {
		return containsCriteria(key, criteriaKey, parameters);
	}

	/**
	 * Selector contains criteria boolean.
	 *
	 * @param key         the key
	 * @param criteriaKey the criteria key
	 * @return the boolean
	 */
	public boolean selectorContainsCriteria(String key, String criteriaKey) {
		return containsCriteria(key, criteriaKey, selectors);
	}

	/**
	 * Contains criteria boolean.
	 *
	 * @param key         the key
	 * @param criteriaKey the criteria key
	 * @param target      the target
	 * @return the boolean
	 */
	public boolean containsCriteria(String key, String criteriaKey, HashMap<String, Parameter> target) {
		if (target.containsKey(key)) {
			Parameter parameter = target.get(key);
			return parameter.containsCriteria(criteriaKey);
		}
		return false;
	}

	/**
	 * Parm get criteria value string.
	 *
	 * @param key         the key
	 * @param criteriaKey the criteria key
	 * @return the string
	 */
	//fix nullpointer error when there is no value entered for the criteriakey
	public String parmGetCriteriaValue(String key, String criteriaKey) {
		if (parameters.get(key).getCriteria(criteriaKey) == null) {
			return null;
		}
		return parameters.get(key).getCriteria(criteriaKey).toString();
	}

	/**
	 * Gets selector criteria value.
	 *
	 * @param key         the key
	 * @param criteriaKey the criteria key
	 * @return the selector criteria value
	 */
	public String getSelectorCriteriaValue(String key, String criteriaKey) {
		if (selectorContainsCriteria(key, criteriaKey)) {
			Parameter p = selectors.get(key);
			return p.getCriteria(criteriaKey).toString();
		} else {
			return null;
		}
	}

	//	/**
	//	 * Gets selector criteria value as date.
	//	 *
	//	 * @param key         the key
	//	 * @param criteriaKey the criteria key
	//	 * @return Date object representation of the critieria value for selector.
	//	 */
	//	public Date getSelectorCriteriaValueAsDate(String key, String criteriaKey) {
	//
	//		if (selectorContainsCriteria(key, criteriaKey)) {
	//			Parameter p = selectors.get(key);
	//			return StringUtil.stringToDate(p.getCriteria(criteriaKey).toString(), ValidFormats.JSON_UTC_DATE);
	//		} else {
	//			return null;
	//		}
	//
	//	}

	/**
	 * Get string.
	 *
	 * @param key the key
	 * @return the string
	 */
	public String get(String key) {
		Parameter param = parameters.get(key);
		if (param == null) {
			return null;
		}
		return param.toString();
	}

	/**
	 * Gets boolean.
	 *
	 * @param key the key
	 * @return the boolean
	 */
	public Boolean getBoolean(String key) {
		Parameter param = parameters.get(key);
		if (param == null) {
			return null;
		}
		String value = param.toString();

		return ("true".equals(value) || "false".equals(value)) ? Boolean.valueOf(value) : null;
	}

	/**
	 * Gets uuid.
	 *
	 * @param key the key
	 * @return the uuid
	 */
	public UUID getUUID(String key) {
		Parameter param = parameters.get(key);
		if (param == null) {
			return null;
		}
		return StringUtil.stringToUUID(param.toString());
	}

	/**
	 * Contains key boolean.
	 *
	 * @param key the key
	 * @return the boolean
	 */
	public boolean containsKey(String key) {
		return parameters.containsKey(key);
	}

	private List<String> parseValues(String values) {
		ArrayList<String> outList = new ArrayList<>();
		if (values.contains("[")) {

			String working = values;
			//We Have protected values

			while (working.length() > 0) {
				int posFirstCom = working.indexOf(",");
				int posFirstWrap = working.indexOf("[");
				//Check if we have a comma before wrapper
				if (posFirstCom > -1 && posFirstCom < posFirstWrap) {
					String current = working.substring(0, posFirstCom);
					outList.add(current);
					working = working.substring(posFirstCom + 1, working.length());
				} else {
					int posOfEnd = working.indexOf("]");

					String current = working.substring(0, posOfEnd).replace("[", "").replace("]", "");
					outList.add(current);
					if (posOfEnd + 2 > working.length()) {
						working = "";
					} else {
						working = working.substring(posOfEnd + 2, working.length());
					}
				}
			}
		} else {
			//do not split and add
			String[] stringValues = values.split(",");
			for (String val : stringValues) {
				outList.add(val);
			}
		}
		return outList;
	}

	/**
	 * Parameter query string.
	 *
	 * @return the string
	 */
	public String parameterQuery() {
		StringBuilder sb = new StringBuilder();
		int pos = 0;
		Iterator it = parameters.entrySet().iterator();
		while (it.hasNext()) {
			if (pos > 0)
				sb.append("&");
			Map.Entry pair = (Map.Entry) it.next();
			sb.append("fq=");
			sb.append(pair.getKey());
			sb.append(":");
			sb.append(((Parameter) pair.getValue()).toStringSafe());
			HashMap<String, FilterCriteria> criterion = ((Parameter) pair.getValue()).getCriterion();
			if (criterion != null && criterion.size() > 0) {
				Iterator crit = criterion.entrySet().iterator();
				while (crit.hasNext()) {
					Map.Entry citPair = (Map.Entry) crit.next();
					sb.append(";");
					sb.append(citPair.getKey());
					sb.append(":");
					sb.append(((FilterCriteria) citPair.getValue()).toStringSafe());
				}
			}

			pos++;
		}
		return sb.toString();
	}

	/**
	 * Gets errors.
	 *
	 * @return the errors
	 */
	public HashMap<String, String> getErrors() {
		return errors;
	}

	/**
	 * Boolean to return of there are errors
	 *
	 * @return has errors boolean
	 */
	public boolean hasErrors() {
		return errors.size() > 0;
	}

	public Date getSelectorCriteriaValueAsDate(String key, String criteriaKey) {

		if (selectorContainsCriteria(key, criteriaKey)) {
			Parameter p = selectors.get(key);
			return StringUtil.stringToDate(p.getCriteria(criteriaKey).toString(), AppConstants.JSON_UTC_DATE_FORMAT);
		} else {
			return null;
		}
	}

	/**
	 * Nested class to hold parameters
	 */
	public class Parameter {

		private String name;

		private HashMap<String, FilterCriteria> criterion = new HashMap<>();

		private List<String> values = new ArrayList<>();

		/**
		 * Add value.
		 *
		 * @param value the value
		 */
		public void addValue(String value) {
			values.add(value);
		}

		/**
		 * Add criteria.
		 *
		 * @param criteria the criteria
		 */
		public void addCriteria(FilterCriteria criteria) {
			criterion.put(criteria.getName(), criteria);
		}

		/**
		 * Gets name.
		 *
		 * @return the name
		 */
		public String getName() {
			return name;
		}

		/**
		 * Sets name.
		 *
		 * @param name the name
		 */
		public void setName(String name) {
			this.name = name;
		}

		/**
		 * Gets values.
		 *
		 * @return the values
		 */
		public List<String> getValues() {
			return values;
		}

		/**
		 * Sets values.
		 *
		 * @param value the value
		 */
		public void setValues(List<String> value) {
			this.values = value;
		}

		/**
		 * Gets criterion.
		 *
		 * @return the criterion
		 */
		public HashMap<String, FilterCriteria> getCriterion() {
			return criterion;
		}

		/**
		 * Sets criterion.
		 *
		 * @param criterias the criterias
		 */
		public void setCriterion(HashMap<String, FilterCriteria> criterias) {
			this.criterion = criterias;
		}

		/**
		 * Contains criteria boolean.
		 *
		 * @param key the key
		 * @return the boolean
		 */
		public boolean containsCriteria(String key) {
			return criterion.containsKey(key);
		}

		/**
		 * Has multiple values boolean.
		 *
		 * @return the boolean
		 */
		public boolean hasMultipleValues() {
			return values.size() > 1;
		}

		/**
		 * First value string.
		 *
		 * @return the string
		 */
		public String firstValue() {
			return values.get(0);
		}

		@Override
		public String toString() {
			if (values != null || values.size() > 0)
				return StringUtil.toDelimitedString(values);
			return null;
		}

		/**
		 * To string safe string.
		 *
		 * @return the string
		 */
		public String toStringSafe() {
			if (values != null || values.size() > 0)
				return StringUtil.toDelimitedString(values, true);
			return null;
		}

		/**
		 * Has criteria boolean.
		 *
		 * @param key the key
		 * @return the boolean
		 */
		public boolean hasCriteria(String key) {
			return criterion.containsKey(key);
		}

		/**
		 * Gets criteria.
		 *
		 * @param key the key
		 * @return the criteria
		 */
		public FilterCriteria getCriteria(String key) {
			return criterion.get(key);
		}
	}

	/**
	 * The type Filter criteria.
	 */
	public class FilterCriteria {
		private List<String> values;

		private String name;

		/**
		 * Gets name.
		 *
		 * @return the name
		 */
		public String getName() {
			return name;
		}

		/**
		 * Sets name.
		 *
		 * @param name the name
		 */
		public void setName(String name) {
			this.name = name;
		}

		/**
		 * Gets values.
		 *
		 * @return the values
		 */
		public List<String> getValues() {
			return values;
		}

		/**
		 * Sets values.
		 *
		 * @param value the value
		 */
		public void setValues(List<String> value) {
			this.values = value;
		}

		@Override
		public String toString() {
			if (values != null || values.size() > 0)
				return StringUtil.toDelimitedString(values, false);
			return null;
		}

		/**
		 * To string safe string.
		 *
		 * @return the string
		 */
		public String toStringSafe() {
			if (values != null || values.size() > 0)
				return StringUtil.toDelimitedString(values, true);
			return null;
		}
	}

}
