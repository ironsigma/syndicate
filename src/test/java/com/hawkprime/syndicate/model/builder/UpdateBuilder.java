package com.hawkprime.syndicate.model.builder;

import org.joda.time.LocalDateTime;

import com.hawkprime.syndicate.model.Feed;
import com.hawkprime.syndicate.model.Update;

public class UpdateBuilder {
	private Long totalCount = 100L;
	private Long newCount = 50L;
	private LocalDateTime updated = new LocalDateTime();
	private Feed feed = null;

	public Update build() {
		Update post = new Update();
		post.setTotalCount(totalCount);
		post.setNewCount(newCount);
		post.setUpdated(updated);
		post.setFeed(feed);
		return post;
	}
	
	public UpdateBuilder withTotalCount(Long totalCount) {
		this.totalCount = totalCount;
		return this;
	}
	
	public UpdateBuilder withNewCount(Long newCount) {
		this.newCount = newCount;
		return this;
	}
	
	public UpdateBuilder withUpdated(LocalDateTime updated) {
		this.updated = updated;
		return this;
	}
	
	public UpdateBuilder withFeed(Feed feed) {
		this.feed = feed;
		return this;
	}
}
