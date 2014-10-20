package com.hawkprime.syndicate.model.builder;

import org.joda.time.LocalDateTime;

import com.hawkprime.syndicate.model.Feed;
import com.hawkprime.syndicate.model.Update;

/**
 * Update Builder.
 */
public class UpdateBuilder {
	private static final Long DEFAULT_TOTAL = 100L;
	private static final Long DEFAULT_NEW = 50L;

	private Long totalCount = DEFAULT_TOTAL;
	private Long newCount = DEFAULT_NEW;
	private LocalDateTime updated = new LocalDateTime();
	private Feed feed;

	public Update build() {
		final Update post = new Update();
		post.setTotalCount(totalCount);
		post.setNewCount(newCount);
		post.setUpdated(updated);
		post.setFeed(feed);
		return post;
	}

	public UpdateBuilder withTotalCount(final Long totalCount) {
		this.totalCount = totalCount;
		return this;
	}

	public UpdateBuilder withNewCount(final Long newCount) {
		this.newCount = newCount;
		return this;
	}

	public UpdateBuilder withUpdated(final LocalDateTime updated) {
		this.updated = updated;
		return this;
	}

	public UpdateBuilder withFeed(final Feed feed) {
		this.feed = feed;
		return this;
	}
}
