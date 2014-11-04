package com.hawkprime.syndicate.model;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.Type;
import org.jdom.IllegalDataException;
import org.joda.time.LocalDateTime;

import com.hawkprime.syndicate.util.ConfigType;

/**
 * Configuration Value.
 */
@Entity
@Table(name="config")
public class Config {
	@Id
	@GeneratedValue
	@Column(name="config_id")
	private Long id;

	@Column(name="section", nullable=false)
	private String section;

	@Column(name="key", nullable=false)
	private String key;

	@Column(name="value_type", nullable=false)
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

	@Column(name="reference_id")
	private Long referenceId;

	/**
	 * Default Constructor.
	 */
	public Config() {
		/* Empty */
	}

	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(final Long id) {
		this.id = id;
	}

	/**
	 * @return the section
	 */
	public String getSection() {
		return section;
	}

	/**
	 * @param section the section to set
	 */
	public void setSection(final String section) {
		if (section == null || section.isEmpty() || section.trim().isEmpty()) {
			throw new NullPointerException("Config section cannot be null");
		}
		this.section = section;
	}

	/**
	 * @return the key
	 */
	public String getKey() {
		return key;
	}

	/**
	 * @param key the key to set
	 */
	public void setKey(final String key) {
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
	 * @return Configuration value
	 */
	public Object getValue() {
		switch (type) {
		case BOOLEAN:	return getBooleanValue();
		case DATE:		return getDateValue();
		case DECIMAL:	return getDecimalValue();
		case NUMERIC:	return getNumericValue();
		case STRING:	return getStringValue();
		default:		return null;
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
	public void setValue(final String stringValue) {
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
	public void setValue(final boolean booleanValue) {
		this.booleanValue = booleanValue;
		stringValue = null;
		decimalValue = null;
		numericValue = null;
		dateValue = null;
		type = ConfigType.BOOLEAN;
	}

	/**
	 * @return the integer value
	 */
	public Integer getIntegerValue() {
		if (type != ConfigType.NUMERIC) {
			throw new IllegalArgumentException("Cannot get Config value, value is of type: " + type.toString());
		}
		if (numericValue > Integer.MAX_VALUE || numericValue < Integer.MIN_VALUE) {
			throw new IllegalDataException("Value cannot be represented as an integer.");
		}
		return (int) (long) numericValue;
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
	public void setValue(final long numericValue) {
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
	 * @param doubleValue the new value
	 */
	public void setValue(final double doubleValue) {
		setValue(new BigDecimal(doubleValue));
	}

	/**
	 * @return the floatValue
	 */
	public Float getFloatValue() {
		return getDecimalValue().floatValue();
	}

	/**
	 * @param floatValue the new value
	 */
	public void setValue(final float floatValue) {
		setValue(new BigDecimal(floatValue));
	}

	/**
	 * @param decimalValue the decimalValue to set
	 */
	public void setValue(final BigDecimal decimalValue) {
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
	public void setValue(final Date dateValue) {
		setValue(new LocalDateTime(dateValue));
	}

	/**
	 * @param dateValue the dateValue to set
	 */
	public void setValue(final LocalDateTime dateValue) {
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

	/**
	 * @return the referenceId
	 */
	public Long getReferenceId() {
		return referenceId;
	}

	/**
	 * @param referenceId the referenceId to set
	 */
	public void setReferenceId(final Long referenceId) {
		this.referenceId = referenceId;
	}
}
