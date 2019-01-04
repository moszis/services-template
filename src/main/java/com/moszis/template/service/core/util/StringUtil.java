package com.moszis.template.service.core.util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

public class StringUtil {
	private final static String defaultDelimiter = ",";

    /**
     * String to uuid uuid.
     *
     * @param uuidString the uuid string
     * @return the uuid
     */
    public static UUID stringToUUID(String uuidString) {
		if (!uuidString.contains("-")) {
			uuidString = uuidString.replaceFirst("([0-9a-fA-F]{8})([0-9a-fA-F]{4})([0-9a-fA-F]{4})([0-9a-fA-F]{4})([0-9a-fA-F]+)", "$1-$2-$3-$4-$5")
					.toLowerCase();
		}
		return UUID.fromString(uuidString);
	}

    /**
     * String to date date.
     *
     * @param date   the date
     * @param format the format
     * @return the date
     */
    public static Date stringToDate(String date, String format) {
		try {
			DateFormat df1 = new SimpleDateFormat(format);

			return df1.parse(date);
		} catch (Exception e) {
			return null;
		}
	}

    /**
     * Uuid to string string.
     *
     * @param u the u
     * @return the string
     */
    public static String uuidToString(UUID u) {
		if (u == null)
			return null;
		return u.toString().replaceAll("-", "").toUpperCase();
	}

    /**
     * To delimited string string.
     *
     * @param values the values
     * @return the string
     */
    public static String toDelimitedString(List<String> values) {

		return toDelimitedString(values, defaultDelimiter, false);

	}

    /**
     * To delimited string string.
     *
     * @param values   the values
     * @param safeWrap the safe wrap
     * @return the string
     */
    public static String toDelimitedString(List<String> values, boolean safeWrap) {

		return toDelimitedString(values, defaultDelimiter, safeWrap);

	}

    /**
     * To delimited string string.
     *
     * @param values    the values
     * @param delimiter the delimiter
     * @param safeWrap  the safe wrap
     * @return the string
     */
    public static String toDelimitedString(List<String> values, String delimiter, boolean safeWrap) {
		StringBuilder sb = new StringBuilder();
		int i = 0;
		for (String s : values) {
			if (i > 0) {
				sb.append(delimiter);
			}
			if (safeWrap) {
				sb.append("[" + s + "]");
			} else {
				sb.append(s);
			}
			i++;
		}
		return sb.toString();
	}

    /**
     * Is not empty boolean.
     *
     * @param str the str
     * @return the boolean
     */
    public static boolean isNotEmpty(String str) {
		return !isEmpty(str);
	}

    /**
     * Is empty boolean.
     *
     * @param str the str
     * @return the boolean
     */
    public static boolean isEmpty(String str) {
		return (str == null) || "".equals(str.trim());
	}

    /**
     * Add uuid dashes string.
     *
     * @param idNoDashes the id no dashes
     * @return the string
     */
    public static String addUUIDDashes(String idNoDashes) {
		StringBuffer idBuff = new StringBuffer(idNoDashes);
		idBuff.insert(20, '-');
		idBuff.insert(16, '-');
		idBuff.insert(12, '-');
		idBuff.insert(8, '-');
		return idBuff.toString();
	}

    /**
     * To delimited string string.
     *
     * @param list      the list
     * @param delimiter the delimiter
     * @return the string
     */
    public static String toDelimitedString(List<UUID> list, String delimiter) {
		List<String> stringIds = new ArrayList<>();
		if (list != null && !list.isEmpty()) {
			for (UUID id : list) {
				stringIds.add(StringUtil.uuidToString(id));
			}
			return String.join(delimiter, stringIds);
		} else {
			return null;
		}
	}

}

