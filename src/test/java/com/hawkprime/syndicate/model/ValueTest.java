package com.hawkprime.syndicate.model;

import com.hawkprime.syndicate.model.builder.ValueBuilder;
import com.hawkprime.syndicate.util.ValueType;

import java.math.BigDecimal;
import java.util.Date;

import org.joda.time.LocalDateTime;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

/**
 * Value Tests.
 *
 * @version 5.0.0
 * @author Juan D Frias <juandfrias@gmail.com>
 */
public class ValueTest {

	/**
	 * Boolean value test.
	 */
	@Test
	public void booleanValueTest() {
		final Boolean expectedValue = true;
		Value value = new ValueBuilder()
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
		Value value = new ValueBuilder()
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
		Value value = new ValueBuilder()
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
		Value value = new ValueBuilder()
			.withValue(expectedValue)
			.build();
		assertThat((Long) value.getValue(), is(expectedValue));
	}

	/**
	 * String value test.
	 */
	@Test
	public void stringValueTest() {
		final String expectedValue = "MyExpectedValue";
		Value value = new ValueBuilder()
			.withValue(expectedValue)
			.build();
		assertThat((String) value.getValue(), is(expectedValue));
	}

	/**
	 * String set value test.
	 */
	@Test
	public void stringSetValueTest() {
		final String expectedValue = "expectedValue";
		Value value = new Value();
		value.setValue(expectedValue);
		assertThat(value.getType(), is(ValueType.STRING));
		assertThat(value.getString(), is(expectedValue));
	}

	/**
	 * Boolean set value test.
	 */
	@Test
	public void booleanSetValueTest() {
		final Boolean expectedValue = true;
		Value value = new Value();
		value.setValue(expectedValue);
		assertThat(value.getType(), is(ValueType.BOOLEAN));
		assertThat(value.getBoolean(), is(true));
	}

	/**
	 * Integer set value test.
	 */
	@Test
	public void intSetValueTest() {
		final int expectedValue = 42;
		Value value = new Value();
		value.setValue(expectedValue);
		assertThat(value.getType(), is(ValueType.INTEGER));
		assertThat(value.getInteger(), is(expectedValue));
	}

	/**
	 * Long set value test.
	 */
	@Test
	public void longSetValueTest() {
		final long expectedValue = 42L;
		Value value = new Value();
		value.setValue(expectedValue);
		assertThat(value.getType(), is(ValueType.LONG));
		assertThat(value.getLong(), is(expectedValue));
	}

	/**
	 * Double set value test.
	 */
	@Test
	public void doubleSetValueTest() {
		final double expectedValue = 3.1416;
		Value value = new Value();
		value.setValue(expectedValue);
		assertThat(value.getType(), is(ValueType.DECIMAL));
		assertThat(value.getDouble(), is(expectedValue));
	}

	/**
	 * Float set value test.
	 */
	@Test
	public void floatSetValueTest() {
		final float expectedValue = 3.1416f;
		Value value = new Value();
		value.setValue(expectedValue);
		assertThat(value.getType(), is(ValueType.DECIMAL));
		assertThat(value.getFloat(), is(expectedValue));
	}

	/**
	 * Big decimal set value test.
	 */
	@Test
	public void bigDecimalSetValueTest() {
		final BigDecimal expectedValue = new BigDecimal(3.1416);
		Value value = new Value();
		value.setValue(expectedValue);
		assertThat(value.getType(), is(ValueType.DECIMAL));
		assertThat(value.getDecimal(), is(expectedValue));
	}

	/**
	 * Date set value test.
	 */
	@Test
	public void dateSetValueTest() {
		final Date expectedValue = new Date();
		Value value = new Value();
		value.setValue(expectedValue);
		assertThat(value.getType(), is(ValueType.DATE));
		assertThat(value.getDate().toDate(), is(expectedValue));
	}

	/**
	 * Local date time set value test.
	 */
	@Test
	public void localDateTimeSetValueTest() {
		final LocalDateTime expectedValue = LocalDateTime.now();
		Value value = new Value();
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
		Value value = new Value();
		value.setValue(expectedValue);
		value.getString();
	}

	/**
	 * Try to fetch non boolean value test.
	 */
	@Test(expected=IllegalArgumentException.class)
	public void tryToFetchNonBooleanValueTest() {
		final int expectedValue = 42;
		Value value = new Value();
		value.setValue(expectedValue);
		value.getBoolean();
	}

	/**
	 * Try to fetch non long value test.
	 */
	@Test(expected=IllegalArgumentException.class)
	public void tryToFetchNonNumericValueTest() {
		Value value = new Value();
		value.setValue(true);
		value.getLong();
	}

	/**
	 * Try to fetch non integer value test.
	 */
	@Test(expected=IllegalArgumentException.class)
	public void tryToFetchNonIntegerValueTest() {
		Value value = new Value();
		value.setValue(true);
		value.getInteger();
	}

	/**
	 * Try to fetch non decimal value test.
	 */
	@Test(expected=IllegalArgumentException.class)
	public void tryToFetchNonDecimalValueTest() {
		Value value = new Value();
		value.setValue(true);
		value.getDecimal();
	}

	/**
	 * Try to fetch non date value test.
	 */
	@Test(expected=IllegalArgumentException.class)
	public void tryToFetchNonDateValueTest() {
		Value value = new Value();
		value.setValue(true);
		value.getDate();
	}

	/**
	 * Null string test.
	 */
	@Test(expected=NullPointerException.class)
	public void nullStringTest() {
		Value value = new Value();
		final String expectedValue = null;
		value.setValue(expectedValue);
	}

	/**
	 * Null decimal test.
	 */
	@Test(expected=NullPointerException.class)
	public void nullDecimalTest() {
		Value value = new Value();
		final BigDecimal expectedValue = null;
		value.setValue(expectedValue);
	}

	/**
	 * Null date test.
	 */
	@Test(expected=NullPointerException.class)
	public void nullDateTest() {
		Value value = new Value();
		final LocalDateTime expectedValue = null;
		value.setValue(expectedValue);
	}
}
