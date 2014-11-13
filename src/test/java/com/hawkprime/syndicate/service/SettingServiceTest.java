package com.hawkprime.syndicate.service;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.jdom.IllegalDataException;
import org.junit.Test;

import com.hawkprime.syndicate.dao.NodeDao;
import com.hawkprime.syndicate.dao.SettingDao;
import com.hawkprime.syndicate.dao.ValueDao;
import com.hawkprime.syndicate.model.Node;
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
	 */
	@Test
	public void newSettingDeepExistingSubLevelsMultipleNonExistingPathTest() {
		final SettingDao settingDao = mock(SettingDao.class);
		final ValueDao valueDao = mock(ValueDao.class);
		final NodeDao nodeDao = mock(NodeDao.class);

		final NodePath versionPath = NodePath.at("/App/User/1/Feed/Meta/Internal/Build/version");
		when(valueDao.findByPath(versionPath))
				.thenReturn(null);

		final Setting setting = new SettingBuilder()
				.withName(versionPath.getLastComponent())
				.build();

		when(settingDao.findByName(versionPath.getLastComponent()))
				.thenReturn(setting);

		final Node partialNode = new NodeBuilder()
						.withPath("/App/User/1/Feed")
						.build();

		when(nodeDao.findClosestByPath(versionPath.getParent()))
				.thenReturn(partialNode);

		final SettingService service = new SettingService();
		service.setSettingDao(settingDao);
		service.setValueDao(valueDao);
		service.setNodeDao(nodeDao);

		service.setValue(versionPath, "1.0.0");

		final Node metaNode = new NodeBuilder()
				.withParent(partialNode)
				.withPath("/App/User/1/Feed/Meta")
				.build();

		final Node internalNode = new NodeBuilder()
				.withParent(metaNode)
				.withPath("/App/User/1/Feed/Meta/Internal")
				.build();

		final Node buildNode = new NodeBuilder()
				.withParent(internalNode)
				.withPath("/App/User/1/Feed/Meta/Internal/Build")
				.build();

		verify(nodeDao).create(metaNode);
		verify(nodeDao).create(internalNode);
		verify(nodeDao).create(buildNode);

		verify(valueDao).create(new ValueBuilder()
				.withValue("1.0.0")
				.withSetting(setting)
				.withNode(buildNode)
				.build());
	}

	/**
	 * New setting, multiple non existing path.
	 */
	@Test
	public void newSettingMultipleNonExistingPathTest() {
		final SettingDao settingDao = mock(SettingDao.class);
		final ValueDao valueDao = mock(ValueDao.class);
		final NodeDao nodeDao = mock(NodeDao.class);

		final NodePath versionPath = NodePath.at("/App/Meta/Internal/Build/version");
		when(valueDao.findByPath(versionPath))
				.thenReturn(null);

		final Setting setting = new SettingBuilder()
				.withName(versionPath.getLastComponent())
				.build();

		when(settingDao.findByName(versionPath.getLastComponent()))
				.thenReturn(setting);

		final Node partialNode = new NodeBuilder()
						.withPath("/App")
						.build();

		when(nodeDao.findClosestByPath(versionPath.getParent()))
				.thenReturn(partialNode);

		final SettingService service = new SettingService();
		service.setSettingDao(settingDao);
		service.setValueDao(valueDao);
		service.setNodeDao(nodeDao);

		service.setValue(versionPath, "1.0.0");

		final Node metaNode = new NodeBuilder()
				.withParent(partialNode)
				.withPath("/App/Meta")
				.build();

		final Node internalNode = new NodeBuilder()
				.withParent(metaNode)
				.withPath("/App/Meta/Internal")
				.build();

		final Node buildNode = new NodeBuilder()
				.withParent(internalNode)
				.withPath("/App/Meta/Internal/Build")
				.build();

		verify(nodeDao).create(metaNode);
		verify(nodeDao).create(internalNode);
		verify(nodeDao).create(buildNode);

		verify(valueDao).create(new ValueBuilder()
				.withValue("1.0.0")
				.withSetting(setting)
				.withNode(buildNode)
				.build());
	}

	/**
	 * New setting, one non existing path.
	 */
	@Test
	public void newSettingOneNonExistingPathTest() {
		final SettingDao settingDao = mock(SettingDao.class);
		final ValueDao valueDao = mock(ValueDao.class);
		final NodeDao nodeDao = mock(NodeDao.class);

		final NodePath versionPath = NodePath.at("/App/Meta/version");
		when(valueDao.findByPath(versionPath))
				.thenReturn(null);

		final Setting setting = new SettingBuilder()
				.withName(versionPath.getLastComponent())
				.build();

		when(settingDao.findByName(versionPath.getLastComponent()))
				.thenReturn(setting);

		final Node partialNode = new NodeBuilder()
						.withPath("/App")
						.build();

		when(nodeDao.findClosestByPath(versionPath.getParent()))
				.thenReturn(partialNode);

		final SettingService service = new SettingService();
		service.setSettingDao(settingDao);
		service.setValueDao(valueDao);
		service.setNodeDao(nodeDao);

		service.setValue(versionPath, "1.0.0");

		final Node settingNode = new NodeBuilder()
				.withParent(partialNode)
				.withPath("/App/Meta")
				.build();

		verify(nodeDao).create(settingNode);

		verify(valueDao).create(new ValueBuilder()
				.withValue("1.0.0")
				.withSetting(setting)
				.withNode(settingNode)
				.build());
	}

	/**
	 * New setting, existing path.
	 */
	@Test
	public void newSettingExistingPathTest() {
		final SettingDao settingDao = mock(SettingDao.class);
		final ValueDao valueDao = mock(ValueDao.class);
		final NodeDao nodeDao = mock(NodeDao.class);

		final NodePath versionPath = NodePath.at("/App/version");
		when(valueDao.findByPath(versionPath))
				.thenReturn(null);

		final Setting setting = new SettingBuilder()
				.withName(versionPath.getLastComponent())
				.build();

		when(settingDao.findByName(versionPath.getLastComponent()))
				.thenReturn(setting);

		final Node settingNode = new NodeBuilder()
						.withPath("/App")
						.build();

		when(nodeDao.findClosestByPath(versionPath.getParent()))
				.thenReturn(settingNode);

		final SettingService service = new SettingService();
		service.setSettingDao(settingDao);
		service.setValueDao(valueDao);
		service.setNodeDao(nodeDao);

		service.setValue(versionPath, "1.0.0");

		verify(valueDao).create(new ValueBuilder()
				.withValue("1.0.0")
				.withSetting(setting)
				.withNode(settingNode)
				.build());
	}

	/**
	 * Existing setting, existing path.
	 */
	@Test
	public void existingSettingExistingPathTest() {
		final ValueDao valueDao = mock(ValueDao.class);

		final NodePath versionPath = NodePath.at("/App/version");
		final Value value = new ValueBuilder()
				.build();

		when(valueDao.findByPath(versionPath))
				.thenReturn(value);

		final SettingService service = new SettingService();
		service.setValueDao(valueDao);

		service.setValue(versionPath, "1.0.0");

		verify(valueDao).update(value);
	}

	/**
	 * Non existing setting.
	 */
	@Test(expected=IllegalDataException.class)
	public void nonExistingSettingExistingPathTest() {
		final ValueDao valueDao = mock(ValueDao.class);
		final SettingDao settingDao = mock(SettingDao.class);

		final NodePath versionPath = NodePath.at("/App/version");
		when(valueDao.findByPath(versionPath))
				.thenReturn(null);

		final SettingService service = new SettingService();
		service.setValueDao(valueDao);
		service.setSettingDao(settingDao);

		service.setValue(versionPath, "1.0.0");
	}

	/**
	 * Non existing root.
	 */
	@Test
	public void nonExistingRootTest() {
		final ValueDao valueDao = mock(ValueDao.class);
		final SettingDao settingDao = mock(SettingDao.class);
		final NodeDao nodeDao = mock(NodeDao.class);

		final NodePath versionPath = NodePath.at("/version");
		when(valueDao.findByPath(versionPath))
				.thenReturn(null);

		final Setting setting = new SettingBuilder()
				.withName(versionPath.getLastComponent())
				.build();

		when(settingDao.findByName(versionPath.getLastComponent()))
				.thenReturn(setting);

		final SettingService service = new SettingService();
		service.setValueDao(valueDao);
		service.setSettingDao(settingDao);
		service.setNodeDao(nodeDao);

		service.setValue(versionPath, "1.0.0");

		final Node rootNode = new NodeBuilder()
				.withParent(null)
				.withPath("/")
				.build();

		verify(nodeDao).create(rootNode);

		verify(valueDao).create(new ValueBuilder()
				.withValue("1.0.0")
				.withSetting(setting)
				.withNode(rootNode)
				.build());
	}

	/**
	 * Gets the settings test.
	 */
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

		final int expectedNumberOfSettings = 2;
		final Map<String, Object> settings = settingService.getSettings(path);
		assertThat(settings, is(not(nullValue())));
		assertThat(settings.size(), is(expectedNumberOfSettings));
		assertThat(settings.containsKey(updateIntervalSetting.getName()), is(true));
		assertThat(settings.get(updateIntervalSetting.getName()), is(updateIntervalValue.getValue()));
		assertThat(settings.containsKey(maxUpdatesSetting.getName()), is(true));
		assertThat(settings.get(maxUpdatesSetting.getName()), is(updateIntervalValue.getValue()));
	}

	/**
	 * Gets the value test.
	 */
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

	/**
	 * Bad cast test.
	 */
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
