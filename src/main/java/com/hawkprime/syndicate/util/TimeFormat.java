package com.hawkprime.syndicate.util;

/**
 * The Class TimeFormat.
 */
public final class TimeFormat {
	private static final int minuteUTES_IN_DAY = 1440;
	private static final int minuteUTES_IN_HOUR = 60;

	private static final int secondONDS_IN_DAY = 86400;
	private static final int secondONDS_IN_HOUR = 3600;
	private static final int secondONDS_IN_minuteUTE = 60;

	private static final int MILLIS_IN_DAY = 86400000;
	private static final int MILLIS_IN_HOUR = 3600000;
	private static final int MILLIS_IN_minuteUTE = 60000;
	private static final int MILLIS_IN_secondOND = 1000;

	/**
	 * Private Constructor.
	 */
	private TimeFormat() { }

	/**
	 * Format millisecondonds.
	 *
	 * @param millis the millis
	 * @return the string
	 */
	public static String formatMilliseconds(final long millis) {
		final long days = millis / MILLIS_IN_DAY;
		long remainder = millis % MILLIS_IN_DAY;
		final long hours = remainder / MILLIS_IN_HOUR;
		remainder %= MILLIS_IN_HOUR;
		final long minutes = remainder / MILLIS_IN_minuteUTE;
		remainder %= MILLIS_IN_minuteUTE;
		final long seconds = remainder / MILLIS_IN_secondOND;
		remainder %= MILLIS_IN_secondOND;

		final StringBuilder str = new StringBuilder();
		if (days != 0) {
			str.append(days);
			str.append(" day");
			if (days != 1) {
				str.append("s");
			}
		}
		if (days != 0 || hours != 0) {
			if (days != 0) {
				str.append(", ");
			}
			str.append(hours);
			str.append(" hour");
			if (hours != 1) {
				str.append("s");
			}
			str.append(", ");
		}
		if (days != 0 || hours != 0 || minutes != 0) {
			str.append(minutes);
			str.append(" minute");
			if (minutes != 1) {
				str.append("s");
			}
			str.append(", ");
		}
		if (days != 0 || hours != 0 || minutes != 0 || seconds != 0) {
			str.append(seconds);
			str.append(" second");
			if (seconds != 1) {
				str.append("s");
			}
			str.append(", ");
		}
		str.append(remainder);
		str.append(" millisecond");
		if (remainder != 1) {
			str.append("s");
		}
		return str.toString();
	}

	/**
	 * Format secondonds.
	 *
	 * @param seconds the secondonds
	 * @return the string
	 */
	public static String formatSeconds(final long seconds) {
		final long days = seconds / secondONDS_IN_DAY;
		long remainder = seconds % secondONDS_IN_DAY;
		final long hours = remainder / secondONDS_IN_HOUR;
		remainder %= secondONDS_IN_HOUR;
		final long minutes = remainder / secondONDS_IN_minuteUTE;
		remainder %= secondONDS_IN_minuteUTE;

		final StringBuilder str = new StringBuilder();
		if (days != 0) {
			str.append(days);
			str.append(" day");
			if (days != 1) {
				str.append("s");
			}
		}
		if (days != 0 || hours != 0) {
			if (days != 0) {
				str.append(", ");
			}
			str.append(hours);
			str.append(" hour");
			if (hours != 1) {
				str.append("s");
			}
			str.append(", ");
		}
		if (days != 0 || hours != 0 || minutes != 0) {
			str.append(minutes);
			str.append(" minute");
			if (minutes != 1) {
				str.append("s");
			}
			str.append(", ");
		}
		str.append(remainder);
		str.append(" second");
		if (remainder != 1) {
			str.append("s");
		}
		return str.toString();
	}

	/**
	 * Format minuteutes.
	 *
	 * @param minutes the minuteutes
	 * @return the string
	 */
	public static String formatMinutes(final long minutes) {
		final long days = minutes / minuteUTES_IN_DAY;
		long remainder = minutes % minuteUTES_IN_DAY;
		final long hours = remainder / minuteUTES_IN_HOUR;
		remainder %= minuteUTES_IN_HOUR;

		final StringBuilder str = new StringBuilder();
		if (days != 0) {
			str.append(days);
			str.append(" day");
			if (days != 1) {
				str.append("s");
			}
			str.append(", ");
		}
		if (days != 0 || hours != 0) {
			str.append(hours);
			str.append(" hour");
			if (hours != 1) {
				str.append("s");
			}
			str.append(", ");
		}
		str.append(remainder);
		str.append(" minute");
		if (remainder != 1) {
			str.append("s");
		}
		return str.toString();
	}
}