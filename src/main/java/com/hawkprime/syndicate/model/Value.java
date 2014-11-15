package com.hawkprime.syndicate.model;

import com.hawkprime.syndicate.util.ValueType;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.hibernate.annotations.Type;
import org.joda.time.LocalDateTime;

/**
 * Setting Value.
 *
 * @version 5.0.0
 * @author Juan D Frias <juandfrias@gmail.com>
 */
@Entity
@Table(name="value")
public class Value extends AbstractEntity {
	private static final String CANNOT_GET_VALUE_ERR_MSG = "Cannot get value, value is of type: ";

	@Column(name="value_type", nullable=false)
	@Enumerated(EnumType.STRING)
	private ValueType type;

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

	@ManyToOne
	@JoinColumn(name="node_id", nullable=false)
	private Node node;

	@ManyToOne
	@JoinColumn(name="setting_id", nullable=false)
	private Setting setting;

	/**
	 * Gets the type.
	 *
	 * @return the type
	 */
	public ValueType getType() {
		return type;
	}

	/**
	 * Gets the value.
	 *
	 * @return value
	 */
	public Object getValue() {
		final Object value;
		switch (type) {
		case BOOLEAN:	value = getBoolean();	break;
		case DATE:		value = getDate();		break;
		case DECIMAL:	value = getDecimal();	break;
		case INTEGER:	value = getInteger();	break;
		case LONG:		value = getLong();		break;
		case STRING:	value = getString();	break;
		default:		value = null;			break;
		}
		return value;
	}

	/**
	 * Sets the value.
	 *
	 * @param stringValue the stringValue to set
	 */
	public void setValue(final String stringValue) {
		if (stringValue == null) {
			throw new NullPointerException("String value cannot be null");
		}
		this.stringValue = stringValue;
		booleanValue = null;
		decimalValue = null;
		numericValue = null;
		dateValue = null;
		type = ValueType.STRING;
	}

	/**
	 * Sets the value.
	 *
	 * @param booleanValue the booleanValue to set
	 */
	public void setValue(final boolean booleanValue) {
		this.booleanValue = booleanValue;
		stringValue = null;
		decimalValue = null;
		numericValue = null;
		dateValue = null;
		type = ValueType.BOOLEAN;
	}

	/**
	 * Sets the value.
	 *
	 * @param intValue the intValue to set
	 */
	public void setValue(final int intValue) {
		this.numericValue = (long) intValue;
		stringValue = null;
		decimalValue = null;
		booleanValue = null;
		dateValue = null;
		type = ValueType.INTEGER;
	}

	/**
	 * Sets the value.
	 *
	 * @param longValue the longValue to set
	 */
	public void setValue(final long longValue) {
		this.numericValue = longValue;
		stringValue = null;
		decimalValue = null;
		booleanValue = null;
		dateValue = null;
		type = ValueType.LONG;
	}

	/**
	 * Sets the value.
	 *
	 * @param decimalValue the decimalValue to set
	 */
	public void setValue(final BigDecimal decimalValue) {
		if (decimalValue == null) {
			throw new NullPointerException("Decimal value cannot be null");
		}
		this.decimalValue = decimalValue;
		stringValue = null;
		numericValue = null;
		booleanValue = null;
		dateValue = null;
		type = ValueType.DECIMAL;
	}

	/**
	 * Sets the value.
	 *
	 * @param dateValue the dateValue to set
	 */
	public void setValue(final LocalDateTime dateValue) {
		if (dateValue == null) {
			throw new NullPointerException("Date value cannot be null");
		}
		this.dateValue = dateValue;
		stringValue = null;
		numericValue = null;
		booleanValue = null;
		decimalValue = null;
		type = ValueType.DATE;
	}

	/**
	 * Sets the value.
	 *
	 * @param doubleValue the new value
	 */
	public void setValue(final double doubleValue) {
		setValue(new BigDecimal(doubleValue));
	}

	/**
	 * Sets the value.
	 *
	 * @param floatValue the new value
	 */
	public void setValue(final float floatValue) {
		setValue(new BigDecimal(floatValue));
	}

