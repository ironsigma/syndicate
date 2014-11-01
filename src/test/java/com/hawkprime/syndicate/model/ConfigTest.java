package com.hawkprime.syndicate.model;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.math.BigDecimal;
import java.util.Date;

import org.joda.time.LocalDateTime;
import org.junit.Test;

import com.hawkprime.syndicate.util.ConfigType;

/**
 * Configuration Tests.
 */
public class ConfigTest {

	/**
	 * Null key test.
	 */
	@Test(expected=NullPointerException.class)
	public void nullKeyTest() {
		new Config(null, "value");
	}

	/**
	 * Empty key test.
	 */
	@Test(expected=NullPointerException.class)
	public void emptyKeyTest() {
		new Config("", "value");
	}

	/**
	 * Blank key test.
	 */
	@Test(expected=NullPointerException.class)
	public void blankKeyTest() {
		new Config("    ", "value");
	}

	/**
	 * String constructors test.
	 */
	@Test
	public void stringConstructorsTest() {
		final String value = "value";
		final Config config = new Config("key", value);
		assertThat(config.getType(), is(ConfigType.STRING));
		assertThat(config.getStringValue(), is(value));
	}

	/**
	 * Boolean constructors test.
	 */
	@Test
	public void booleanConstructorsTest() {
		final Boolean value = true;
		final Config config = new Config("key", value);
		assertThat(config.getType(), is(ConfigType.BOOLEAN));
		assertThat(config.getBooleanValue(), is(true));
	}

	/**
	 * Integer constructors test.
	 */
	@Test
	public void intConstructorsTest() {
		final int value = 42;
		final Config config = new Config("key", value);
		assertThat(config.getType(), is(ConfigType.NUMERIC));
		assertThat(config.getNumericValue(), is(Long.valueOf(value)));
	}

	/**
	 * Long constructors test.
	 */
	@Test
	public void longConstructorsTest() {
		final long value = 42;
		final Config config = new Config("key", value);
		assertThat(config.getType(), is(ConfigType.NUMERIC));
		assertThat(config.getNumericValue(), is(value));
	}

	/**
	 * Double constructors test.
	 */
	@Test
	public void doubleConstructorsTest() {
		final double value = 3.1416;
		final Config config = new Config("key", value);
		assertThat(config.getType(), is(ConfigType.DECIMAL));
		assertThat(config.getDoubleValue(), is(value));
	}

	/**
	 * Float constructors test.
	 */
	@Test
	public void floatConstructorsTest() {
		final float value = 3.1416f;
		final Config config = new Config("key", value);
		assertThat(config.getType(), is(ConfigType.DECIMAL));
		assertThat(config.getFloatValue(), is(value));
	}

	/**
	 * Big decimal constructors test.
	 */
	@Test
	public void bigDecimalConstructorsTest() {
		final BigDecimal value = new BigDecimal(3.1416);
		final Config config = new Config("key", value);
		assertThat(config.getType(), is(ConfigType.DECIMAL));
		assertThat(config.getDecimalValue(), is(value));
	}

	/**
	 * Date constructors test.
	 */
	@Test
	public void dateConstructorsTest() {
		final Date value = new Date();
		final Config config = new Config("key", value);
		assertThat(config.getType(), is(ConfigType.DATE));
		assertThat(config.getDateValue().toDate(), is(value));
	}

	/**
	 * Local date time constructors test.
	 */
	@Test
	public void localDateTimeConstructorsTest() {
		final LocalDateTime value = LocalDateTime.now();
		final Config config = new Config("key", value);
		assertThat(config.getType(), is(ConfigType.DATE));
		assertThat(config.getDateValue(), is(value));
	}

	/**
	 * Fetch string object value test.
	 */
	@Test
	public void fetchStringObjectValueTest() {
		final Object value = "value";
		final Config config = new Config("key", (String) value);
		assertThat(config.getValue(), is(value));
	}

	/**
	 * Fetch string object value test.
	 */
	@Test
	public void fetchBooleanObjectValueTest() {
		final Object value = true;
		final Config config = new Config("key", (Boolean) value);
		assertThat(config.getValue(), is(value));
	}

	/**
	 * Fetch string object value test.
	 */
	@Test
	public void fetchDateObjectValueTest() {
		final Object value = LocalDateTime.now();
		final Config config = new Config("key", (LocalDateTime) value);
		assertThat(config.getValue(), is(value));
	}

	/**
	 * Fetch string object value test.
	 */
	@Test
	public void fetchDecimalObjectValueTest() {
		final Object value = new BigDecimal(3.1416);
		final Config config = new Config("key", (BigDecimal) value);
		assertThat(config.getValue(), is(value));
	}

	/**
	 * Fetch string object value test.
	 */
	@Test
	public void fetchNumericObjectValueTest() {
		final Object value = Long.valueOf(42);
		final Config config = new Config("key", (Long) value);
		assertThat(config.getValue(), is(value));
	}

	/**
	 * Try to fetch non string value test.
	 */
	@Test(expected=IllegalArgumentException.class)
	public void tryToFetchNonStringValueTest() {
		final int value = 42;
		final Config config = new Config("key", value);
		config.getStringValue();
	}

	/**
	 * Try to fetch non boolean value test.
	 */
	@Test(expected=IllegalArgumentException.class)
	public void tryToFetchNonBooleanValueTest() {
		final int value = 42;
		final Config config = new Config("key", value);
		config.getBooleanValue();
	}

	/**
	 * Try to fetch non numeric value test.
	 */
	@Test(expected=IllegalArgumentException.class)
	public void tryToFetchNonNumericValueTest() {
		final Config config = new Config("key", true);
		config.getNumericValue();
	}

	/**
	 * Try to fetch non decimal value test.
	 */
	@Test(expected=IllegalArgumentException.class)
	public void tryToFetchNonDecimalValueTest() {
		final Config config = new Config("key", true);
		config.getDecimalValue();
	}

	/**
	 * Try to fetch non date value test.
	 */
	@Test(expected=IllegalArgumentException.class)
	public void tryToFetchNonDateValueTest() {
		final Config config = new Config("key", true);
		config.getDateValue();
	}

	/**
	 * Null string test.
	 */
	@Test(expected=NullPointerException.class)
	public void nullStringTest() {
		final Config config = new Config("key", 42);
		config.setStringValue(null);
	}

	/**
	 * Null decimal test.
	 */
	@Test(expected=NullPointerException.class)
	public void nullDecimalTest() {
		final Config config = new Config("key", 42);
		config.setDecimalValue(null);
	}

	/**
	 * Null date test.
	 */
	@Test(expected=NullPointerException.class)
	public void nullDateTest() {
		final Config config = new Config("key", 42);
		config.setDateValue(null);
	}
}
