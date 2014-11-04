package com.hawkprime.syndicate.model;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.math.BigDecimal;
import java.util.Date;

import org.jdom.IllegalDataException;
import org.joda.time.LocalDateTime;
import org.junit.Test;

import com.hawkprime.syndicate.model.builder.ConfigBuilder;
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
		final Config config = new Config();
		config.setKey(null);
	}

	/**
	 * Empty key test.
	 */
	@Test(expected=NullPointerException.class)
	public void emptyKeyTest() {
		final Config config = new Config();
		config.setKey("");
	}

	/**
	 * Blank key test.
	 */
	@Test(expected=NullPointerException.class)
	public void blankKeyTest() {
		final Config config = new Config();
		config.setKey("    ");
	}

	/**
	 * Null section test.
	 */
	@Test(expected=NullPointerException.class)
	public void nullSectionTest() {
		final Config config = new Config();
		config.setSection(null);
	}

	/**
	 * Empty section test.
	 */
	@Test(expected=NullPointerException.class)
	public void emptySectionTest() {
		final Config config = new Config();
		config.setSection("");
	}

	/**
	 * Blank section test.
	 */
	@Test(expected=NullPointerException.class)
	public void blankSectionTest() {
		final Config config = new Config();
		config.setSection("    ");
	}

	/**
	 * Boolean value test.
	 */
	@Test
	public void booleanValueTest() {
		final Boolean value = true;
		final Config config = new ConfigBuilder()
			.withValue(value)
			.build();
		assertThat((Boolean) config.getValue(), is(value));
	}

	/**
	 * Date value test.
	 */
	@Test
	public void dateValueTest() {
		final LocalDateTime value = LocalDateTime.now();
		final Config config = new ConfigBuilder()
			.withValue(value)
			.build();
		assertThat((LocalDateTime) config.getValue(), is(value));
	}

	/**
	 * Decimal value test.
	 */
	@Test
	public void decimalValueTest() {
		final BigDecimal value = new BigDecimal(3.1416);
		final Config config = new ConfigBuilder()
			.withValue(value)
			.build();
		assertThat((BigDecimal) config.getValue(), is(value));
	}

	/**
	 * Numeric value test.
	 */
	@Test
	public void numericValueTest() {
		final Long value = 42L;
		final Config config = new ConfigBuilder()
			.withValue(value)
			.build();
		assertThat((Long) config.getValue(), is(value));
	}

	/**
	 * String value test.
	 */
	@Test
	public void stringValueTest() {
		final String value = "value";
		final Config config = new ConfigBuilder()
			.withValue(value)
			.build();
		assertThat((String) config.getValue(), is(value));
	}

	/**
	 * String constructors test.
	 */
	@Test
	public void stringConstructorsTest() {
		final String value = "value";
		final Config config = new Config();
		config.setValue(value);
		assertThat(config.getType(), is(ConfigType.STRING));
		assertThat(config.getStringValue(), is(value));
	}

	/**
	 * Boolean constructors test.
	 */
	@Test
	public void booleanConstructorsTest() {
		final Boolean value = true;
		final Config config = new Config();
		config.setValue(value);
		assertThat(config.getType(), is(ConfigType.BOOLEAN));
		assertThat(config.getBooleanValue(), is(true));
	}

	/**
	 * Integer constructors test.
	 */
	@Test
	public void intConstructorsTest() {
		final int value = 42;
		final Config config = new Config();
		config.setValue(value);
		assertThat(config.getType(), is(ConfigType.NUMERIC));
		assertThat(config.getIntegerValue(), is(value));
	}

	/**
	 * Long constructors test.
	 */
	@Test
	public void longConstructorsTest() {
		final long value = 42;
		final Config config = new Config();
		config.setValue(value);
		assertThat(config.getType(), is(ConfigType.NUMERIC));
		assertThat(config.getNumericValue(), is(value));
	}

	/**
	 * Double constructors test.
	 */
	@Test
	public void doubleConstructorsTest() {
		final double value = 3.1416;
		final Config config = new Config();
		config.setValue(value);
		assertThat(config.getType(), is(ConfigType.DECIMAL));
		assertThat(config.getDoubleValue(), is(value));
	}

	/**
	 * Float constructors test.
	 */
	@Test
	public void floatConstructorsTest() {
		final float value = 3.1416f;
		final Config config = new Config();
		config.setValue(value);
		assertThat(config.getType(), is(ConfigType.DECIMAL));
		assertThat(config.getFloatValue(), is(value));
	}

	/**
	 * Big decimal constructors test.
	 */
	@Test
	public void bigDecimalConstructorsTest() {
		final BigDecimal value = new BigDecimal(3.1416);
		final Config config = new Config();
		config.setValue(value);
		assertThat(config.getType(), is(ConfigType.DECIMAL));
		assertThat(config.getDecimalValue(), is(value));
	}

	/**
	 * Date constructors test.
	 */
	@Test
	public void dateConstructorsTest() {
		final Date value = new Date();
		final Config config = new Config();
		config.setValue(value);
		assertThat(config.getType(), is(ConfigType.DATE));
		assertThat(config.getDateValue().toDate(), is(value));
	}

	/**
	 * Local date time constructors test.
	 */
	@Test
	public void localDateTimeConstructorsTest() {
		final LocalDateTime value = LocalDateTime.now();
		final Config config = new Config();
		config.setValue(value);
		assertThat(config.getType(), is(ConfigType.DATE));
		assertThat(config.getDateValue(), is(value));
	}

	/**
	 * Try to fetch non string value test.
	 */
	@Test(expected=IllegalArgumentException.class)
	public void tryToFetchNonStringValueTest() {
		final int value = 42;
		final Config config = new Config();
		config.setValue(value);
		config.getStringValue();
	}

	/**
	 * Try to fetch non boolean value test.
	 */
	@Test(expected=IllegalArgumentException.class)
	public void tryToFetchNonBooleanValueTest() {
		final int value = 42;
		final Config config = new Config();
		config.setValue(value);
		config.getBooleanValue();
	}

	/**
	 * Try to fetch non numeric value test.
	 */
	@Test(expected=IllegalArgumentException.class)
	public void tryToFetchNonNumericValueTest() {
		final Config config = new Config();
		config.setValue(true);
		config.getNumericValue();
	}

	/**
	 * Try to fetch non integer value test.
	 */
	@Test(expected=IllegalArgumentException.class)
	public void tryToFetchNonIntegerValueTest() {
		final Config config = new Config();
		config.setValue(true);
		config.getIntegerValue();
	}

	/**
	 * Try to fetch non decimal value test.
	 */
	@Test(expected=IllegalArgumentException.class)
	public void tryToFetchNonDecimalValueTest() {
		final Config config = new Config();
		config.setValue(true);
		config.getDecimalValue();
	}

	/**
	 * Try to fetch non date value test.
	 */
	@Test(expected=IllegalArgumentException.class)
	public void tryToFetchNonDateValueTest() {
		final Config config = new Config();
		config.setValue(true);
		config.getDateValue();
	}

	/**
	 * Try to fetch big long test.
	 */
	@Test(expected=IllegalDataException.class)
	public void tryToFetchBigLongTest() {
		final Config config = new Config();
		config.setValue(Integer.MAX_VALUE + 1L);
		config.getIntegerValue();
	}

	/**
	 * Try to fetch small long test.
	 */
	@Test(expected=IllegalDataException.class)
	public void tryToFetchSmallLongTest() {
		final Config config = new Config();
		config.setValue(Integer.MIN_VALUE - 1L);
		config.getIntegerValue();
	}

	/**
	 * Null string test.
	 */
	@Test(expected=NullPointerException.class)
	public void nullStringTest() {
		final Config config = new Config();
		final String value = null;
		config.setValue(value);
	}

	/**
	 * Null decimal test.
	 */
	@Test(expected=NullPointerException.class)
	public void nullDecimalTest() {
		final Config config = new Config();
		final BigDecimal value = null;
		config.setValue(value);
	}

	/**
	 * Null date test.
	 */
	@Test(expected=NullPointerException.class)
	public void nullDateTest() {
		final Config config = new Config();
		final LocalDateTime value = null;
		config.setValue(value);
	}
}
