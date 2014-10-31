package com.hawkprime.syndicate.model;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.Type;
import org.joda.time.LocalDateTime;

import com.hawkprime.syndicate.util.ConfigType;

/**
 * Configuration Value.
 */
@Entity
@Table(name="config")
public class Config {
	@Id
	@Column(name="config_id")
	private String key;

	@Column(nullable=false, name="value_type")
	@Enumerated(EnumType.STRING)
	private ConfigType type;

	@Column(name="string_value")
	private String stringValue;

	@Column(name="boolean_value")
	private Boolean booleanValue;

	@Column(name="numeric_value")
	private Long numericValue;

	@Column(name="decimal_value")
	private BigDecimal decimalValue;

	@Column(name="date_value")
	@Type(type="org.jadira.usertype.dateandtime.joda.PersistentLocalDateTime")
	private LocalDateTime dateValue;

	/**
	 * Default constructor.
	 * Needed for JPA hydration
	 */
	@SuppressWarnings("unused")
	private Config() {
		/* empty */
	}

	/**
	 * Create string value.
	 * @param key Key
	 * @param value Value
	 */
	public Config(final String key, final String value) {
		setId(key);
		setStringValue(value);
	}

	/**
	 * Create boolean value.
	 * @param key Key
	 * @param value Value
	 */
	public Config(final String key, final boolean value) {
		setId(key);
		setBooleanValue(value);
	}

	/**
	 * Create int value.
	 * @param key Key
	 * @param value Value
	 */
	public Config(final String key, final int value) {
		setId(key);
		setNumericValue(new Long(value));
	}

	/**
	 * Create long value.
	 * @param key Key
	 * @param value Value
	 */
	public Config(final String key, final long value) {
		setId(key);
		setNumericValue(value);
	}

	/**
	 * Create double value.
	 * @param key Key
	 * @param value Value
	 */
	public Config(final String key, final double value) {
		setId(key);
		setDecimalValue(new BigDecimal(value));
	}

	/**
	 * Create float value.
	 * @param key Key
	 * @param value Value
	 */
	public Config(final String key, final float value) {
		setId(key);
		setDecimalValue(new BigDecimal(value));
	}

	/**
	 * Create big decimal value.
	 * @param key Key
	 * @param value Value
	 */
	public Config(final String key, final BigDecimal value) {
		setId(key);
		setDecimalValue(value);
	}

	/**
	 * Create date value.
	 * @param key Key
	 * @param value Value
	 */
	public Config(final String key, final Date value) {
		setId(key);
		setDateValue(new LocalDateTime(value));
	}

	/**
	 * Create local date time value.
	 * @param key Key
	 * @param value Value
	 */
	public Config(final String key, final LocalDateTime value) {
		setId(key);
		setDateValue(value);
	}

	/**
	 * @return the key
	 */
	public String getId() {
		return key;
	}

	/**
	 * @param key the key to set
	 */
	private void setId(final String key) {
		if (key == null || key.isEmpty() || key.trim().isEmpty()) {
			throw new NullPointerException("Config key cannot be null");
		}
		this.key = key;
	}

	/**
	 * @return the type
	 */
	public ConfigType getType() {
		return type;
	}

	/**
	 * Get value.
	 * @return object value
	 */
	public Object getValue() {
		switch (type) {
		case BOOLEAN:
			return getBooleanValue();
		case DATE:
			return getDateValue();
		case DECIMAL:
			return getDecimalValue();
		case NUMERIC:
			return getNumericValue();
		case STRING:
			return getStringValue();
		default:
			throw new IllegalStateException("Unknown config type: " + type.toString());
		}
	}

	/**
	 * @return the stringValue
	 */
	public String getStringValue() {
		if (type != ConfigType.STRING) {
			throw new IllegalArgumentException("Cannot get Config value, value is of type: " + type.toString());
		}
		return stringValue;
	}

	/**
	 * @param stringValue the stringValue to set
	 */
	public void setStringValue(final String stringValue) {
		if (stringValue == null) {
			throw new NullPointerException("Config string value cannot be null");
		}
		this.stringValue = stringValue;
		booleanValue = null;
		decimalValue = null;
		numericValue = null;
		dateValue = null;
		type = ConfigType.STRING;
	}

	/**
	 * @return the booleanValue
	 */
	public Boolean getBooleanValue() {
		if (type != ConfigType.BOOLEAN) {
			throw new IllegalArgumentException("Cannot get Config value, value is of type: " + type.toString());
		}
		return booleanValue;
	}

	/**
	 * @param booleanValue the booleanValue to set
	 */
	public void setBooleanValue(final boolean booleanValue) {
		this.booleanValue = booleanValue;
		stringValue = null;
		decimalValue = null;
		numericValue = null;
		dateValue = null;
		type = ConfigType.BOOLEAN;
	}

	/**
	 * @return the numericValue
	 */
	public Long getNumericValue() {
		if (type != ConfigType.NUMERIC) {
			throw new IllegalArgumentException("Cannot get Config value, value is of type: " + type.toString());
		}
		return numericValue;
	}

	/**
	 * @param numericValue the numericValue to set
	 */
	public void setNumericValue(final long numericValue) {
		this.numericValue = numericValue;
		stringValue = null;
		decimalValue = null;
		booleanValue = null;
		dateValue = null;
		type = ConfigType.NUMERIC;
	}

	/**
	 * @return the decimalValue
	 */
	public BigDecimal getDecimalValue() {
		if (type != ConfigType.DECIMAL) {
			throw new IllegalArgumentException("Cannot get Config value, value is of type: " + type.toString());
		}
		return decimalValue;
	}

	/**
	 * @return the doubleValue
	 */
	public Double getDoubleValue() {
		return getDecimalValue().doubleValue();
	}

	/**
	 * @return the floatValue
	 */
	public Float getFloatValue() {
		return getDecimalValue().floatValue();
	}

	/**
	 * @param decimalValue the decimalValue to set
	 */
	public void setDecimalValue(final BigDecimal decimalValue) {
		if (decimalValue == null) {
			throw new NullPointerException("Config decimal value cannot be null");
		}
		this.decimalValue = decimalValue;
		stringValue = null;
		numericValue = null;
		booleanValue = null;
		dateValue = null;
		type = ConfigType.DECIMAL;
	}

	/**
	 * @return the dateValue
	 */
	public LocalDateTime getDateValue() {
		if (type != ConfigType.DATE) {
			throw new IllegalArgumentException("Cannot get Config value, value is of type: " + type.toString());
		}
		return dateValue;
	}

	/**
	 * @param dateValue the dateValue to set
	 */
	public void setDateValue(final LocalDateTime dateValue) {
		if (dateValue == null) {
			throw new NullPointerException("Config date value cannot be null");
		}
		this.dateValue = dateValue;
		stringValue = null;
		numericValue = null;
		booleanValue = null;
		decimalValue = null;
		type = ConfigType.DATE;
	}
}
