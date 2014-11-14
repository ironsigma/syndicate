package com.hawkprime.syndicate.model.builder;

import com.hawkprime.syndicate.model.Feed;
import com.hawkprime.syndicate.model.Update;

import org.joda.time.LocalDateTime;

/**
 * Update Builder.
 *
 * @version 5.0.0
 * @author Juan D Frias <juandfrias@gmail.com>
 */
public class UpdateBuilder {
	private static final Long DEFAULT_TOTAL = 100L;
	private static final Long DEFAULT_NEW = 50L;

	private Long id;
	private Long totalCount = DEFAULT_TOTAL;
	private Long newCount = DEFAULT_NEW;
	private LocalDateTime updated = LocalDateTime.now();
	private Feed feed;

	/**
	 * Builds the update.
	 *
	 * @return the update
	 */
	public Update build() {
		Update post = new Update();
		post.setId(id);
		post.setTotalCount(totalCount);
		post.setNewCount(newCount);
		post.setUpdated(updated);
		post.setFeed(feed);
		return post;
	}

	/**
	 * With id.
	 *
	 * @param id the id
	 * @return the update builder
	 */
	public UpdateBuilder withId(final Long id) {
		this.id = id;
		return this;
	}

	/**
	 * With total count.
	 *
	 * @param totalCount the total count
	 * @return the update builder
	 */
	public UpdateBuilder withTotalCount(final Long totalCount) {
		this.totalCount = totalCount;
		return this;
	}

	/**
	 * With new count.
	 *
	 * @param newCount the new count
	 * @return the update builder
	 */
	public UpdateBuilder withNewCount(final Long newCount) {
		this.newCount = newCount;
		return this;
	}

	/**
	 * With updated.
	 *
	 * @param updated the updated
	 * @return the update builder
	 */
	public UpdateBuilder withUpdated(final LocalDateTime updated) {
		this.updated = updated;
		return this;
	}

	/**
	 * With feed.
	 *
	 * @param feed the feed
	 * @return the update builder
	 */
	public UpdateBuilder withFeed(final Feed feed) {
		this.feed = feed;
		return this;
	}
}
