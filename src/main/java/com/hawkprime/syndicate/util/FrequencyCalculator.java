package com.hawkprime.syndicate.util;

import com.hawkprime.syndicate.service.ConfigurationService;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

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
	private static final int DEFAULT_MAX_OPTIMAL_RANGE = 80;
	private static final int DEFAULT_MIN_OPTIMAL_RANGE = 70;
	private static final int DEFAULT_MAX_FREQUENCY = 1;
	private static final int DEFAULT_MIN_FREQUENCY = 2880;

	@Autowired
	private ConfigurationService configService;

	private int maxOptimalRange;
	private int minOptimalRange;
	private int minFrequency;
	private int maxFrequency;

	/**
	 * Initialize Calculator.
	 */
	@PostConstruct
	@Transactional
	private void init() {
		maxOptimalRange = configService.getValue(NodePath.at("/App/Feed/MaxOptimalRange"), DEFAULT_MAX_OPTIMAL_RANGE);
		minOptimalRange = configService.getValue(NodePath.at("/App/Feed/MinOptimalRange"), DEFAULT_MIN_OPTIMAL_RANGE);
		maxFrequency = configService.getValue(NodePath.at("/App/Feed/MaxUpdate"), DEFAULT_MAX_FREQUENCY);
		minFrequency = configService.getValue(NodePath.at("/App/Feed/MinUpdate"), DEFAULT_MIN_FREQUENCY);
	}

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
	 * @param max Maximum number of minutes to check (min wait time)
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
	 * Get minimum optimal percent range.
	 * @return integer range from 0 to 100
	 */
	public int getMinOptimalRange() {
		return minOptimalRange;
	}

	/**
	 * Get maximum update frequency.
	 * @return maximum number of minutes to wait before update
	 */
	public int getMaxUpdateFrequency() {
		return maxFrequency;
	}

	/**
	 * Get minimum update frequency.
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
