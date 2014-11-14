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

	private static final String DAY = " day";
	private static final String HOUR = " hour";
	private static final String MINUTE = " minute";
	private static final String SECOND = " second";
	private static final String MILLISECOND = " millisecond";
	private static final String PLURAL = "s";
	private static final String SEPARATOR = ", ";

	/**
	 * Private Constructor.
	 */
	private TimeFormat() { }

	/**
	 * Format milliseconds.
	 *
	 * @param millis the milliseconds
	 * @return the string
	 */
	public static String formatMilliseconds(final long millis) {
		final long days = millis / MILLIS_IN_DAY;
		long remainder = millis % MILLIS_IN_DAY;
		final long hours = remainder / MILLIS_IN_HOUR;
		remainder %= MILLIS_IN_HOUR;
		final long minutes = remainder / MILLIS_IN_MINUTEUTE;
		remainder %= MILLIS_IN_MINUTEUTE;
		final long seconds = remainder / MILLIS_IN_SECONDOND;
		remainder %= MILLIS_IN_SECONDOND;

		final StringBuilder str = new StringBuilder();
		if (days != 0) {
			str.append(days);
			str.append(DAY);
			if (days != 1) {
				str.append(PLURAL);
			}
		}
		if (days != 0 || hours != 0) {
			if (days != 0) {
				str.append(SEPARATOR);
			}
			str.append(hours);
			str.append(HOUR);
			if (hours != 1) {
				str.append(PLURAL);
			}
			str.append(SEPARATOR);
		}
		if (days != 0 || hours != 0 || minutes != 0) {
			str.append(minutes);
			str.append(MINUTE);
			if (minutes != 1) {
				str.append(PLURAL);
			}
			str.append(SEPARATOR);
		}
		if (days != 0 || hours != 0 || minutes != 0 || seconds != 0) {
			str.append(seconds);
			str.append(SECOND);
			if (seconds != 1) {
				str.append(PLURAL);
			}
			str.append(SEPARATOR);
		}
		str.append(remainder);
		str.append(MILLISECOND);
		if (remainder != 1) {
			str.append(PLURAL);
		}
		return str.toString();
	}

	/**
	 * Format seconds.
	 *
	 * @param seconds the seconds
	 * @return the string
	 */
	public static String formatSeconds(final long seconds) {
		final long days = seconds / SECONDONDS_IN_DAY;
		long remainder = seconds % SECONDONDS_IN_DAY;
		final long hours = remainder / SECONDONDS_IN_HOUR;
		remainder %= SECONDONDS_IN_HOUR;
		final long minutes = remainder / SECONDONDS_IN_MINUTEUTE;
		remainder %= SECONDONDS_IN_MINUTEUTE;

		final StringBuilder str = new StringBuilder();
		if (days != 0) {
			str.append(days);
			str.append(DAY);
			if (days != 1) {
				str.append(PLURAL);
			}
		}
		if (days != 0 || hours != 0) {
			if (days != 0) {
				str.append(SEPARATOR);
			}
			str.append(hours);
			str.append(HOUR);
			if (hours != 1) {
				str.append(PLURAL);
			}
			str.append(SEPARATOR);
		}
		if (days != 0 || hours != 0 || minutes != 0) {
			str.append(minutes);
			str.append(MINUTE);
			if (minutes != 1) {
				str.append(PLURAL);
			}
			str.append(SEPARATOR);
		}
		str.append(remainder);
		str.append(SECOND);
		if (remainder != 1) {
			str.append(PLURAL);
		}
		return str.toString();
	}

	/**
	 * Format minutes.
	 *
	 * @param minutes the minutes
	 * @return the string
	 */
	public static String formatMinutes(final long minutes) {
		final long days = minutes / MINUTEUTES_IN_DAY;
		long remainder = minutes % MINUTEUTES_IN_DAY;
		final long hours = remainder / MINUTEUTES_IN_HOUR;
		remainder %= MINUTEUTES_IN_HOUR;

		final StringBuilder str = new StringBuilder();
		if (days != 0) {
			str.append(days);
			str.append(DAY);
			if (days != 1) {
				str.append(PLURAL);
			}
			str.append(SEPARATOR);
		}
		if (days != 0 || hours != 0) {
			str.append(hours);
			str.append(HOUR);
			if (hours != 1) {
				str.append(PLURAL);
			}
			str.append(SEPARATOR);
		}
		str.append(remainder);
		str.append(MINUTE);
		if (remainder != 1) {
			str.append(PLURAL);
		}
		return str.toString();
	}
}
