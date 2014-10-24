package com.hawkprime.syndicate.util;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Before;
import org.junit.Test;

/**
 * Frequency Calculator Tests.
 */
public class FrequencyCalculatorTest {

	@Before
	public void setUp() {
		final int minRange = 70;
		final int maxRange = 80;
		FrequencyCalculator.setOptimalRange(minRange, maxRange);

		final int minUpdate = 2880;
		final int maxUpdate = 1;
		FrequencyCalculator.setUpdateFrequencyRange(minUpdate, maxUpdate);
	}

	@Test
	public void gettersSettersTest() {
		final int minRange = 50;
		final int maxRange = 60;

		FrequencyCalculator.setOptimalRange(minRange, maxRange);
		assertThat(FrequencyCalculator.getMinOptimalRange(), is(minRange));
		assertThat(FrequencyCalculator.getMaxOptimalRange(), is(maxRange));

		final int minUpdate = 60;
		final int maxUpdate = 1;

		FrequencyCalculator.setUpdateFrequencyRange(minUpdate, maxUpdate);
		assertThat(FrequencyCalculator.getMinUpdateFrequency(), is(minUpdate));
		assertThat(FrequencyCalculator.getMaxUpdateFrequency(), is(maxUpdate));
	}

	@Test
	public void tooManyUpdates() {
		final int percentNew = 48;
		final int currentFrequency = 120;
		final int expectedNewFrequency = 146;

		assertThat(FrequencyCalculator.calculateNewFrequency(currentFrequency, percentNew), is(expectedNewFrequency));
	}

	@Test
	public void tooFewUpdates() {
		final int percentNew = 95;
		final int currentFrequency = 191;
		final int expectedNewFrequency = 163;

		assertThat(FrequencyCalculator.calculateNewFrequency(currentFrequency, percentNew), is(expectedNewFrequency));
	}

	@Test
	public void goodUpdates() {
		final int percentNew = 75;
		final int currentFrequency = 191;

		assertThat(FrequencyCalculator.calculateNewFrequency(currentFrequency, percentNew), is(currentFrequency));
	}

	@Test
	public void minUpdates() {
		final int percentNew = 1;
		final int currentFrequency = 2200;
		final int expectedNewFrequency = 2880;

		assertThat(FrequencyCalculator.calculateNewFrequency(currentFrequency, percentNew), is(expectedNewFrequency));
	}

	@Test
	public void maxUpdates() {
		final int percentNew = 1000;
		final int currentFrequency = 60;
		final int expectedNewFrequency = 1;

		assertThat(FrequencyCalculator.calculateNewFrequency(currentFrequency, percentNew), is(expectedNewFrequency));
	}

	@Test
	public void minAdjustmentTooSmall() {
		final int percentNew = 29;
		final int currentFrequency = 2;
		final int expectedFrequency = 3;
		assertThat(FrequencyCalculator.calculateNewFrequency(currentFrequency, percentNew), is(expectedFrequency));
	}

	@Test
	public void maxAdjustmentTooSmall() {
		final int percentNew = 81;
		final int currentFrequency = 2;
		final int expectedFrequency = 1;
		assertThat(FrequencyCalculator.calculateNewFrequency(currentFrequency, percentNew), is(expectedFrequency));
	}
}
