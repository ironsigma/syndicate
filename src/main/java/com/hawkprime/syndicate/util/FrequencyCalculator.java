package com.hawkprime.syndicate.util;

import com.hawkprime.syndicate.service.ConfigurationService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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
	private static final int DEFAULT_MAX_OPTIMAL_RANGE = 80;
	private static final int DEFAULT_MIN_OPTIMAL_RANGE = 70;
	private static final int DEFAULT_MAX_FREQUENCY = 1;
	private static final int DEFAULT_MIN_FREQUENCY = 2880;

	@Autowired
	private ConfigurationService configService;

	/**
	 * Calculate new frequency to update.
	 * @param currentFrequency current number of minutes between updates
	 * @param percentNew percent number of new post in feed from 0 to 100
	 * @return new number of minutes to wait before updates
	 */
	public int calculateNewFrequency(final int currentFrequency, final int percentNew) {
		final int maxOptimalRange = configService.getValue(NodePath.at("/App/Feed/MaxOptimalRange"), DEFAULT_MAX_OPTIMAL_RANGE);
		final int minOptimalRange = configService.getValue(NodePath.at("/App/Feed/MinOptimalRange"), DEFAULT_MIN_OPTIMAL_RANGE);
		final int maxFrequency = configService.getValue(NodePath.at("/App/Feed/MaxUpdate"), DEFAULT_MAX_FREQUENCY);
		final int minFrequency = configService.getValue(NodePath.at("/App/Feed/MinUpdate"), DEFAULT_MIN_FREQUENCY);

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

	/**
	 * Sets the configuration service.
	 *
	 * @param configurationService the new configuration service
	 */
	public void setConfigurationService(final ConfigurationService configurationService) {
		this.configService = configurationService;
	}
}
