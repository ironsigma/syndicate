package com.hawkprime.syndicate.model.builder;

import com.hawkprime.syndicate.model.Node;
import com.hawkprime.syndicate.model.Setting;
import com.hawkprime.syndicate.model.Value;

import java.math.BigDecimal;
import java.util.Date;

import org.joda.time.LocalDateTime;

/**
 * The Class ValueBuilder.
 *
 * @version 5.0.0
 * @author Juan D Frias <juandfrias@gmail.com>
 */
public class ValueBuilder {
	private final Value value = new Value();

	/**
	 * Default values.
	 */
	public ValueBuilder() {
		value.setValue("MyValue");
	}

	/**
	 * Gets the value object.
	 *
	 * @return value object
	 */
	public Value build() {
		return value;
	}

	/**
	 * With id.
	 *
	 * @param aValue the a value
	 * @return the value builder
	 */
	public ValueBuilder withId(final Long aValue) {
		value.setId(aValue);
		return this;
	}

	/**
	 * With setting.
	 * @param setting setting.
	 * @return the value builder
	 */
	public ValueBuilder withSetting(final Setting setting) {
		value.setSetting(setting);
		return this;
	}

	/**
	 * With Node.
	 * @param node node.
	 * @return the value builder
	 */
	public ValueBuilder withNode(final Node node) {
		value.setNode(node);
		return this;
	}

	/**
	 * With value.
	 *
	 * @param aValue the a value
	 * @return the value builder
	 */
	public ValueBuilder withValue(final String aValue) {
		value.setValue(aValue);
		return this;
	}

	/**
	 * With value.
	 *
	 * @param aValue the a value
	 * @return the value builder
	 */
	public ValueBuilder withValue(final boolean aValue) {
		value.setValue(aValue);
		return this;
	}

	/**
	 * With value.
	 *
	 * @param aValue the a value
	 * @return the value builder
	 */
	public ValueBuilder withValue(final int aValue) {
		value.setValue(aValue);
		return this;
	}

	/**
	 * With value.
	 *
	 * @param aValue the a value
	 * @return the value builder
	 */
	public ValueBuilder withValue(final long aValue) {
		value.setValue(aValue);
		return this;
	}

	/**
	 * With value.
	 *
	 * @param aValue the a value
	 * @return the value builder
	 */
	public ValueBuilder withValue(final double aValue) {
		value.setValue(aValue);
		return this;
	}

	/**
	 * With value.
	 *
	 * @param aValue the a value
	 * @return the value builder
	 */
	public ValueBuilder withValue(final float aValue) {
		value.setValue(aValue);
		return this;
	}

	/**
	 * With value.
	 *
	 * @param aValue the a value
	 * @return the value builder
	 */
	public ValueBuilder withValue(final BigDecimal aValue) {
		value.setValue(aValue);
		return this;
	}

	/**
	 * With value.
	 *
	 * @param aValue the a value
	 * @return the value builder
	 */
	public ValueBuilder withValue(final Date aValue) {
		value.setValue(aValue);
		return this;
	}

	/**
	 * With value.
	 *
	 * @param aValue the a value
	 * @return the value builder
	 */
	public ValueBuilder withValue(final LocalDateTime aValue) {
		value.setValue(aValue);
		return this;
	}
}
