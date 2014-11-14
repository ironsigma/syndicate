package com.hawkprime.syndicate.service;

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

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.jdom.IllegalDataException;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.*;

/**
 * Setting tests.
 *
 * @version 5.0.0
 * @author Juan D Frias <juandfrias@gmail.com>
 */
public class SettingServiceTest {
	private static final String VERSION_VALUE = "1.0.0";
	private static final NodePath APP_PATH = NodePath.at("/App");
	private static final NodePath APP_VERSION_PATH = NodePath.at("/App/version");
	private static final NodePath UPDATE_INTERVAL_PATH = NodePath.at("/App/Feed/1/UpdateInterval");

	private final SettingService settingService = new SettingService();

	/**
	 * New setting, deep existing sub levels, multiple non existing path.
	 */
	@Test
	public void newSettingDeepExistingSubLevelsMultipleNonExistingPathTest() {
		SettingDao settingDao = mock(SettingDao.class);
		ValueDao valueDao = mock(ValueDao.class);
		NodeDao nodeDao = mock(NodeDao.class);

		NodePath versionPath = NodePath.at("/App/User/1/Feed/Meta/Internal/Build/version");
		when(valueDao.findByPath(versionPath))
				.thenReturn(null);

		Setting setting = new SettingBuilder()
				.withName(versionPath.getLastComponent())
				.build();

		when(settingDao.findByName(versionPath.getLastComponent()))
				.thenReturn(setting);

		Node partialNode = new NodeBuilder()
						.withPath("/App/User/1/Feed")
						.build();

		when(nodeDao.findClosestByPath(versionPath.getParent()))
				.thenReturn(partialNode);

		SettingService service = new SettingService();
		service.setSettingDao(settingDao);
		service.setValueDao(valueDao);
		service.setNodeDao(nodeDao);

		service.setValue(versionPath, VERSION_VALUE);

		Node metaNode = new NodeBuilder()
				.withParent(partialNode)
				.withPath("/App/User/1/Feed/Meta")
				.build();

		Node internalNode = new NodeBuilder()
				.withParent(metaNode)
				.withPath("/App/User/1/Feed/Meta/Internal")
				.build();

		Node buildNode = new NodeBuilder()
				.withParent(internalNode)
				.withPath("/App/User/1/Feed/Meta/Internal/Build")
				.build();

		verify(nodeDao).create(metaNode);
		verify(nodeDao).create(internalNode);
		verify(nodeDao).create(buildNode);

		verify(valueDao).create(new ValueBuilder()
				.withValue(VERSION_VALUE)
				.withSetting(setting)
				.withNode(buildNode)
				.build());
	}

	/**
	 * New setting, multiple non existing path.
	 */
	@Test
	public void newSettingMultipleNonExistingPathTest() {
		SettingDao settingDao = mock(SettingDao.class);
		ValueDao valueDao = mock(ValueDao.class);
		NodeDao nodeDao = mock(NodeDao.class);

		NodePath versionPath = NodePath.at("/App/Meta/Internal/Build/version");
		when(valueDao.findByPath(versionPath))
				.thenReturn(null);

		Setting setting = new SettingBuilder()
				.withName(versionPath.getLastComponent())
				.build();

		when(settingDao.findByName(versionPath.getLastComponent()))
				.thenReturn(setting);

		Node partialNode = new NodeBuilder()
						.withPath(APP_PATH.toString())
						.build();

		when(nodeDao.findClosestByPath(versionPath.getParent()))
				.thenReturn(partialNode);

		SettingService service = new SettingService();
		service.setSettingDao(settingDao);
		service.setValueDao(valueDao);
		service.setNodeDao(nodeDao);

		service.setValue(versionPath, VERSION_VALUE);

		Node metaNode = new NodeBuilder()
				.withParent(partialNode)
				.withPath(APP_PATH.append("/Meta").toString())
				.build();

		Node internalNode = new NodeBuilder()
				.withParent(metaNode)
				.withPath("/App/Meta/Internal")
				.build();

		Node buildNode = new NodeBuilder()
				.withParent(internalNode)
				.withPath("/App/Meta/Internal/Build")
				.build();

		verify(nodeDao).create(metaNode);
		verify(nodeDao).create(internalNode);
		verify(nodeDao).create(buildNode);

		verify(valueDao).create(new ValueBuilder()
				.withValue(VERSION_VALUE)
				.withSetting(setting)
				.withNode(buildNode)
				.build());
	}

	/**
	 * New setting, one non existing path.
	 */
	@Test
	public void newSettingOneNonExistingPathTest() {
		SettingDao settingDao = mock(SettingDao.class);
		ValueDao valueDao = mock(ValueDao.class);
		NodeDao nodeDao = mock(NodeDao.class);

		NodePath versionPath = NodePath.at("/App/Meta/version");
		when(valueDao.findByPath(versionPath))
				.thenReturn(null);

		Setting setting = new SettingBuilder()
				.withName(versionPath.getLastComponent())
				.build();

		when(settingDao.findByName(versionPath.getLastComponent()))
				.thenReturn(setting);

		Node partialNode = new NodeBuilder()
						.withPath(APP_PATH.toString())
						.build();

		when(nodeDao.findClosestByPath(versionPath.getParent()))
				.thenReturn(partialNode);

		SettingService service = new SettingService();
		service.setSettingDao(settingDao);
		service.setValueDao(valueDao);
		service.setNodeDao(nodeDao);

		service.setValue(versionPath, VERSION_VALUE);

		Node settingNode = new NodeBuilder()
				.withParent(partialNode)
				.withPath("/App/Meta")
				.build();

		verify(nodeDao).create(settingNode);

		verify(valueDao).create(new ValueBuilder()
				.withValue(VERSION_VALUE)
				.withSetting(setting)
				.withNode(settingNode)
				.build());
	}

