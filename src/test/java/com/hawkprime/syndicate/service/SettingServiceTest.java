package com.hawkprime.syndicate.service;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.junit.Ignore;
import org.junit.Test;

import com.hawkprime.syndicate.dao.NodeDao;
import com.hawkprime.syndicate.dao.SettingDao;
import com.hawkprime.syndicate.dao.ValueDao;
import com.hawkprime.syndicate.model.Setting;
import com.hawkprime.syndicate.model.Value;
import com.hawkprime.syndicate.model.builder.NodeBuilder;
import com.hawkprime.syndicate.model.builder.SettingBuilder;
import com.hawkprime.syndicate.model.builder.ValueBuilder;
import com.hawkprime.syndicate.util.NodePath;

/**
 * Setting tests.
 */
public class SettingServiceTest {
	private final SettingService settingService = new SettingService();

	/**
	 * New setting, deep existing sub levels, multiple non existing path.
	 * TODO: Finish mocks
	 */
	@Test
	@Ignore
	public void newSettingDeepExistingSubLevelsMultipleNonExistingPathTest() {
		SettingDao settingDao = mock(SettingDao.class);
		ValueDao valueDao = mock(ValueDao.class);
		NodeDao nodeDao = mock(NodeDao.class);

		settingDao.create(new SettingBuilder()
				.withName("version")
				.build());

		final SettingService service = new SettingService();
		service.setSettingDao(settingDao);
		service.setValueDao(valueDao);
		service.setNodeDao(nodeDao);

		service.setValue(NodePath.at("/App/User/1/Feed/Meta/Build/version"), "1.0.0");
	}

	/**
	 * New setting, multiple non existing path.
	 * TODO: Finish mocks
	 */
	@Test
	@Ignore
	public void newSettingMultipleNonExistingPathTest() {
		SettingDao settingDao = mock(SettingDao.class);
		ValueDao valueDao = mock(ValueDao.class);
		NodeDao nodeDao = mock(NodeDao.class);

		settingDao.create(new SettingBuilder()
				.withName("version")
				.build());

		final SettingService service = new SettingService();
		service.setSettingDao(settingDao);
		service.setValueDao(valueDao);
		service.setNodeDao(nodeDao);

		service.setValue(NodePath.at("/App/Meta/Internal/Build/version"), "1.0.0");
	}

	/**
	 * New setting, one non existing path.
	 * TODO: Finish mocks
	 */
	@Test
	@Ignore
	public void newSettingOneNonExistingPathTest() {
		SettingDao settingDao = mock(SettingDao.class);
		ValueDao valueDao = mock(ValueDao.class);
		NodeDao nodeDao = mock(NodeDao.class);

		settingDao.create(new SettingBuilder()
				.withName("version")
				.build());

		final SettingService service = new SettingService();
		service.setSettingDao(settingDao);
		service.setValueDao(valueDao);
		service.setNodeDao(nodeDao);

		service.setValue(NodePath.at("/App/Meta/version"), "1.0.0");
	}

	/**
	 * New setting, existing path.
	 * TODO: Finish mocks
	 */
	@Test
	public void newSettingExistingPathTest() {
		SettingDao settingDao = mock(SettingDao.class);
		ValueDao valueDao = mock(ValueDao.class);
		NodeDao nodeDao = mock(NodeDao.class);

		when(valueDao.findByPath(NodePath.at("/App/version")))
				.thenReturn(null);

		Setting setting = new SettingBuilder()
				.withName("version")
				.build();

		when(settingDao.findByName("version"))
				.thenReturn(setting);

		when(nodeDao.findClosestByPath(NodePath.at("/App")))
				.thenReturn(new NodeBuilder()
						.withPath("/App")
						.build());

		final SettingService service = new SettingService();
		service.setSettingDao(settingDao);
		service.setValueDao(valueDao);
		service.setNodeDao(nodeDao);

		service.setValue(NodePath.at("/App/version"), "1.0.0");
	}

	@Test
	public void getSettingsTest() {
		final ValueDao valueDao = mock(ValueDao.class);
		final SettingDao settingDao = mock(SettingDao.class);
		settingService.setValueDao(valueDao);
		settingService.setSettingDao(settingDao);

		final Setting updateIntervalSetting = new SettingBuilder().withName("UpdateInterval").build();
		final Setting maxUpdatesSetting = new SettingBuilder().withName("MaxUpdates").build();
		final List<Setting> settingList = Arrays.asList(new Setting[] {
				updateIntervalSetting,
				maxUpdatesSetting,
		});

		final NodePath path = NodePath.at("/App/Feed/1");
		when(settingDao.findByPath(path))
				.thenReturn(settingList);

		final Value updateIntervalValue = new ValueBuilder()
				.withValue(1)
				.withNode(new NodeBuilder().build())
				.withSetting(updateIntervalSetting)
				.build();

		when(valueDao.findByPath(path.append(updateIntervalSetting.getName())))
				.thenReturn(updateIntervalValue);

		final Value maxUpdatesValue = new ValueBuilder()
				.withValue(1)
				.withNode(new NodeBuilder().build())
				.withSetting(maxUpdatesSetting)
				.build();

		when(valueDao.findByPath(path.append(maxUpdatesSetting.getName())))
				.thenReturn(maxUpdatesValue);

		final Map<String, Object> settings = settingService.getSettings(path);
		assertThat(settings, is(not(nullValue())));
		assertThat(settings.size(), is(2));
		assertThat(settings.containsKey(updateIntervalSetting.getName()), is(true));
		assertThat(settings.get(updateIntervalSetting.getName()), is(updateIntervalValue.getValue()));
		assertThat(settings.containsKey(maxUpdatesSetting.getName()), is(true));
		assertThat(settings.get(maxUpdatesSetting.getName()), is(updateIntervalValue.getValue()));
	}

	@Test
	public void getValueTest() {
		final Integer expectedValue = 60;
		final ValueDao valueDao = mock(ValueDao.class);
		settingService.setValueDao(valueDao);

		final Value value = new ValueBuilder()
				.withValue(expectedValue)
				.build();

		when(valueDao.findByPath(NodePath.at("/App/Feed/1/UpdateInterval")))
				.thenReturn(value);

		Integer testValue = settingService.getValue(NodePath.at("/App/Feed/1/UpdateInterval"), Integer.class);
		assertThat(testValue, is(not(nullValue())));
		assertThat(testValue, is(expectedValue));

		testValue = settingService.getValue(NodePath.at("/App/Feed/1/xxxUpdateInterval"), Integer.class);
		assertThat(testValue, is(nullValue()));
	}

	@Test(expected=ClassCastException.class)
	public void badCastTest() {
		final ValueDao valueDao = mock(ValueDao.class);
		settingService.setValueDao(valueDao);

		final Value value = new ValueBuilder()
				.withValue(0)
				.build();

		when(valueDao.findByPath(NodePath.at("/App/Feed/1/UpdateInterval")))
				.thenReturn(value);

		final String testValue = settingService.getValue(NodePath.at("/App/Feed/1/UpdateInterval"), String.class);
		assertThat(testValue, is(nullValue()));
	}
}
