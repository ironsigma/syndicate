package com.hawkprime.syndicate.dao;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.Assert.assertThat;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.hawkprime.syndicate.model.Value;
import com.hawkprime.syndicate.util.NodePath;
import com.hawkprime.syndicate.util.ValueType;

/**
 * Value DAO Tests.
 */
public class ValueDaoTest extends AbstractDaoTest {
	@Autowired
	private ValueDao valueDao;

	@Test
	public void findByPath() {
		final int expectedValue = 60;
		Value value = valueDao.findByPath(new NodePath("/App/Feed/1/UpdateInterval"));
		assertThat(value, is(not(nullValue())));
		assertThat(value.getType(), is(ValueType.INTEGER));
		assertThat((Integer) value.getValue(), is(expectedValue));

		value = valueDao.findByPath(new NodePath("/xxx/UpdateInterval"));
		assertThat(value, is(nullValue()));
	}
}
