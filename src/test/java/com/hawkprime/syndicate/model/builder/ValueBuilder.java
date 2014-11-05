package com.hawkprime.syndicate.model.builder;

import java.math.BigDecimal;
import java.util.Date;

import org.joda.time.LocalDateTime;

import com.hawkprime.syndicate.model.Value;

/**
 * The Class ValueBuilder.
 */
public class ValueBuilder {
	private final Value instance = new Value();

	/**
	 * Default values.
	 */
	public ValueBuilder() {
		instance.setId(null);
		instance.setValue("MyValue");
	}

	/**
	 * Gets the value object.
	 *
	 * @return value object
	 */
	public Value build() {
		return instance;
	}

	/**
	 * With id.
	 *
	 * @param aValue the a value
	 * @return the value builder
	 */
	public ValueBuilder withId(final Long aValue) {
		instance.setId(aValue);
		return this;
	}

	/**
	 * With value.
	 *
	 * @param aValue the a value
	 * @return the value builder
	 */
	public ValueBuilder withValue(final String aValue) {
		instance.setValue(aValue);
		return this;
	}

	/**
	 * With value.
	 *
	 * @param aValue the a value
	 * @return the value builder
	 */
	public ValueBuilder withValue(final boolean aValue) {
		instance.setValue(aValue);
		return this;
	}

	/**
	 * With value.
	 *
	 * @param aValue the a value
	 * @return the value builder
	 */
	public ValueBuilder withValue(final int aValue) {
		instance.setValue(aValue);
		return this;
	}

	/**
	 * With value.
	 *
	 * @param aValue the a value
	 * @return the value builder
	 */
	public ValueBuilder withValue(final long aValue) {
		instance.setValue(aValue);
		return this;
	}

	/**
	 * With value.
	 *
	 * @param aValue the a value
	 * @return the value builder
	 */
	public ValueBuilder withValue(final double aValue) {
		instance.setValue(aValue);
		return this;
	}

	/**
	 * With value.
	 *
	 * @param aValue the a value
	 * @return the value builder
	 */
	public ValueBuilder withValue(final float aValue) {
		instance.setValue(aValue);
		return this;
	}

	/**
	 * With value.
	 *
	 * @param aValue the a value
	 * @return the value builder
	 */
	public ValueBuilder withValue(final BigDecimal aValue) {
		instance.setValue(aValue);
		return this;
	}

	/**
	 * With value.
	 *
	 * @param aValue the a value
	 * @return the value builder
	 */
	public ValueBuilder withValue(final Date aValue) {
		instance.setValue(aValue);
		return this;
	}

	/**
	 * With value.
	 *
	 * @param aValue the a value
	 * @return the value builder
	 */
	public ValueBuilder withValue(final LocalDateTime aValue) {
		instance.setValue(aValue);
		return this;
	}
}
