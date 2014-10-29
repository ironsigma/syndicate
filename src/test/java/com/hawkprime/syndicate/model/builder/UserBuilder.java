package com.hawkprime.syndicate.model.builder;

import com.hawkprime.syndicate.model.User;

/**
 * User Builder.
 */
public class UserBuilder {
	private Long id;
	private String name = "User";

	public User build() {
		final User user = new User();
		user.setId(id);
		user.setName(name);
		return user;
	}

	public UserBuilder withId(final Long id) {
		this.id = id;
		return this;
	}

	public UserBuilder withName(final String name) {
		this.name = name;
		return this;
	}
}
