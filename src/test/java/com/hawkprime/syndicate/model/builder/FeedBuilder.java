package com.hawkprime.syndicate.model.builder;

import com.hawkprime.syndicate.model.Feed;

/**
 * Feed Builder.
 *
 * @version 5.0.0
 * @author Juan D Frias <juandfrias@gmail.com>
 */
public class FeedBuilder {
	private Long id;
	private String name = "Feed";
	private String url = "http://myfeed.com/rss";
	private Integer updateFrequency = 1;
	private Boolean active = true;

	/**
	 * Builds the feed.
	 *
	 * @return the feed
	 */
	public Feed build() {
		Feed feed = new Feed();
		feed.setId(id);
		feed.setName(name);
		feed.setUrl(url);
		feed.setActive(active);
		feed.setUpdateFrequency(updateFrequency);
		return feed;
	}

	/**
	 * With id.
	 *
	 * @param id the id
	 * @return the feed builder
	 */
	public FeedBuilder withId(final Long id) {
		this.id = id;
		return this;
	}

	/**
	 * With name.
	 *
	 * @param name the name
	 * @return the feed builder
	 */
	public FeedBuilder withName(final String name) {
		this.name = name;
		return this;
	}

	/**
	 * With URL.
	 *
	 * @param url the URL
	 * @return the feed builder
	 */
	public FeedBuilder withUrl(final String url) {
		this.url = url;
		return this;
	}

	/**
	 * With update frequency.
	 *
	 * @param updateFrequency the update frequency
	 * @return the feed builder
	 */
	public FeedBuilder withUpdateFrequency(final Integer updateFrequency) {
		this.updateFrequency = updateFrequency;
		return this;
	}

	/**
	 * With is active.
	 *
	 * @param active the active
	 * @return the feed builder
	 */
	public FeedBuilder withIsActive(final Boolean active) {
		this.active = active;
		return this;
	}
}
