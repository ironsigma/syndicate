package com.hawkprime.syndicate.dao;

import com.hawkprime.syndicate.model.Value;
import com.hawkprime.syndicate.util.NodePath;
import com.hawkprime.syndicate.util.ValueType;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.assertThat;

/**
 * Value DAO Tests.
 */
public class ValueDaoTest extends AbstractDaoTest {
	@Autowired
	private ValueDao valueDao;

	@Autowired
	private SettingDao settingDao;

	/**
	 * Find by path.
	 */
	@Test
	public void findByPath() {
		final int expectedValue = 60;
		Value value = valueDao.findByPath(NodePath.at("/App/Feed/1/UpdateInterval"));
		assertThat(value, is(not(nullValue())));
		assertThat(value.getType(), is(ValueType.INTEGER));
		assertThat((Integer) value.getValue(), is(expectedValue));

		value = valueDao.findByPath(NodePath.at("/xxx/UpdateInterval"));
		assertThat(value, is(nullValue()));
	}
}
