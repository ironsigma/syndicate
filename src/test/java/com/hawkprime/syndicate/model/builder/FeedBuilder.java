package com.hawkprime.syndicate.model.builder;

import com.hawkprime.syndicate.model.Feed;

/**
 * Feed Builder.
 */
public class FeedBuilder {
	private Long id;
	private String name = "Feed";
	private String url = "http://myfeed.com/rss";
	private Integer updateFrequency = 1;
	private Boolean active = true;

	public Feed build() {
		final Feed feed = new Feed();
		feed.setId(id);
		feed.setName(name);
		feed.setUrl(url);
		feed.setActive(active);
		feed.setUpdateFrequency(updateFrequency);
		return feed;
	}

	public FeedBuilder withId(final Long id) {
		this.id = id;
		return this;
	}

	public FeedBuilder withName(final String name) {
		this.name = name;
		return this;
	}

	public FeedBuilder withUrl(final String url) {
		this.url = url;
		return this;
	}

	public FeedBuilder withUpdateFrequency(final Integer updateFrequency) {
		this.updateFrequency = updateFrequency;
		return this;
	}

	public FeedBuilder withIsActive(final Boolean active) {
		this.active = active;
		return this;
	}
}
