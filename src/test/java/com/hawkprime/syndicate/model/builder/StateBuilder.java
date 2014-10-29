package com.hawkprime.syndicate.model.builder;

import com.hawkprime.syndicate.model.Post;
import com.hawkprime.syndicate.model.State;
import com.hawkprime.syndicate.model.User;

/**
 * State Builder.
 */
public class StateBuilder {
	private Long id;
	private boolean read;
	private boolean stared;
	private Post post;
	private User user;

	public State build() {
		final State state = new State();
		state.setId(id);
		state.setRead(read);
		state.setStared(stared);
		state.setPost(post);
		state.setUser(user);
		return state;
	}

	public StateBuilder withRead(final Boolean read) {
		this.read = read;
		return this;
	}

	public StateBuilder withStared(final Boolean stared) {
		this.stared = stared;
		return this;
	}

	public StateBuilder withPost(final Post post) {
		this.post = post;
		return this;
	}

	public StateBuilder withUser(final User user) {
		this.user = user;
		return this;
	}

	public StateBuilder withId(final Long id) {
		this.id = id;
		return this;
	}

}
