package com.hawkprime.syndicate.service;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.Test;

import com.hawkprime.syndicate.dao.ValueDao;
import com.hawkprime.syndicate.model.Value;
import com.hawkprime.syndicate.model.builder.ValueBuilder;
import com.hawkprime.syndicate.util.NodePath;

/**
 * Setting tests.
 */
public class SettingServiceTest {
	private final SettingService settingService = new SettingService();

	@Test
	public void aTest() {
		final Integer expectedValue = 60;
		final ValueDao valueDao = mock(ValueDao.class);
		settingService.setValueDao(valueDao);

		final Value value = new ValueBuilder()
				.withValue(expectedValue)
				.build();

		when(valueDao.findByPath(new NodePath("/App/Feed/1/UpdateInterval")))
				.thenReturn(value);

		Integer testValue = settingService.getValue(new NodePath("/App/Feed/1/UpdateInterval"), Integer.class);
		assertThat(testValue, is(not(nullValue())));
		assertThat(testValue, is(expectedValue));

		testValue = settingService.getValue(new NodePath("/App/Feed/1/xxxUpdateInterval"), Integer.class);
		assertThat(testValue, is(nullValue()));
	}

	@Test(expected=ClassCastException.class)
	public void badCastTest() {
		final ValueDao valueDao = mock(ValueDao.class);
		settingService.setValueDao(valueDao);

		final Value value = new ValueBuilder()
				.withValue(0)
				.build();

		when(valueDao.findByPath(new NodePath("/App/Feed/1/UpdateInterval")))
				.thenReturn(value);

		final String testValue = settingService.getValue(new NodePath("/App/Feed/1/UpdateInterval"), String.class);
		assertThat(testValue, is(nullValue()));
	}
}
