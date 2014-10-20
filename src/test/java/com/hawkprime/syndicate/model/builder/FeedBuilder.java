package com.hawkprime.syndicate.model.builder;

import com.hawkprime.syndicate.model.Feed;

public class FeedBuilder {
	private String name = "Feed";
	private String url = "http://myfeed.com/rss";

	public Feed build() {
		Feed feed = new Feed();
		feed.setName(name);
		feed.setUrl(url);
		return feed;
	}
	
	public FeedBuilder withName(String name) {
		this.name = name;
		return this;
	}
	
	public FeedBuilder withUrl(String url) {
		this.url = url;
		return this;
	}
}
