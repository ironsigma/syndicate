package com.hawkprime.syndicate.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * Calculate Frequencies.
 */
@Component
public class FrequencyCalculator {
	private static final Logger LOG = LoggerFactory.getLogger(FrequencyCalculator.class);

	private static final double PERCENT = 100.00;
	private static final int MINS_PER_HR = 60;

	@Value("${syndicate.update.frequency.optimal.max}")
	private int maxOptimalRange;

	@Value("${syndicate.update.frequency.optimal.min}")
	private int minOptimalRange;

	@Value("${syndicate.update.frequency.min}")
	private int minFrequency;

	@Value("${syndicate.update.frequency.max}")
	private int maxFrequency;

	public void setOptimalRange(final int min, final int max) {
		maxOptimalRange = max;
		minOptimalRange = min;
	}

	public void setUpdateFrequencyRange(final int min, final int max) {
		minFrequency = min;
		maxFrequency = max;
	}

	public int getMaxOptimalRange() {
		return maxOptimalRange;
	}

	public int getMinOptimalRange() {
		return minOptimalRange;
	}

	public int getMaxUpdateFrequency() {
		return maxFrequency;
	}

	public int getMinUpdateFrequency() {
		return minFrequency;
	}

	public int calculateNewFrequency(final int currentFrequency, final int percentNew) {
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