	/**
	 * Sets the value.
	 *
	 * @param dateValue the dateValue to set
	 */
	public void setValue(final Date dateValue) {
		setValue(new LocalDateTime(dateValue));
	}

	/**
	 * Gets the string.
	 *
	 * @return the stringValue
	 */
	public String getString() {
		if (type != ValueType.STRING) {
			throw new IllegalArgumentException(CANNOT_GET_VALUE_ERR_MSG + type.toString());
		}
		return stringValue;
	}

	/**
	 * Gets the boolean.
	 *
	 * @return the booleanValue
	 */
	public Boolean getBoolean() {
		if (type != ValueType.BOOLEAN) {
			throw new IllegalArgumentException(CANNOT_GET_VALUE_ERR_MSG + type.toString());
		}
		return booleanValue;
	}

	/**
	 * Gets the integer.
	 *
	 * @return the integer value
	 */
	public Integer getInteger() {
		if (type != ValueType.INTEGER) {
			throw new IllegalArgumentException(CANNOT_GET_VALUE_ERR_MSG + type.toString());
		}
		return (int) (long) numericValue;
	}

	/**
	 * Gets the long.
	 *
	 * @return the numericValue
	 */
	public Long getLong() {
		if (type != ValueType.LONG) {
			throw new IllegalArgumentException(CANNOT_GET_VALUE_ERR_MSG + type.toString());
		}
		return numericValue;
	}

	/**
	 * Gets the decimal.
	 *
	 * @return the decimalValue
	 */
	public BigDecimal getDecimal() {
		if (type != ValueType.DECIMAL) {
			throw new IllegalArgumentException(CANNOT_GET_VALUE_ERR_MSG + type.toString());
		}
		return decimalValue;
	}

	/**
	 * Gets the date.
	 *
	 * @return the dateValue
	 */
	public LocalDateTime getDate() {
		if (type != ValueType.DATE) {
			throw new IllegalArgumentException(CANNOT_GET_VALUE_ERR_MSG + type.toString());
		}
		return dateValue;
	}

	/**
	 * Gets the double.
	 *
	 * @return the doubleValue
	 */
	public Double getDouble() {
		return getDecimal().doubleValue();
	}

	/**
	 * Gets the float.
	 *
	 * @return the floatValue
	 */
	public Float getFloat() {
		return getDecimal().floatValue();
	}

	/**
	 * Gets the node.
	 *
	 * @return the node
	 */
	public Node getNode() {
		return node;
	}

	/**
	 * Sets the node.
	 *
	 * @param node the new node
	 */
	public void setNode(final Node node) {
		this.node = node;
	}

	/**
	 * Gets the setting.
	 *
	 * @return the setting
	 */
	public Setting getSetting() {
		return setting;
	}

	/**
	 * Sets the setting.
	 *
	 * @param setting the new setting
	 */
	public void setSetting(final Setting setting) {
		this.setting = setting;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(final Object object) {
		if (object == null || !(object instanceof Value)) {
			return false;
		}
		if (this == object) {
			return true;
		}
		final Value rhs = (Value) object;
		return new EqualsBuilder()
				.append(getId(), rhs.getId())
				.append(stringValue, rhs.stringValue)
				.append(booleanValue, rhs.booleanValue)
				.append(numericValue, rhs.numericValue)
				.append(decimalValue, rhs.decimalValue)
				.append(dateValue, rhs.dateValue)
				.append(node, rhs.node)
				.append(setting, rhs.setting)
				.isEquals();
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		return new HashCodeBuilder()
				.append(getId())
				.append(stringValue)
				.append(booleanValue)
				.append(numericValue)
				.append(decimalValue)
				.append(dateValue)
				.append(node)
				.append(setting)
				.toHashCode();
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return new ToStringBuilder(this)
				.append("id", getId())
				.append("node", node.getPath())
				.append("setting", setting.getName())
				.append("type", type)
				.append("value", getValue())
				.toString();
	}
}
