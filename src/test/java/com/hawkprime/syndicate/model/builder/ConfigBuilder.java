package com.hawkprime.syndicate.model.builder;

import java.math.BigDecimal;
import java.util.Date;

import org.joda.time.LocalDateTime;

import com.hawkprime.syndicate.model.Config;

/**
 * The Class ConfigBuilder.
 */
public class ConfigBuilder {
	private final Config instance = new Config();

	/**
	 * Default values.
	 */
	public ConfigBuilder() {
		instance.setId(null);
		instance.setSection("MySection");
		instance.setKey("MyKey");
		instance.setValue("MyValue");
	}

	/**
	 * Gets the single instance of ConfigBuilder.
	 *
	 * @return single instance of ConfigBuilder
	 */
	public Config build() {
		return instance;
	}

	/**
	 * With id.
	 *
	 * @param aValue the a value
	 * @return the configuration builder
	 */
	public ConfigBuilder withId(final Long aValue) {
		instance.setId(aValue);
		return this;
	}

	/**
	 * With section.
	 *
	 * @param aValue the a value
	 * @return the configuration builder
	 */
	public ConfigBuilder withSection(final String aValue) {
		instance.setSection(aValue);

		return this;
	}

	/**
	 * With key.
	 *
	 * @param aValue the a value
	 * @return the configuration builder
	 */
	public ConfigBuilder withKey(final String aValue) {
		instance.setKey(aValue);
		return this;
	}

	/**
	 * With value.
	 *
	 * @param aValue the a value
	 * @return the configuration builder
	 */
	public ConfigBuilder withValue(final String aValue) {
		instance.setValue(aValue);
		return this;
	}

	/**
	 * With value.
	 *
	 * @param aValue the a value
	 * @return the configuration builder
	 */
	public ConfigBuilder withValue(final boolean aValue) {
		instance.setValue(aValue);
		return this;
	}

	/**
	 * With value.
	 *
	 * @param aValue the a value
	 * @return the configuration builder
	 */
	public ConfigBuilder withValue(final long aValue) {
		instance.setValue(aValue);
		return this;
	}

	/**
	 * With value.
	 *
	 * @param aValue the a value
	 * @return the configuration builder
	 */
	public ConfigBuilder withValue(final double aValue) {
		instance.setValue(aValue);
		return this;
	}

	/**
	 * With value.
	 *
	 * @param aValue the a value
	 * @return the configuration builder
	 */
	public ConfigBuilder withValue(final float aValue) {
		instance.setValue(aValue);
		return this;
	}

	/**
	 * With value.
	 *
	 * @param aValue the a value
	 * @return the configuration builder
	 */
	public ConfigBuilder withValue(final BigDecimal aValue) {
		instance.setValue(aValue);
		return this;
	}

	/**
	 * With value.
	 *
	 * @param aValue the a value
	 * @return the configuration builder
	 */
	public ConfigBuilder withValue(final Date aValue) {
		instance.setValue(aValue);
		return this;
	}

	/**
	 * With value.
	 *
	 * @param aValue the a value
	 * @return the configuration builder
	 */
	public ConfigBuilder withValue(final LocalDateTime aValue) {
		instance.setValue(aValue);
		return this;
	}

	/**
	 * With reference id.
	 *
	 * @param aValue the a value
	 * @return the configuration builder
	 */
	public ConfigBuilder withReferenceId(final Long aValue) {
		instance.setReferenceId(aValue);
		return this;
	}
}
