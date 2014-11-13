package com.hawkprime.syndicate.dao;

import com.hawkprime.syndicate.model.Setting;
import com.hawkprime.syndicate.model.builder.SettingBuilder;
import com.hawkprime.syndicate.util.NodePath;

import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.assertThat;

/**
 * Setting DAO Test.
 */
public class SettingDaoTest extends AbstractDaoTest {
	private static final String SETTING_MAX_UPDATE = "MaxUpdate";

	@Autowired
	private SettingDao settingDao;

	/**
	 * Creates the test.
	 */
	@Test
	@Transactional
	public void createTest() {
		final String name = "MyNewSetting";
		Setting setting = new SettingBuilder()
				.withName(name)
				.build();

		settingDao.create(setting);
		assertThat(setting.getId(), is(not(nullValue())));

		setting = settingDao.findById(setting.getId());
		assertThat(setting.getName(), is(name));
	}

	/**
	 * Read test.
	 */
	@Test
	public void readTest() {
		Setting setting = settingDao.findById(1L);
		assertThat(setting.getName(), is(SETTING_MAX_UPDATE));
	}

	/**
	 * Update test.
	 */
	@Test
	@Transactional
	public void updateTest() {
		final String initialName = "Initial Name";
		final String newName = "MyUpdatedSetting";
		Setting setting = new SettingBuilder()
				.withName(initialName)
				.build();

		settingDao.create(setting);
		assertThat(setting.getId(), is(not(nullValue())));

		setting = settingDao.findById(setting.getId());
		assertThat(setting.getName(), is(initialName));

		setting.setName(newName);
		settingDao.update(setting);

		setting = settingDao.findById(setting.getId());
		assertThat(setting.getName(), is(newName));
	}

	/**
	 * Delete test.
	 */
	@Test
	@Transactional
	public void deleteTest() {
		Setting setting = new SettingBuilder().build();

		settingDao.create(setting);
		Long id = setting.getId();
		setting = null;
		assertThat(id, is(not(nullValue())));

		settingDao.deleteById(id);

		setting = settingDao.findById(id);
		assertThat(setting, is(nullValue()));
	}

	/**
	 * Find by name test.
	 */
	@Test
	public void findByNameTest() {
		assertThat(settingDao.findByName("XXNotASettingXX"), is(nullValue()));
		assertThat(settingDao.findByName("SortField"), is(not(nullValue())));
	}

	/**
	 * Find by path test.
	 */
	@Test
	public void findByPathTest() {
		final int settingCount = 5;
		List<Setting> settingList = settingDao.findByPath(NodePath.at("/App/Feed/1"));
		assertThat(settingList.size(), is(not(0)));
		int count = 0;
		for (Setting setting : settingList) {
			if (SETTING_MAX_UPDATE.equals(setting.getName())
			    || "MinUpdate".equals(setting.getName())
			    || "MaxOptimalRange".equals(setting.getName())
			    || "MinOptimalRange".equals(setting.getName())
			    || "UpdateInterval".equals(setting.getName())) {

				count++;
			}
		}
		assertThat(count, is(settingCount));
	}
}
