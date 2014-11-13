package com.hawkprime.syndicate.model;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.hibernate.annotations.Type;
import org.joda.time.LocalDateTime;

import com.hawkprime.syndicate.util.ValueType;

/**
 * Setting Value.
 */
@Entity
@Table(name="value")
public class Value {

	@Id
	@GeneratedValue
	private Long id;

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
	 * Gets the id.
	 *
	 * @return the id
	 */
	public Long getId() {
		return id;
	}

	/**
	 * Sets the id.
	 *
	 * @param id the new id
	 */
	public void setId(final Long id) {
		this.id = id;
	}

	/**
	 * @return the type
	 */
	public ValueType getType() {
		return type;
	}

	/**
	 * @return value
	 */
	public Object getValue() {
		switch (type) {
		case BOOLEAN:	return getBoolean();
		case DATE:		return getDate();
		case DECIMAL:	return getDecimal();
		case INTEGER:	return getInteger();
		case LONG:		return getLong();
		case STRING:	return getString();
		default:		return null;
		}
	}

	/**
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
	 * @param doubleValue the new value
	 */
	public void setValue(final double doubleValue) {
		setValue(new BigDecimal(doubleValue));
	}

	/**
	 * @param floatValue the new value
	 */
	public void setValue(final float floatValue) {
		setValue(new BigDecimal(floatValue));
	}

	/**
	 * @param dateValue the dateValue to set
	 */
	public void setValue(final Date dateValue) {
		setValue(new LocalDateTime(dateValue));
	}

	/**
	 * @return the stringValue
	 */
	public String getString() {
		if (type != ValueType.STRING) {
			throw new IllegalArgumentException("Cannot get value, value is of type: " + type.toString());
		}
		return stringValue;
	}

	/**
	 * @return the booleanValue
	 */
	public Boolean getBoolean() {
		if (type != ValueType.BOOLEAN) {
			throw new IllegalArgumentException("Cannot get value, value is of type: " + type.toString());
		}
		return booleanValue;
	}
	/**
	 * @return the integer value
	 */
	public Integer getInteger() {
		if (type != ValueType.INTEGER) {
			throw new IllegalArgumentException("Cannot get value, value is of type: " + type.toString());
		}
		return (int) (long) numericValue;
	}

	/**
	 * @return the numericValue
	 */
	public Long getLong() {
		if (type != ValueType.LONG) {
			throw new IllegalArgumentException("Cannot get value, value is of type: " + type.toString());
		}
		return numericValue;
	}

	/**
	 * @return the decimalValue
	 */
	public BigDecimal getDecimal() {
		if (type != ValueType.DECIMAL) {
			throw new IllegalArgumentException("Cannot get value, value is of type: " + type.toString());
		}
		return decimalValue;
	}

	/**
	 * @return the dateValue
	 */
	public LocalDateTime getDate() {
		if (type != ValueType.DATE) {
			throw new IllegalArgumentException("Cannot get value, value is of type: " + type.toString());
		}
		return dateValue;
	}

	/**
	 * @return the doubleValue
	 */
	public Double getDouble() {
		return getDecimal().doubleValue();
	}

	/**
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
				.append(id, rhs.id)
				.append(stringValue, rhs.stringValue)
				.append(booleanValue, rhs.booleanValue)
				.append(numericValue, rhs.numericValue)
				.append(decimalValue, rhs.decimalValue)
				.append(dateValue, rhs.dateValue)
				.append(node, rhs.node)
				.append(setting, rhs.setting)
				.isEquals();
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder()
				.append(id)
				.append(stringValue)
				.append(booleanValue)
				.append(numericValue)
				.append(decimalValue)
				.append(dateValue)
				.append(node)
				.append(setting)
				.toHashCode();
	}

	@Override
	public String toString() {
		return new ToStringBuilder(this)
				.append("id", id)
				.append("node", node.getPath())
				.append("setting", setting.getName())
				.append("type", type)
				.append("value", getValue())
				.toString();
	}
}
