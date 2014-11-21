package com.hawkprime.syndicate.model.builder;

import com.hawkprime.syndicate.model.Node;
import com.hawkprime.syndicate.model.Setting;
import com.hawkprime.syndicate.model.Value;

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
	 * @param <T> the value type
	 * @param aValue the a value
	 * @return the value builder
	 */
	public <T> ValueBuilder withValue(final T aValue) {
		value.setValue(aValue);
		return this;
	}
}
