package com.hawkprime.syndicate.util;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Before;
import org.junit.Test;

/**
 * Frequency Calculator Tests.
 */
public class FrequencyCalculatorTest {
	private final FrequencyCalculator frequencyCalculator = new FrequencyCalculator();

	/**
	 * Set-up.
	 */
	@Before
	public void setUp() {
		final int minRange = 70;
		final int maxRange = 80;
		frequencyCalculator.setOptimalRange(minRange, maxRange);

		final int minUpdate = 2880;
		final int maxUpdate = 1;
		frequencyCalculator.setUpdateFrequencyRange(minUpdate, maxUpdate);
	}

	/**
	 * Getters Setters test.
	 */
	@Test
	public void gettersSettersTest() {
		final int minRange = 50;
		final int maxRange = 60;

		frequencyCalculator.setOptimalRange(minRange, maxRange);
		assertThat(frequencyCalculator.getMinOptimalRange(), is(minRange));
		assertThat(frequencyCalculator.getMaxOptimalRange(), is(maxRange));

		final int minUpdate = 60;
		final int maxUpdate = 1;

		frequencyCalculator.setUpdateFrequencyRange(minUpdate, maxUpdate);
		assertThat(frequencyCalculator.getMinUpdateFrequency(), is(minUpdate));
		assertThat(frequencyCalculator.getMaxUpdateFrequency(), is(maxUpdate));
	}

	/**
	 * Too many updates test.
	 */
	@Test
	public void tooManyUpdatesTest() {
		final int percentNew = 48;
		final int currentFrequency = 120;
		final int expectedNewFrequency = 146;

		assertThat(frequencyCalculator.calculateNewFrequency(currentFrequency, percentNew), is(expectedNewFrequency));
	}

	/**
	 * Too few updates test.
	 */
	@Test
	public void tooFewUpdatesTest() {
		final int percentNew = 95;
		final int currentFrequency = 191;
		final int expectedNewFrequency = 163;

		assertThat(frequencyCalculator.calculateNewFrequency(currentFrequency, percentNew), is(expectedNewFrequency));
	}

	/**
	 * Good updates test.
	 */
	@Test
	public void goodUpdatesTest() {
		final int percentNew = 75;
		final int currentFrequency = 191;

		assertThat(frequencyCalculator.calculateNewFrequency(currentFrequency, percentNew), is(currentFrequency));
	}

	/**
	 * Min updates test.
	 */
	@Test
	public void minUpdatesTest() {
		final int percentNew = 1;
		final int currentFrequency = 2200;
		final int expectedNewFrequency = 2880;

		assertThat(frequencyCalculator.calculateNewFrequency(currentFrequency, percentNew), is(expectedNewFrequency));
	}

	/**
	 * Max updates test.
	 */
	@Test
	public void maxUpdatesTest() {
		final int percentNew = 1000;
		final int currentFrequency = 60;
		final int expectedNewFrequency = 1;

		assertThat(frequencyCalculator.calculateNewFrequency(currentFrequency, percentNew), is(expectedNewFrequency));
	}

	/**
	 * Min adjustment too small test.
	 */
	@Test
	public void minAdjustmentTooSmallTest() {
		final int percentNew = 29;
		final int currentFrequency = 2;
		final int expectedFrequency = 3;
		assertThat(frequencyCalculator.calculateNewFrequency(currentFrequency, percentNew), is(expectedFrequency));
	}

	/**
	 * Max adjustment too small test.
	 */
	@Test
	public void maxAdjustmentTooSmallTest() {
		final int percentNew = 81;
		final int currentFrequency = 2;
		final int expectedFrequency = 1;
		assertThat(frequencyCalculator.calculateNewFrequency(currentFrequency, percentNew), is(expectedFrequency));
	}
}
