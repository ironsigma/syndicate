package com.hawkprime.syndicate.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * Calculate Frequencies.
 *
 * @version 5.0.0
 * @author Juan D Frias <juandfrias@gmail.com>
 */
@Component
public class FrequencyCalculator {
	private static final Logger LOG = LoggerFactory.getLogger(FrequencyCalculator.class);

	private static final double PERCENT = 100.00;

	@Value("${syndicate.update.frequency.optimal.max}")
	private int maxOptimalRange;

	@Value("${syndicate.update.frequency.optimal.min}")
	private int minOptimalRange;

	@Value("${syndicate.update.frequency.min}")
	private int minFrequency;

	@Value("${syndicate.update.frequency.max}")
	private int maxFrequency;

	/**
	 * Set optimal range.
	 * @param min Minimum post per update percentage
	 * @param max Max post per update percentage
	 */
	public void setOptimalRange(final int min, final int max) {
		maxOptimalRange = max;
		minOptimalRange = min;
	}

	/**
	 * Set update frequency range.
	 * @param min Minimum number of minutes to check (max wait time)
	 * @param max Maxinum numbe of minutes to check (min wait time)
	 */
	public void setUpdateFrequencyRange(final int min, final int max) {
		minFrequency = min;
		maxFrequency = max;
	}

	/**
	 * Get maximum optimal percent range.
	 * @return integer range from 0 to 100
	 */
	public int getMaxOptimalRange() {
		return maxOptimalRange;
	}

	/**
	 * Get mininum optimal percent range.
	 * @return integer range from 0 to 100
	 */
	public int getMinOptimalRange() {
		return minOptimalRange;
	}

	/**
	 * Get maximum update frequency.
	 * @return maxium number of minutes to wait before update
	 */
	public int getMaxUpdateFrequency() {
		return maxFrequency;
	}

	/**
	 * Get mininum update frequency.
	 * @return minimum number of minutes to wait before update
	 */
	public int getMinUpdateFrequency() {
		return minFrequency;
	}

	/**
	 * Calculate new frequency to update.
	 * @param currentFrequency current number of minutes between updates
	 * @param percentNew percent number of new post in feed from 0 to 100
	 * @return new number of minutes to wait before updates
	 */
	public int calculateNewFrequency(final int currentFrequency, final int percentNew) {
		LOG.debug("Current frequency is every {}, new posts are at {}%",
				TimeFormat.formatMinutes(currentFrequency), percentNew);

		if (percentNew >= minOptimalRange && percentNew <= maxOptimalRange) {
			LOG.debug("Percentage is optimal, no adjustments neccesary");
			return currentFrequency;
		}

		int adjustment;
		if (percentNew < minOptimalRange) {
			LOG.debug("Too few posts, less updates needed, adjusting");

			adjustment = (int) ((minOptimalRange - percentNew) / PERCENT * currentFrequency);
			if (adjustment < 1) {
				adjustment = 1;
			}
			LOG.debug("Decresing update times by {}", TimeFormat.formatMinutes(adjustment));

			adjustment = currentFrequency + adjustment;
			if (adjustment > minFrequency) {
				LOG.debug("Adjustment would be below minimum update threshold, using minimum update");
				adjustment = minFrequency;
			}

		} else {
			LOG.debug("Too many posts, more updates are needed, adjusting");

			adjustment = (int) ((percentNew - maxOptimalRange) / PERCENT * currentFrequency);
			if (adjustment < 1) {
				adjustment = 1;
			}
			LOG.debug("Increasing update times by {}", TimeFormat.formatMinutes(adjustment));

			adjustment = currentFrequency - adjustment;
			if (adjustment < maxFrequency) {
				LOG.debug("Adjustment would be above maximum update threshold, using maximum update");
				adjustment = maxFrequency;
			}

		}

		LOG.debug("Adjusting to {}", TimeFormat.formatMinutes(adjustment));
		return adjustment;
	}
}
