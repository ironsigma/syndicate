package com.hawkprime.syndicate.model;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.Table;

import org.apache.commons.lang3.NotImplementedException;
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
	private String id;

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

	@SuppressWarnings("unused")
	private Config() {
		/* empty */
	}

	public Config(final String id, final String value) {
		setId(id);
		setStringValue(value);
	}

	public Config(final String id, final boolean value) {
		setId(id);
		setBooleanValue(value);
	}

	public Config(final String id, final int value) {
		setId(id);
		setNumericValue(new Long(value));
	}

	public Config(final String id, final long value) {
		setId(id);
		setNumericValue(value);
	}

	public Config(final String id, final double value) {
		setId(id);
		setDecimalValue(new BigDecimal(value));
	}

	public Config(final String id, final float value) {
		setId(id);
		setDecimalValue(new BigDecimal(value));
	}

	public Config(final String id, final Date value) {
		setId(id);
		setDateValue(new LocalDateTime(value));
	}

	public Config(final String id, final LocalDateTime value) {
		setId(id);
		setDateValue(value);
	}

	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	private void setId(final String id) {
		if (id == null) {
			throw new NullPointerException("Config id cannot be null");
		}
		this.id = id;
	}

	/**
	 * @return the type
	 */
	public ConfigType getType() {
		return type;
	}

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
			throw new NotImplementedException("Unknown config type: " + type.toString());
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
