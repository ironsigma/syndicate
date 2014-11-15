package com.hawkprime.syndicate.util;

/**
 * The Class TimeFormat.
 *
 * @version 5.0.0
 * @author Juan D Frias <juandfrias@gmail.com>
 */
public final class TimeFormat {
	private static final int MINUTEUTES_IN_DAY = 1440;
	private static final int MINUTEUTES_IN_HOUR = 60;

	private static final int SECONDONDS_IN_DAY = 86400;
	private static final int SECONDONDS_IN_HOUR = 3600;
	private static final int SECONDONDS_IN_MINUTEUTE = 60;

	private static final int MILLIS_IN_DAY = 86400000;
	private static final int MILLIS_IN_HOUR = 3600000;
	private static final int MILLIS_IN_MINUTEUTE = 60000;
	private static final int MILLIS_IN_SECONDOND = 1000;

	private static final String DAY = "day";
	private static final String HOUR = "hour";
	private static final String MINUTE = "minute";
	private static final String SECOND = "second";
	private static final String MILLISECOND = "millisecond";
	private static final String SEPARATOR = ", ";

	/**
	 * Private Constructor.
	 */
	private TimeFormat() { }

	/**
	 * Display number and label with plurals.
	 * @param number Number
	 * @param string Label
	 * @return String pluralized
	 */
	private static String plural(final long number, final String string) {
		if (number != 1) {
			return String.format("%d %ss", number, string);
		}
		return String.format("%d %s", number, string);
	}

	/**
	 * Format times into a single string.
	 * @param days Days
	 * @param hours Hours
	 * @param minutes Minutes
	 * @param seconds Seconds
	 * @param millis Milliseconds
	 * @return formated String.
	 */
	private static String formatTimes(final long days, final long hours, final long minutes,
			final Long seconds, final Long millis) {

		final StringBuilder str = new StringBuilder();
		if (days != 0) {
			str.append(plural(days, DAY));
			str.append(SEPARATOR);
		}
		if (days != 0 || hours != 0) {
			str.append(plural(hours, HOUR));
			str.append(SEPARATOR);
		}
		if (days != 0 || hours != 0 || minutes != 0 || seconds == null) {
			str.append(plural(minutes, MINUTE));
			if (seconds != null) {
				str.append(SEPARATOR);
			}
		}
		if (seconds != null) {
			if (days != 0 || hours != 0 || minutes != 0 || seconds != 0 || millis == null) {
				str.append(plural(seconds, SECOND));
				if (millis != null) {
					str.append(SEPARATOR);
				}
			}
			if (millis != null) {
				str.append(plural(millis, MILLISECOND));
			}
		}
		return str.toString();
	}

	/**
	 * Format milliseconds.
	 *
	 * @param time the milliseconds
	 * @return the string
	 */
	public static String formatMilliseconds(final long time) {
		final long days = time / MILLIS_IN_DAY;
		long millis = time % MILLIS_IN_DAY;
		final long hours = millis / MILLIS_IN_HOUR;
		millis %= MILLIS_IN_HOUR;
		final long minutes = millis / MILLIS_IN_MINUTEUTE;
		millis %= MILLIS_IN_MINUTEUTE;
		final long seconds = millis / MILLIS_IN_SECONDOND;
		millis %= MILLIS_IN_SECONDOND;

		return formatTimes(days, hours, minutes, seconds, millis);
	}

	/**
	 * Format seconds.
	 *
	 * @param time the seconds
	 * @return the string
	 */
	public static String formatSeconds(final long time) {
		final long days = time / SECONDONDS_IN_DAY;
		long seconds = time % SECONDONDS_IN_DAY;
		final long hours = seconds / SECONDONDS_IN_HOUR;
		seconds %= SECONDONDS_IN_HOUR;
		final long minutes = seconds / SECONDONDS_IN_MINUTEUTE;
		seconds %= SECONDONDS_IN_MINUTEUTE;

		return formatTimes(days, hours, minutes, seconds, null);
	}

	/**
	 * Format minutes.
	 *
	 * @param time the minutes
	 * @return the string
	 */
	public static String formatMinutes(final long time) {
		final long days = time / MINUTEUTES_IN_DAY;
		long minutes = time % MINUTEUTES_IN_DAY;
		final long hours = minutes / MINUTEUTES_IN_HOUR;
		minutes %= MINUTEUTES_IN_HOUR;

		return formatTimes(days, hours, minutes, null, null);
	}
}
