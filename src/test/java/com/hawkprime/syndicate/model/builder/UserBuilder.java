package com.hawkprime.syndicate.model.builder;

import com.hawkprime.syndicate.model.User;

/**
 * User Builder.
 *
 * @version 5.0.0
 * @author Juan D Frias <juandfrias@gmail.com>
 */
public class UserBuilder {
	private Long id;
	private String name = "User";

	/**
	 * Builds the user.
	 *
	 * @return the user
	 */
	public User build() {
		User user = new User();
		user.setId(id);
		user.setName(name);
		return user;
	}

	/**
	 * With id.
	 *
	 * @param id the id
	 * @return the user builder
	 */
	public UserBuilder withId(final Long id) {
		this.id = id;
		return this;
	}

	/**
	 * With name.
	 *
	 * @param name the name
	 * @return the user builder
	 */
	public UserBuilder withName(final String name) {
		this.name = name;
		return this;
	}
}
