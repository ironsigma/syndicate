package com.hawkprime.syndicate.dao;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.Assert.assertThat;

import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.hawkprime.syndicate.model.Config;

/**
 * Configuration DAO Tests.
 */
public class ConfigDaoTest extends AbstractDaoTest {
	@Autowired
	private ConfigDao configDao;

	@Test
	public void findAllTest() {
		final List<Config> allConfigs = configDao.findAll();
		assertThat(allConfigs.size(), is(1));
	}

	@Test
	public void readConfigTest() {
		final Config config = configDao.findById("user.01.name");
		assertThat(config.getStringValue(), is("Joe Hawk"));
	}

	@Test
	@Transactional
	public void createConfigTest() {
		final String key = "my.config";
		final String value = "Another config";

		Config config = new Config(key, value);

		configDao.create(config);
		config = null;

		config = configDao.findById(key);

		assertThat(config.getStringValue(), is(value));
	}

	@Test
	@Transactional
	public void updateConfigTest() {
		Config config = new Config("my.update.config", "value");

		// persist
		configDao.create(config);

		// clear out
		final String key = config.getId();
		config = null;

		// fetch back
		config = configDao.findById(key);

		// change values
		final String value = "Awsome config";

		// update config
		config.setStringValue(value);

		// persist
		configDao.update(config);

		// clear out
		config = null;

		// fetch back
		config = configDao.findById(key);

		// test
		assertThat(config.getStringValue(), is(value));
	}

	@Test
	@Transactional
	public void deleteConfigTest() {
		Config config = new Config("my.delete.config", "value");

		configDao.create(config);

		final String key = config.getId();
		config = null;

		configDao.deleteById(key);

		config = configDao.findById(key);
		assertThat(config, is(nullValue()));
	}
}
