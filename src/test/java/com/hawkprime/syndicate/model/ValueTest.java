package com.hawkprime.syndicate.model;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.math.BigDecimal;
import java.util.Date;

import org.joda.time.LocalDateTime;
import org.junit.Test;

import com.hawkprime.syndicate.model.builder.ValueBuilder;
import com.hawkprime.syndicate.util.ValueType;

/**
 * Value Tests.
 */
public class ValueTest {

	/**
	 * Boolean value test.
	 */
	@Test
	public void booleanValueTest() {
		final Boolean expectedValue = true;
		final Value value = new ValueBuilder()
			.withValue(expectedValue)
			.build();
		assertThat((Boolean) value.getValue(), is(expectedValue));
	}

	/**
	 * Date value test.
	 */
	@Test
	public void dateValueTest() {
		final LocalDateTime expectedValue = LocalDateTime.now();
		final Value value = new ValueBuilder()
			.withValue(expectedValue)
			.build();
		assertThat((LocalDateTime) value.getValue(), is(expectedValue));
	}

	/**
	 * Decimal value test.
	 */
	@Test
	public void decimalValueTest() {
		final BigDecimal expectedValue = new BigDecimal(3.1416);
		final Value value = new ValueBuilder()
			.withValue(expectedValue)
			.build();
		assertThat((BigDecimal) value.getValue(), is(expectedValue));
	}

	/**
	 * Numeric value test.
	 */
	@Test
	public void numericValueTest() {
		final Long expectedValue = 42L;
		final Value value = new ValueBuilder()
			.withValue(expectedValue)
			.build();
		assertThat((Long) value.getValue(), is(expectedValue));
	}

	/**
	 * String value test.
	 */
	@Test
	public void stringValueTest() {
		final String expectedValue = "expectedValue";
		final Value value = new ValueBuilder()
			.withValue(expectedValue)
			.build();
		assertThat((String) value.getValue(), is(expectedValue));
	}

	/**
	 * String constructors test.
	 */
	@Test
	public void stringConstructorsTest() {
		final String expectedValue = "expectedValue";
		final Value value = new Value();
		value.setValue(expectedValue);
		assertThat(value.getType(), is(ValueType.STRING));
		assertThat(value.getString(), is(expectedValue));
	}

	/**
	 * Boolean constructors test.
	 */
	@Test
	public void booleanConstructorsTest() {
		final Boolean expectedValue = true;
		final Value value = new Value();
		value.setValue(expectedValue);
		assertThat(value.getType(), is(ValueType.BOOLEAN));
		assertThat(value.getBoolean(), is(true));
	}

	/**
	 * Integer constructors test.
	 */
	@Test
	public void intConstructorsTest() {
		final int expectedValue = 42;
		final Value value = new Value();
		value.setValue(expectedValue);
		assertThat(value.getType(), is(ValueType.INTEGER));
		assertThat(value.getInteger(), is(expectedValue));
	}

	/**
	 * Long constructors test.
	 */
	@Test
	public void longConstructorsTest() {
		final long expectedValue = 42;
		final Value value = new Value();
		value.setValue(expectedValue);
		assertThat(value.getType(), is(ValueType.LONG));
		assertThat(value.getLong(), is(expectedValue));
	}

	/**
	 * Double constructors test.
	 */
	@Test
	public void doubleConstructorsTest() {
		final double expectedValue = 3.1416;
		final Value value = new Value();
		value.setValue(expectedValue);
		assertThat(value.getType(), is(ValueType.DECIMAL));
		assertThat(value.getDouble(), is(expectedValue));
	}

	/**
	 * Float constructors test.
	 */
	@Test
	public void floatConstructorsTest() {
		final float expectedValue = 3.1416f;
		final Value value = new Value();
		value.setValue(expectedValue);
		assertThat(value.getType(), is(ValueType.DECIMAL));
		assertThat(value.getFloat(), is(expectedValue));
	}

	/**
	 * Big decimal constructors test.
	 */
	@Test
	public void bigDecimalConstructorsTest() {
		final BigDecimal expectedValue = new BigDecimal(3.1416);
		final Value value = new Value();
		value.setValue(expectedValue);
		assertThat(value.getType(), is(ValueType.DECIMAL));
		assertThat(value.getDecimal(), is(expectedValue));
	}

	/**
	 * Date constructors test.
	 */
	@Test
	public void dateConstructorsTest() {
		final Date expectedValue = new Date();
		final Value value = new Value();
		value.setValue(expectedValue);
		assertThat(value.getType(), is(ValueType.DATE));
		assertThat(value.getDate().toDate(), is(expectedValue));
	}

	/**
	 * Local date time constructors test.
	 */
	@Test
	public void localDateTimeConstructorsTest() {
		final LocalDateTime expectedValue = LocalDateTime.now();
		final Value value = new Value();
		value.setValue(expectedValue);
		assertThat(value.getType(), is(ValueType.DATE));
		assertThat(value.getDate(), is(expectedValue));
	}

	/**
	 * Try to fetch non string value test.
	 */
	@Test(expected=IllegalArgumentException.class)
	public void tryToFetchNonStringValueTest() {
		final int expectedValue = 42;
		final Value value = new Value();
		value.setValue(expectedValue);
		value.getString();
	}

	/**
	 * Try to fetch non boolean value test.
	 */
	@Test(expected=IllegalArgumentException.class)
	public void tryToFetchNonBooleanValueTest() {
		final int expectedValue = 42;
		final Value value = new Value();
		value.setValue(expectedValue);
		value.getBoolean();
	}

	/**
	 * Try to fetch non long value test.
	 */
	@Test(expected=IllegalArgumentException.class)
	public void tryToFetchNonNumericValueTest() {
		final Value value = new Value();
		value.setValue(true);
		value.getLong();
	}

	/**
	 * Try to fetch non integer value test.
	 */
	@Test(expected=IllegalArgumentException.class)
	public void tryToFetchNonIntegerValueTest() {
		final Value value = new Value();
		value.setValue(true);
		value.getInteger();
	}

	/**
	 * Try to fetch non decimal value test.
	 */
	@Test(expected=IllegalArgumentException.class)
	public void tryToFetchNonDecimalValueTest() {
		final Value value = new Value();
		value.setValue(true);
		value.getDecimal();
	}

	/**
	 * Try to fetch non date value test.
	 */
	@Test(expected=IllegalArgumentException.class)
	public void tryToFetchNonDateValueTest() {
		final Value value = new Value();
		value.setValue(true);
		value.getDate();
	}

	/**
	 * Null string test.
	 */
	@Test(expected=NullPointerException.class)
	public void nullStringTest() {
		final Value value = new Value();
		final String expectedValue = null;
		value.setValue(expectedValue);
	}

	/**
	 * Null decimal test.
	 */
	@Test(expected=NullPointerException.class)
	public void nullDecimalTest() {
		final Value value = new Value();
		final BigDecimal expectedValue = null;
		value.setValue(expectedValue);
	}

	/**
	 * Null date test.
	 */
	@Test(expected=NullPointerException.class)
	public void nullDateTest() {
		final Value value = new Value();
		final LocalDateTime expectedValue = null;
		value.setValue(expectedValue);
	}
}