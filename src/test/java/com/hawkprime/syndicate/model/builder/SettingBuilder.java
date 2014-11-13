package com.hawkprime.syndicate.model.builder;

import com.hawkprime.syndicate.model.Setting;

/**
 * Setting Builder.
 */
public class SettingBuilder {
	private Long id;
	private String name = "Setting";

	/**
	 * Builds the setting.
	 *
	 * @return the setting
	 */
	public Setting build() {
		Setting setting = new Setting();
		setting.setId(id);
		setting.setName(name);
		return setting;
	}

	/**
	 * With id.
	 *
	 * @param id the id
	 * @return the setting builder
	 */
	public SettingBuilder withId(final Long id) {
		this.id = id;
		return this;
	}

	/**
	 * With name.
	 *
	 * @param name the name
	 * @return the setting builder
	 */
	public SettingBuilder withName(final String name) {
		this.name = name;
		return this;
	}
}
