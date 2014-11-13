package com.hawkprime.syndicate.util;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

/**
 * Elapsed Time Tests.
 */
public class TimeFormatTest {

	/**
	 * Format milliseconds.
	 */
	@Test
	public void formatMilliseconds() {
		// CHECKSTYLE:OFF MagicNumber
		assertThat(TimeFormat.formatMilliseconds(0), is("0 milliseconds"));
		assertThat(TimeFormat.formatMilliseconds(999), is("999 milliseconds"));
		assertThat(TimeFormat.formatMilliseconds(1000), is("1 second, 0 milliseconds"));
		assertThat(TimeFormat.formatMilliseconds(1001), is("1 second, 1 millisecond"));
		assertThat(TimeFormat.formatMilliseconds(59000), is("59 seconds, 0 milliseconds"));
		assertThat(TimeFormat.formatMilliseconds(60000), is("1 minute, 0 seconds, 0 milliseconds"));
		assertThat(TimeFormat.formatMilliseconds(61000), is("1 minute, 1 second, 0 milliseconds"));
		assertThat(TimeFormat.formatMilliseconds(61001), is("1 minute, 1 second, 1 millisecond"));
		assertThat(TimeFormat.formatMilliseconds(3599059), is("59 minutes, 59 seconds, 59 milliseconds"));
		assertThat(TimeFormat.formatMilliseconds(3600000), is("1 hour, 0 minutes, 0 seconds, 0 milliseconds"));
		assertThat(TimeFormat.formatMilliseconds(3600001), is("1 hour, 0 minutes, 0 seconds, 1 millisecond"));
		assertThat(TimeFormat.formatMilliseconds(3600059), is("1 hour, 0 minutes, 0 seconds, 59 milliseconds"));
		assertThat(TimeFormat.formatMilliseconds(3601000), is("1 hour, 0 minutes, 1 second, 0 milliseconds"));
		assertThat(TimeFormat.formatMilliseconds(3601001), is("1 hour, 0 minutes, 1 second, 1 millisecond"));
		assertThat(TimeFormat.formatMilliseconds(3659000), is("1 hour, 0 minutes, 59 seconds, 0 milliseconds"));
		assertThat(TimeFormat.formatMilliseconds(3659059), is("1 hour, 0 minutes, 59 seconds, 59 milliseconds"));
		assertThat(TimeFormat.formatMilliseconds(3660000), is("1 hour, 1 minute, 0 seconds, 0 milliseconds"));
		assertThat(TimeFormat.formatMilliseconds(3660001), is("1 hour, 1 minute, 0 seconds, 1 millisecond"));
		assertThat(TimeFormat.formatMilliseconds(3661000), is("1 hour, 1 minute, 1 second, 0 milliseconds"));
		assertThat(TimeFormat.formatMilliseconds(3661001), is("1 hour, 1 minute, 1 second, 1 millisecond"));
		assertThat(TimeFormat.formatMilliseconds(7200000), is("2 hours, 0 minutes, 0 seconds, 0 milliseconds"));

		assertThat(TimeFormat.formatMilliseconds(86399059),
				is("23 hours, 59 minutes, 59 seconds, 59 milliseconds"));

		assertThat(TimeFormat.formatMilliseconds(86400000),
				is("1 day, 0 hours, 0 minutes, 0 seconds, 0 milliseconds"));

		assertThat(TimeFormat.formatMilliseconds(90061001),
				is("1 day, 1 hour, 1 minute, 1 second, 1 millisecond"));

		assertThat(TimeFormat.formatMilliseconds(172800000),
				is("2 days, 0 hours, 0 minutes, 0 seconds, 0 milliseconds"));
		// CHECKSTYLE:ON MagicNumber
	}

	/**
	 * Format seconds.
	 */
	@Test
	public void formatSeconds() {
		// CHECKSTYLE:OFF MagicNumber
		assertThat(TimeFormat.formatSeconds(0), is("0 seconds"));
		assertThat(TimeFormat.formatSeconds(1), is("1 second"));
		assertThat(TimeFormat.formatSeconds(59), is("59 seconds"));
		assertThat(TimeFormat.formatSeconds(60), is("1 minute, 0 seconds"));
		assertThat(TimeFormat.formatSeconds(61), is("1 minute, 1 second"));
		assertThat(TimeFormat.formatSeconds(3599), is("59 minutes, 59 seconds"));
		assertThat(TimeFormat.formatSeconds(3600), is("1 hour, 0 minutes, 0 seconds"));
		assertThat(TimeFormat.formatSeconds(3601), is("1 hour, 0 minutes, 1 second"));
		assertThat(TimeFormat.formatSeconds(3659), is("1 hour, 0 minutes, 59 seconds"));
		assertThat(TimeFormat.formatSeconds(3660), is("1 hour, 1 minute, 0 seconds"));
		assertThat(TimeFormat.formatSeconds(3661), is("1 hour, 1 minute, 1 second"));
		assertThat(TimeFormat.formatSeconds(7200), is("2 hours, 0 minutes, 0 seconds"));
		assertThat(TimeFormat.formatSeconds(86399), is("23 hours, 59 minutes, 59 seconds"));
		assertThat(TimeFormat.formatSeconds(86400), is("1 day, 0 hours, 0 minutes, 0 seconds"));
		assertThat(TimeFormat.formatSeconds(90061), is("1 day, 1 hour, 1 minute, 1 second"));
		assertThat(TimeFormat.formatSeconds(172800), is("2 days, 0 hours, 0 minutes, 0 seconds"));
		// CHECKSTYLE:ON MagicNumber
	}

	/**
	 * Format minutes.
	 */
	@Test
	public void formatMinutes() {
		// CHECKSTYLE:OFF MagicNumber
		assertThat(TimeFormat.formatMinutes(0), is("0 minutes"));
		assertThat(TimeFormat.formatMinutes(1), is("1 minute"));
		assertThat(TimeFormat.formatMinutes(59), is("59 minutes"));
		assertThat(TimeFormat.formatMinutes(60), is("1 hour, 0 minutes"));
		assertThat(TimeFormat.formatMinutes(61), is("1 hour, 1 minute"));
		assertThat(TimeFormat.formatMinutes(1439), is("23 hours, 59 minutes"));
		assertThat(TimeFormat.formatMinutes(1440), is("1 day, 0 hours, 0 minutes"));
		assertThat(TimeFormat.formatMinutes(1441), is("1 day, 0 hours, 1 minute"));
		assertThat(TimeFormat.formatMinutes(1499), is("1 day, 0 hours, 59 minutes"));
		assertThat(TimeFormat.formatMinutes(1500), is("1 day, 1 hour, 0 minutes"));
		assertThat(TimeFormat.formatMinutes(1501), is("1 day, 1 hour, 1 minute"));
		assertThat(TimeFormat.formatMinutes(2880), is("2 days, 0 hours, 0 minutes"));
		// CHECKSTYLE:ON MagicNumber
	}
}
