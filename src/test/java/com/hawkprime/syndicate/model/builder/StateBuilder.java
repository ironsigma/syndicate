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

	/**
	 * Builds the state.
	 *
	 * @return the state
	 */
	public State build() {
		State state = new State();
		state.setId(id);
		state.setRead(read);
		state.setStared(stared);
		state.setPost(post);
		state.setUser(user);
		return state;
	}

	/**
	 * With read.
	 *
	 * @param read the read
	 * @return the state builder
	 */
	public StateBuilder withRead(final Boolean read) {
		this.read = read;
		return this;
	}

	/**
	 * With stared.
	 *
	 * @param stared the stared
	 * @return the state builder
	 */
	public StateBuilder withStared(final Boolean stared) {
		this.stared = stared;
		return this;
	}

	/**
	 * With post.
	 *
	 * @param post the post
	 * @return the state builder
	 */
	public StateBuilder withPost(final Post post) {
		this.post = post;
		return this;
	}

	/**
	 * With user.
	 *
	 * @param user the user
	 * @return the state builder
	 */
	public StateBuilder withUser(final User user) {
		this.user = user;
		return this;
	}

	/**
	 * With id.
	 *
	 * @param id the id
	 * @return the state builder
	 */
	public StateBuilder withId(final Long id) {
		this.id = id;
		return this;
	}

}
