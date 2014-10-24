package com.hawkprime.syndicate.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Calculate Frequencies.
 */
public final class FrequencyCalculator {
	private static final Logger LOG = LoggerFactory.getLogger(FrequencyCalculator.class);

	private static final double PERCENT = 100.00;
	private static final int MINS_PER_HR = 60;

	private static final int DEFAULT_MAX_OPTIMAL_RANGE = 80;
	private static final int DEFAULT_MIN_OPTIMAL_RANGE = 70;

	private static final int DEFAULT_MIN_FREQUENCY = 2880;
	private static final int DEFAULT_MAX_FREQUENCY = 1;

	private static int maxOptimalRange = DEFAULT_MAX_OPTIMAL_RANGE;
	private static int minOptimalRange = DEFAULT_MIN_OPTIMAL_RANGE;

	private static int minFrequency = DEFAULT_MIN_FREQUENCY;
	private static int maxFrequency = DEFAULT_MAX_FREQUENCY;

	private FrequencyCalculator() { }

	public static void setOptimalRange(final int min, final int max) {
		maxOptimalRange = max;
		minOptimalRange = min;
	}

	public static void setUpdateFrequencyRange(final int min, final int max) {
		minFrequency = min;
		maxFrequency = max;
	}

	public static int getMaxOptimalRange() {
		return maxOptimalRange;
	}

	public static int getMinOptimalRange() {
		return minOptimalRange;
	}

	public static int getMaxUpdateFrequency() {
		return maxFrequency;
	}

	public static int getMinUpdateFrequency() {
		return minFrequency;
	}

	public static int calculateNewFrequency(final int currentFrequency, final int percentNew) {
		LOG.debug("Current frequency is every {} hours {} minutes, new posts are at {}%",
				currentFrequency / MINS_PER_HR, currentFrequency % MINS_PER_HR, percentNew);

		if (percentNew < minOptimalRange) {
			LOG.debug("Too few posts, less updates needed, adjusting");

			int adjustment = (int) ((minOptimalRange - percentNew) / PERCENT * currentFrequency);
			if (adjustment < 1) {
				adjustment = 1;
			}
			LOG.debug("Decresing update times by {} hours {} minutes",
					adjustment / MINS_PER_HR, adjustment % MINS_PER_HR);

			adjustment = currentFrequency + adjustment;
			if (adjustment > minFrequency) {
				LOG.debug("Adjustment would be below minimum update threshold, using minimum update");
				adjustment = minFrequency;
			}

			LOG.debug("Adjusting to {} hours {} minute updates",
					adjustment / MINS_PER_HR, adjustment % MINS_PER_HR);

			return adjustment;

		} else if (percentNew > maxOptimalRange) {
			LOG.debug("Too many posts, more updates are needed, adjusting");

			int adjustment = (int) ((percentNew - maxOptimalRange) / PERCENT * currentFrequency);
			if (adjustment < 1) {
				adjustment = 1;
			}
			LOG.debug("Increasing update times by {} hours {} minutes",
					adjustment / MINS_PER_HR, adjustment % MINS_PER_HR);

			adjustment = currentFrequency - adjustment;
			if (adjustment < maxFrequency) {
				LOG.debug("Adjustment would be above maximum update threshold, using maximum update");
				adjustment = maxFrequency;
			}
			LOG.debug("Adjusting to {} hours {} minute updates",
					adjustment / MINS_PER_HR, adjustment % MINS_PER_HR);

			return adjustment;

		} else {
			LOG.debug("Percentage is optimal, no adjustments neccesary");
			return currentFrequency;
		}
	}
}
