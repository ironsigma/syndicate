package com.hawkprime.syndicate.dao;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.Assert.assertThat;

import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.hawkprime.syndicate.model.Config;
import com.hawkprime.syndicate.model.builder.ConfigBuilder;
import com.hawkprime.syndicate.util.ConfigType;

/**
 * Configuration DAO Tests.
 */
public class ConfigDaoTest extends AbstractDaoTest {
	@Autowired
	private ConfigDao configDao;

	/**
	 * No section config value test.
	 */
	@Test(expected=NullPointerException.class)
	public void noSectionConfigValueTest() {
		configDao.getConfigValue(null, "name", ConfigType.STRING, 1L);
	}

	/**
	 * No key config value test.
	 */
	@Test(expected=NullPointerException.class)
	public void noKeyConfigValueTest() {
		configDao.getConfigValue("Section", null, ConfigType.STRING, 1L);
	}

	/**
	 * No type config value test.
	 */
	@Test(expected=NullPointerException.class)
	public void noTypeConfigValueTest() {
		configDao.getConfigValue("Section", "name", null, 1L);
	}

	/**
	 * No result config value test.
	 */
	@Test
	public void noResultConfigValueTest() {
		final Object value = configDao.getConfigValue("xxSection", "xxxname", ConfigType.STRING, 1L);
		assertThat(value, is(nullValue()));
	}

	/**
	 * Config value test.
	 */
	@Test
	public void configValueTest() {
		String name;

		name = (String) configDao.getConfigValue("User", "name", ConfigType.STRING, 1L);
		assertThat(name, is(not(nullValue())));
		assertThat(name, is("Joe Hawk"));

		name = (String) configDao.getConfigValue("User", "name", ConfigType.STRING, null);
		assertThat(name, is(not(nullValue())));
		assertThat(name, is("Joe Hawk 2"));

		name = (String) configDao.getConfigValue("User", "name", ConfigType.STRING, 2L);
		assertThat(name, is(not(nullValue())));
		assertThat(name, is("Joe Hawk 3"));
	}

	/**
	 * No section configuration test.
	 */
	@Test(expected=NullPointerException.class)
	public void noSectionConfigurationTest() {
		configDao.getConfiguration(null, 1L);
	}

	/**
	 * No reference id configuration test.
	 */
	@Test
	public void noReferenceIdConfigurationTest() {
		final List<Config> configList = configDao.getConfiguration("User", null);
		assertThat(configList.size(), is(1));
		assertThat(configList.get(0).getStringValue(), is("Joe Hawk 2"));
	}

	/**
	 * Configuration test.
	 */
	@Test
	public void configurationTest() {
		final int expectedMinUpdate = 2880;
		final int expectedMaxUpdate = 1;
		final int expectedMinOptimal = 70;
		final int expectedmaxOptimal = 80;

		final List<Config> configList = configDao.getConfiguration("Feed", 1L);
		assertThat(configList.size(), is(4));
		int keysTested = 0;
		for (final Config config : configList) {
			if (config.getKey().equals("max-update")) {
				assertThat(config.getIntegerValue(), is(expectedMaxUpdate));
				keysTested++;
			} else if (config.getKey().equals("min-update")) {
				assertThat(config.getIntegerValue(), is(expectedMinUpdate));
				keysTested++;
			} else if (config.getKey().equals("max-optimal")) {
				assertThat(config.getIntegerValue(), is(expectedmaxOptimal));
				keysTested++;
			} else if (config.getKey().equals("min-optimal")) {
				assertThat(config.getIntegerValue(), is(expectedMinOptimal));
				keysTested++;
			}
		}
		assertThat(keysTested, is(4));
	}

	/**
	 * Find all entities in table.
	 */
	@Test
	public void findAllTest() {
		final List<Config> allConfigs = configDao.findAll();
		assertThat(allConfigs.size(), is(7));
	}

	/**
	 * Read entities.
	 */
	@Test
	public void readConfigTest() {
		final Config config = configDao.findById(1L);
		assertThat(config.getKey(), is("name"));
		assertThat(config.getSection(), is("User"));
		assertThat(config.getType(), is(ConfigType.STRING));
		assertThat(config.getReferenceId(), is(1L));
		assertThat(config.getStringValue(), is("Joe Hawk"));
	}

	/**
	 * Create entities.
	 */
	@Test
	@Transactional
	public void createConfigTest() {
		final String section = "my.section";
		final String key = "my.config";
		final String value = "Another config";
		final Long refId = 99L;

		Config config = new ConfigBuilder()
			.withSection(section)
			.withKey(key)
			.withValue(value)
			.withReferenceId(refId)
			.build();

		configDao.create(config);
		final long id = config.getId();
		config = null;

		config = configDao.findById(id);

		assertThat(config.getSection(), is(section));
		assertThat(config.getKey(), is(key));
		assertThat(config.getStringValue(), is(value));
		assertThat(config.getReferenceId(), is(refId));
	}

	/**
	 * Update entities.
	 */
	@Test
	@Transactional
	public void updateConfigTest() {
		final String section = "my.section";
		final String key = "my.config";
		String value = "Another config";
		final Long refId = 99L;

		Config config = new ConfigBuilder()
			.withSection(section)
			.withKey(key)
			.withValue(value)
			.withReferenceId(refId)
			.build();

		// persist
		configDao.create(config);

		// clear out
		final long id = config.getId();
		config = null;

		// fetch back
		config = configDao.findById(id);

		// change values
		value = "Awsome config";

		// update config
		config.setValue(value);

		// persist
		configDao.update(config);

		// clear out
		config = null;

		// fetch back
		config = configDao.findById(id);

		// test
		assertThat(config.getStringValue(), is(value));
	}

	/**
	 * Delete entities.
	 */
	@Test
	@Transactional
	public void deleteConfigTest() {
		Config config = new ConfigBuilder()
				.build();

		configDao.create(config);

		final long id = config.getId();
		config = null;

		configDao.deleteById(id);

		config = configDao.findById(id);
		assertThat(config, is(nullValue()));
	}

}
