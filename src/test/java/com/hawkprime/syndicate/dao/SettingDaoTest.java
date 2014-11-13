package com.hawkprime.syndicate.dao;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.Assert.assertThat;

import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.hawkprime.syndicate.model.Setting;
import com.hawkprime.syndicate.model.builder.SettingBuilder;
import com.hawkprime.syndicate.util.NodePath;

/**
 * Setting DAO Test.
 */
public class SettingDaoTest extends AbstractDaoTest {
	@Autowired
	private SettingDao settingDao;

	@Test
	@Transactional
	public void createTest() {
		final String name = "MySetting";
		Setting setting = new SettingBuilder()
				.withName(name)
				.build();

		settingDao.create(setting);
		assertThat(setting.getId(), is(not(nullValue())));

		setting = settingDao.findById(setting.getId());
		assertThat(setting.getName(), is(name));
	}

	@Test
	public void readTest() {
		final Setting setting = settingDao.findById(1L);
		assertThat(setting.getName(), is("MaxUpdate"));
	}

	@Test
	@Transactional
	public void updateTest() {
		final String initialName = "Initial Name";
		final String newName = "MySetting";
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

	@Test
	@Transactional
	public void deleteTest() {
		Setting setting = new SettingBuilder().build();

		settingDao.create(setting);
		final Long id = setting.getId();
		setting = null;
		assertThat(id, is(not(nullValue())));

		settingDao.deleteById(id);

		setting = settingDao.findById(id);
		assertThat(setting, is(nullValue()));
	}

	@Test
	public void findByNameTest() {
		assertThat(settingDao.findByName("XXNotASettingXX"), is(nullValue()));
		assertThat(settingDao.findByName("SortField"), is(not(nullValue())));
	}

	@Test
	public void findByPathTest() {
		final List<Setting> settingList = settingDao.findByPath(NodePath.at("/App/Feed/1"));
		assertThat(settingList.size(), is(not(0)));
		int count = 0;
		for (Setting setting : settingList) {
			if ("MaxUpdate".equals(setting.getName())
			    || "MinUpdate".equals(setting.getName())
			    || "MaxOptimalRange".equals(setting.getName())
			    || "MinOptimalRange".equals(setting.getName())
			    || "UpdateInterval".equals(setting.getName())) {

				count++;
			}
		}
		assertThat(count, is(5));
	}
}