	/**
	 * New setting, existing path.
	 */
	@Test
	public void newSettingExistingPathTest() {
		SettingDao settingDao = mock(SettingDao.class);
		ValueDao valueDao = mock(ValueDao.class);
		NodeDao nodeDao = mock(NodeDao.class);

		when(valueDao.findByPath(APP_VERSION_PATH))
				.thenReturn(null);

		Setting setting = new SettingBuilder()
				.withName(APP_VERSION_PATH.getLastComponent())
				.build();

		when(settingDao.findByName(APP_VERSION_PATH.getLastComponent()))
				.thenReturn(setting);

		Node settingNode = new NodeBuilder()
						.withPath(APP_PATH.toString())
						.build();

		when(nodeDao.findClosestByPath(APP_VERSION_PATH.getParent()))
				.thenReturn(settingNode);

		SettingService service = new SettingService();
		service.setSettingDao(settingDao);
		service.setValueDao(valueDao);
		service.setNodeDao(nodeDao);

		service.setValue(APP_VERSION_PATH, VERSION_VALUE);

		verify(valueDao).create(new ValueBuilder()
				.withValue(VERSION_VALUE)
				.withSetting(setting)
				.withNode(settingNode)
				.build());
	}

	/**
	 * Existing setting, existing path.
	 */
	@Test
	public void existingSettingExistingPathTest() {
		ValueDao valueDao = mock(ValueDao.class);

		Value value = new ValueBuilder()
				.build();

		when(valueDao.findByPath(APP_VERSION_PATH))
				.thenReturn(value);

		SettingService service = new SettingService();
		service.setValueDao(valueDao);

		service.setValue(APP_VERSION_PATH, VERSION_VALUE);

		verify(valueDao).update(value);
	}

	/**
	 * Non existing setting.
	 */
	@Test(expected=IllegalDataException.class)
	public void nonExistingSettingExistingPathTest() {
		ValueDao valueDao = mock(ValueDao.class);
		SettingDao settingDao = mock(SettingDao.class);

		when(valueDao.findByPath(APP_VERSION_PATH))
				.thenReturn(null);

		SettingService service = new SettingService();
		service.setValueDao(valueDao);
		service.setSettingDao(settingDao);

		service.setValue(APP_VERSION_PATH, VERSION_VALUE);
	}

	/**
	 * Non existing root.
	 */
	@Test
	public void nonExistingRootTest() {
		ValueDao valueDao = mock(ValueDao.class);
		SettingDao settingDao = mock(SettingDao.class);
		NodeDao nodeDao = mock(NodeDao.class);

		SettingService service = new SettingService();
		service.setValueDao(valueDao);
		service.setSettingDao(settingDao);
		service.setNodeDao(nodeDao);

		NodePath versionPath = NodePath.at("/version");
		when(valueDao.findByPath(versionPath))
				.thenReturn(null);

		Setting setting = new SettingBuilder()
				.withName(versionPath.getLastComponent())
				.build();

		when(settingDao.findByName(versionPath.getLastComponent()))
				.thenReturn(setting);

		service.setValue(versionPath, VERSION_VALUE);

		Node rootNode = new NodeBuilder()
				.withParent(null)
				.withPath("/")
				.build();

		verify(nodeDao).create(rootNode);

		verify(valueDao).create(new ValueBuilder()
				.withValue(VERSION_VALUE)
				.withSetting(setting)
				.withNode(rootNode)
				.build());
	}

	/**
	 * Gets the settings test.
	 */
	@Test
	public void getSettingsTest() {
		ValueDao valueDao = mock(ValueDao.class);
		SettingDao settingDao = mock(SettingDao.class);
		settingService.setValueDao(valueDao);
		settingService.setSettingDao(settingDao);

		Setting updateIntervalSetting = new SettingBuilder().withName("UpdateInterval").build();
		Setting maxUpdatesSetting = new SettingBuilder().withName("MaxUpdates").build();
		List<Setting> settingList = Arrays.asList(new Setting[] {
				updateIntervalSetting,
				maxUpdatesSetting,
		});

		NodePath path = NodePath.at("/App/Feed/1");
		when(settingDao.findByPath(path))
				.thenReturn(settingList);

		Value updateIntervalValue = new ValueBuilder()
				.withValue(1)
				.withNode(new NodeBuilder().build())
				.withSetting(updateIntervalSetting)
				.build();

		when(valueDao.findByPath(path.append(updateIntervalSetting.getName())))
				.thenReturn(updateIntervalValue);

		Value maxUpdatesValue = new ValueBuilder()
				.withValue(1)
				.withNode(new NodeBuilder().build())
				.withSetting(maxUpdatesSetting)
				.build();

		when(valueDao.findByPath(path.append(maxUpdatesSetting.getName())))
				.thenReturn(maxUpdatesValue);

		final int expectedNumberOfSettings = 2;
		Map<String, Object> settings = settingService.getSettings(path);
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
		ValueDao valueDao = mock(ValueDao.class);
		settingService.setValueDao(valueDao);

		Value value = new ValueBuilder()
				.withValue(expectedValue)
				.build();

		when(valueDao.findByPath(UPDATE_INTERVAL_PATH))
				.thenReturn(value);

		Integer testValue = settingService.getValue(UPDATE_INTERVAL_PATH, Integer.class);
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
		ValueDao valueDao = mock(ValueDao.class);
		settingService.setValueDao(valueDao);

		Value value = new ValueBuilder()
				.withValue(0)
				.build();

		when(valueDao.findByPath(UPDATE_INTERVAL_PATH))
				.thenReturn(value);

		String testValue = settingService.getValue(UPDATE_INTERVAL_PATH, String.class);
		assertThat(testValue, is(nullValue()));
	}
}
