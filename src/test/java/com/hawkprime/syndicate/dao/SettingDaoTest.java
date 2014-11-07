package com.hawkprime.syndicate.dao;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.junit.Assert.assertThat;

import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.hawkprime.syndicate.model.Setting;
import com.hawkprime.syndicate.util.NodePath;

/**
 * Setting DAO Test.
 */
public class SettingDaoTest extends AbstractDaoTest {
	@Autowired
	private SettingDao settingDao;

	@Test
	public void aTest() {
		final List<Setting> settingList = settingDao.findByPath(new NodePath("/App/Feed/1"));
		assertThat(settingList.size(), is(not(0)));
	}
}
